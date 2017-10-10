package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportTimeoutEnforced;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class TimeoutEnforcedEvaluator extends Evaluator<ReportTimeoutEnforced> {

    private StatsState state;
    private StatsCollection collection;

    public TimeoutEnforcedEvaluator(StatsState state) {
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        collection.addTimeOut(state.isHomePlaying());
    }
}
