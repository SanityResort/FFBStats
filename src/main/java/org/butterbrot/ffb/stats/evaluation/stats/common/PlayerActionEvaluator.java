package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportPlayerAction;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class PlayerActionEvaluator extends Evaluator<ReportPlayerAction> {

    private StatsState state;
    private TurnOverFinder turnOverFinder;
    private StatsCollection collection;

    public PlayerActionEvaluator(StatsCollection collection, StatsState state, TurnOverFinder turnOverFinder) {
        this.state = state;
        this.turnOverFinder = turnOverFinder;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        state.setBallScatters(false);
        state.setLastReportWasBlockRoll(false);
        state.setBlockRerolled(false);
        ReportPlayerAction action = ((ReportPlayerAction) report);
        state.setCurrentBlockRoll(null);
        state.setPoReport(null);
        turnOverFinder.reset();
        turnOverFinder.add(action);
        collection.addPlayerActon(action.getPlayerAction(), action.getActingPlayerId());
        switch (action.getPlayerAction()) {
            case BLITZ:
            case BLITZ_MOVE:
            case BLOCK:
            case MULTIPLE_BLOCK:
                state.setActivePlayer(action.getActingPlayerId());
                break;
            case MOVE:
                state.setActivePlayer(action.getActingPlayerId());
                break;
            default:
                state.setActivePlayer(null);
        }
    }
}
