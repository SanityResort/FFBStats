package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportTimeoutEnforced;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class TimeoutEnforcedEvaluator extends Evaluator<ReportTimeoutEnforced> {

    private final StatsState state;
    private final StatsCollection collection;

    public TimeoutEnforcedEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        collection.addTimeOut(state.isHomePlaying());
    }
}
