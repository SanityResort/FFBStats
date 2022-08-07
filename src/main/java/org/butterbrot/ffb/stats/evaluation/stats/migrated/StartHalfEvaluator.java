package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportMasterChefRoll;
import com.fumbbl.ffb.report.ReportStartHalf;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.Evaluator;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class StartHalfEvaluator extends Evaluator<ReportStartHalf> {

    private StatsState state;
    private TurnOverFinder turnOverFinder;
    private StatsCollection collection;

    public StartHalfEvaluator(StatsState state, TurnOverFinder turnOverFinder, StatsCollection collection) {
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
            turnOverFinder.findTurnover().ifPresent(turnOver -> collection.addTurnOver(turnOver));
            turnOverFinder.reset();
            state.setStartSecondHalf(true);
        } else if (((ReportStartHalf) report).getHalf() > 2) {
            turnOverFinder.findTurnover().ifPresent(turnOver -> collection.addTurnOver(turnOver));
            turnOverFinder.reset();
            state.setStartOvertime(true);
            state.setStartSecondHalf(false);
        }
    }
}
