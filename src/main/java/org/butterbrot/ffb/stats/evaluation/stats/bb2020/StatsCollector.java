package org.butterbrot.ffb.stats.evaluation.stats.bb2020;

import com.fumbbl.ffb.TurnMode;
import com.fumbbl.ffb.model.change.ModelChange;
import com.fumbbl.ffb.model.change.ModelChangeId;
import com.fumbbl.ffb.net.commands.ServerCommand;
import com.fumbbl.ffb.net.commands.ServerCommandModelSync;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportList;
import com.fumbbl.ffb.report.ReportStartHalf;
import org.butterbrot.ffb.stats.adapter.bb2020.PlayerActionMapping;
import org.butterbrot.ffb.stats.adapter.bb2020.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StatsCollector extends org.butterbrot.ffb.stats.evaluation.stats.StatsCollector<ReportPoInjury> {

    private static final Logger logger = LoggerFactory.getLogger(StatsCollector.class);

    @SuppressWarnings("unused")
    public StatsCollector() {
        this(new ArrayList<>());
    }

    public StatsCollector(List<ServerCommand> replayCommands) {
        super(replayCommands);
        evaluators.add(new ApothecaryRollEvaluator(collection));
        evaluators.add(new InjuryEvaluator(collection, state));
        evaluators.add(new KickoffExtraReRollEvaluator(collection));
        evaluators.add(new KickoffOfficiousRefEvaluator(collection));
        evaluators.add(new TurnEndEvaluator(collection, state));
        evaluators.add(new CheeringFansEvaluator(collection));
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
                        logger.error("Could not evaluate report: " + report.getId(), e);
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

    @Override
    protected org.butterbrot.ffb.stats.adapter.PlayerActionMapping createPlayerActionMapping() {
        return new PlayerActionMapping();
    }

    @Override
    protected StatsState<ReportPoInjury> createStatsState() {
        return new org.butterbrot.ffb.stats.evaluation.stats.bb2020.StatsState();
    }

    @Override
    protected TurnOverFinder createTurnOverFinder() {
        return new org.butterbrot.ffb.stats.evaluation.turnover.bb2020.TurnOverFinder();
    }
}
