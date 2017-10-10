package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportStartHalf;

public class StartHalfEvaluator extends Evaluator<ReportStartHalf> {

    private StatsState state;

    public StartHalfEvaluator(StatsState state) {
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        if (((ReportStartHalf) report).getHalf() == 2) {
            state.setStartSecondHalf(true);
        } else if (((ReportStartHalf) report).getHalf() > 2) {
            state.setStartOvertime(true);
            state.setStartSecondHalf(false);
        }
    }
}
