package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportPlayerAction;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class PlayerActionEvaluator extends Evaluator<ReportPlayerAction> {

    private final StatsState<? extends ExposingInjuryReport> state;
    private final TurnOverFinder turnOverFinder;
    private final StatsCollection collection;

    public PlayerActionEvaluator(StatsCollection collection, StatsState<? extends ExposingInjuryReport> state, TurnOverFinder turnOverFinder) {
        this.state = state;
        this.turnOverFinder = turnOverFinder;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportPlayerAction action = ((ReportPlayerAction) report);
        state.setBallScatters(false);
        state.setLastReportWasBlockRoll(false);
        state.setBlockRerolled(false);
        state.setCurrentBlockRoll(null);
        state.setPoReport(null);
        state.setActivePlayer(action.getActingPlayerId());
        turnOverFinder.reset();
        turnOverFinder.add(action);
        turnOverFinder.setActivePlayer(action.getActingPlayerId());
        collection.addPlayerActon(action.getPlayerAction(), action.getActingPlayerId());

    }
}
