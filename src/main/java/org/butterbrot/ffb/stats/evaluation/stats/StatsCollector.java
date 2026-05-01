package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.TurnMode;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.model.change.ModelChange;
import com.fumbbl.ffb.model.change.ModelChangeId;
import com.fumbbl.ffb.net.commands.ServerCommand;
import com.fumbbl.ffb.net.commands.ServerCommandModelSync;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportList;
import com.fumbbl.ffb.report.ReportStartHalf;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.adapter.PlayerActionMapping;
import org.butterbrot.ffb.stats.evaluation.stats.common.BlockRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.KickoffResultEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.MasterChefRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.PillingOnEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.PlayerActionEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.ReRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.ScatterBallEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.SkillRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.TimeoutEnforcedEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.UploadEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.WeatherEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.WizardUseEvaluator;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class StatsCollector<T extends ExposingInjuryReport> {

    protected final List<ServerCommand> replayCommands;
    protected final StatsCollection collection;
    protected final TurnOverFinder turnOverFinder;
    protected final StatsState<T> state;
    protected final List<Evaluator<?>> evaluators = new ArrayList<>();
    protected final Evaluator<ReportStartHalf> halfEvaluator;

    protected StatsCollector(List<ServerCommand> replayCommands) {
        collection = new StatsCollection(createPlayerActionMapping());
        turnOverFinder = createTurnOverFinder();
        state = createStatsState();
        this.replayCommands = replayCommands;
        halfEvaluator = createHalfEvaluator(state, turnOverFinder, collection );
        evaluators.add(halfEvaluator);
        evaluators.add(new BlockRollEvaluator(collection, state));
        evaluators.add(new KickoffResultEvaluator(collection, state));
        evaluators.add(new MasterChefRollEvaluator(state));
        evaluators.add(new PillingOnEvaluator(state));
        evaluators.add(new PlayerActionEvaluator(collection, state, turnOverFinder));
        evaluators.add(new ReRollEvaluator(state, collection));
        evaluators.add(new ScatterBallEvaluator(state, collection));
        evaluators.add(new SkillRollEvaluator(collection, state));
        evaluators.add(new TimeoutEnforcedEvaluator(collection, state));
        evaluators.add(new WeatherEvaluator(collection));
        evaluators.add(new WizardUseEvaluator(state, collection));
        evaluators.add(new UploadEvaluator(collection));
    }

    private void setAwayTeam(Team team) {
        collection.setAwayTeam(team);
    }

    private void setHomeTeam(Team team) {
        collection.setHomeTeam(team);
        turnOverFinder.addHomePlayers(team);
    }

    public void setGame(Game game) {
        setAwayTeam(game.getTeamAway());
        setHomeTeam(game.getTeamHome());
        state.setGame(game);
        collection.setGame(game);
    }

    public StatsCollection evaluate(String replayId) {
        collection.setReplayId(replayId);

        for (ServerCommand command : replayCommands) {
            if (command instanceof ServerCommandModelSync) {
                //logger.info(new Gson().toJson(command));

                ServerCommandModelSync modelSync = (ServerCommandModelSync) command;

                ReportList reportList = modelSync.getReportList();
                for (IReport report : reportList.getReports()) {
                    try {
                        report.diceStats(collection.getGame()).forEach(collection::addStat);
                        //  logger.info(new Gson().toJson(report));
                        if (!TurnMode.KICKOFF_RETURN.equals(state.getTurnMode())) {
                            if (state.isActionTurn()) {
                                turnOverFinder.add(report);
                            }
                            for (Evaluator<?> evaluator : evaluators) {
                                if (evaluator.handles(report)) {
                                    evaluator.evaluate(report);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
											getLogger().error("Could not evaluate report: {}", report.getId(), e);
                    }
                }

                boolean newTurnValuesSet = false;

                for (ModelChange change : modelSync.getModelChanges().getChanges()) {
                    //logger.info(new Gson().toJson(change));
                    if (ModelChangeId.GAME_SET_HOME_PLAYING == change.getChangeId()) {
                        state.setHomePlaying((boolean) change.getValue());
                        turnOverFinder.setHomeTeamActive(state.isHomePlaying());
                    }

                    if (ModelChangeId.GAME_SET_TURN_MODE == change.getChangeId()) {
                        state.setTurnMode((TurnMode) change.getValue());
                        newTurnValuesSet = true;
                    }

                    if (ModelChangeId.TURN_DATA_SET_TURN_NR == change.getChangeId()) {
                        state.setTurnNumber((int) change.getValue());
                        newTurnValuesSet = true;
                    }
                }

                state.setActionTurn(TurnMode.BLITZ == state.getTurnMode() || TurnMode.REGULAR == state.getTurnMode());

                if (newTurnValuesSet && state.isActionTurn() && state.isNewTurn()) {
                    turnOverFinder.findTurnover().ifPresent(collection::addTurnOver);
                    turnOverFinder.reset();
                    state.setLastTurn(collection.addTurn(state.isHomePlaying(), state.getTurnMode(), state
                      .getTurnNumber()));
                }
            }
        }

        // ugly hack to evaluate master chef rolls of the current active half before the game ends.
        // during the loop master chef rolls are evaluated before a new half is started
        // it can't happen when the report appears as the first half start is not reported but the others are
        // this way is the only remotely consistent one I found so far
        halfEvaluator.evaluate(new ReportStartHalf(0));

        return collection;
    }


    protected abstract PlayerActionMapping createPlayerActionMapping();

    protected abstract StatsState<T> createStatsState();

    protected abstract org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder createTurnOverFinder();

    protected abstract Evaluator<ReportStartHalf> createHalfEvaluator(StatsState<? extends ExposingInjuryReport> state,  TurnOverFinder turnOverFinder, StatsCollection statsCollection);

    protected abstract Logger getLogger();
}
