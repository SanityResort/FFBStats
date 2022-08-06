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
import org.butterbrot.ffb.stats.evaluation.stats.migrated.ApothecaryRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.BlockRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StatsCollector {

    private static final Logger logger = LoggerFactory.getLogger(StatsCollector.class);

    private final List<ServerCommand> replayCommands;
    private final StatsCollection collection = new StatsCollection();
    private final TurnOverFinder turnOverFinder = new TurnOverFinder();
    private final StatsState state = new StatsState();
    private final List<Evaluator<?>> evaluators = new ArrayList<>();
    private final StartHalfEvaluator halfEvaluator;
    private Game game;

    public StatsCollector() {
        this(new ArrayList<>());
    }

    public StatsCollector(List<ServerCommand> replayCommands) {
        this.replayCommands = replayCommands;
        halfEvaluator = new StartHalfEvaluator(state, turnOverFinder, collection );
        evaluators.add(new ApothecaryRollEvaluator(collection));
        evaluators.add(new BlockRollEvaluator(collection, state));
        evaluators.add(new BribesRollEvaluator(collection));
        evaluators.add(new InjuryEvaluator(collection, state));
        evaluators.add(new KickoffExtraReRollEvaluator(collection));
        evaluators.add(new SwarmingRollEvaluator(collection));
        evaluators.add(new KickoffPitchInvasionEvaluator(collection, state));
        evaluators.add(new KickoffResultEvaluator(collection, state));
        evaluators.add(new KickoffThrowARockEvaluator(collection));
        evaluators.add(new MasterChefRollEvaluator(state));
        evaluators.add(new PenaltyShootoutEvaluator(collection));
        evaluators.add(new PillingOnEvaluator(state));
        evaluators.add(new PlayerActionEvaluator(collection, state, turnOverFinder));
        evaluators.add(new ReRollEvaluator(state, collection));
        evaluators.add(new ReportRiotousRookiesEvaluator(collection));
        evaluators.add(new ScatterBallEvaluator(state, collection));
        evaluators.add(new SkillRollEvaluator(collection, state));
        evaluators.add(new SpecialEffectRollEvaluator(collection));
        evaluators.add(new SpectatorsEvaluator(collection, state));
        evaluators.add(new StandUpRollEvaluator(collection));
        evaluators.add(halfEvaluator);
        evaluators.add(new TentaclesShadowingRollEvaluator(collection));
        evaluators.add(new TimeoutEnforcedEvaluator(collection, state));
        evaluators.add(new TurnEndEvaluator(collection, state));
        evaluators.add(new WeatherEvaluator(collection));
        evaluators.add(new WinnigsRollEvaluator(collection));
        evaluators.add(new WizardUseEvaluator(state, collection));
    }

    private void setAwayTeam(Team team) {
        collection.setAwayTeam(team);
    }

    private void setHomeTeam(Team team) {
        collection.setHomeTeam(team);
        turnOverFinder.addHomePlayers(team);
    }

    public void setGame(Game game) {
        this.game = game;
        setAwayTeam(game.getTeamAway());
        setHomeTeam(game.getTeamHome());
        state.setGame(game);
        collection.setGame(game);
    }

    public List<ServerCommand> getReplayCommands() {
        return replayCommands;
    }

    public StatsCollection evaluate(String replayId) {
        collection.setReplayId(replayId);

        for (ServerCommand command : replayCommands) {
            if (command instanceof ServerCommandModelSync) {
                //logger.info(new Gson().toJson(command));

                ServerCommandModelSync modelSync = (ServerCommandModelSync) command;

                ReportList reportList = modelSync.getReportList();
                for (IReport report : reportList.getReports()) {
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

                if (newTurnValuesSet && state.isActionTurn() && state.isNewTurn()){
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
}
