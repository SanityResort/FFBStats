package org.butterbrot.ffb.stats.evaluation.stats.bb2016;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportMasterChefRoll;
import com.fumbbl.ffb.report.ReportStartHalf;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class StartHalfEvaluator extends Evaluator<ReportStartHalf> {

    private final StatsState<? extends ExposingInjuryReport> state;
    private final TurnOverFinder turnOverFinder;
    private final StatsCollection collection;

    public StartHalfEvaluator(StatsState<? extends ExposingInjuryReport> state, TurnOverFinder turnOverFinder, StatsCollection collection) {
        this.state = state;
        this.turnOverFinder = turnOverFinder;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {

        if (state.getChefRoll() != null) {
            ReportMasterChefRoll chef = state.getChefRoll();
            for (int roll : chef.getMasterChefRoll()) {
                collection.addChefRoll(roll, chef.getTeamId(), 4);
            }
        }

        state.setChefRoll(null);

        if (((ReportStartHalf) report).getHalf() == 2) {
            turnOverFinder.findTurnover().ifPresent(collection::addTurnOver);
            turnOverFinder.reset();
            state.setStartSecondHalf(true);
        } else if (((ReportStartHalf) report).getHalf() > 2) {
            turnOverFinder.findTurnover().ifPresent(collection::addTurnOver);
            turnOverFinder.reset();
            state.setStartOvertime(true);
            state.setStartSecondHalf(false);
        }
    }
}
