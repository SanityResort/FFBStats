package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportPilingOn;

public class PillingOnEvaluator extends Evaluator<ReportPilingOn> {

    private StatsState state;

    public PillingOnEvaluator(StatsState state) {
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        if (((ReportPilingOn) report).isUsed()) {
            state.setPoReport(((ReportPilingOn) report));
            state.getInjuries().pollLast();
        }
    }
}
