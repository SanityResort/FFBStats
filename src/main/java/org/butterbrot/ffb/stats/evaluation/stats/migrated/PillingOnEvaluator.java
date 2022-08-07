package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportPilingOn;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;

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
