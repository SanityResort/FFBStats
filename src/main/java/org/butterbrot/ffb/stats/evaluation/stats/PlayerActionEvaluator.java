package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;

public class PlayerActionEvaluator extends Evaluator<ReportPlayerAction> {

    private StatsState state;
    private TurnOverFinder turnOverFinder;

    public PlayerActionEvaluator(StatsState state, TurnOverFinder turnOverFinder) {
        this.state = state;
        this.turnOverFinder = turnOverFinder;
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
