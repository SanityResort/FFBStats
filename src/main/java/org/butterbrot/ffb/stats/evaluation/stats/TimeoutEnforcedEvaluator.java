package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportTimeoutEnforced;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class TimeoutEnforcedEvaluator extends Evaluator<ReportTimeoutEnforced> {

    private StatsState state;
    private StatsCollection collection;

    public TimeoutEnforcedEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        collection.addTimeOut(state.isHomePlaying());
    }
}
