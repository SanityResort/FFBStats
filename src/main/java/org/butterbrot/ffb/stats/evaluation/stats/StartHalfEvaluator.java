package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportStartHalf;
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
