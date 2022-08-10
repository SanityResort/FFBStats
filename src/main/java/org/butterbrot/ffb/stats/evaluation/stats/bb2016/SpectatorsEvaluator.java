package org.butterbrot.ffb.stats.evaluation.stats.bb2016;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportSpectators;
import org.butterbrot.ffb.stats.adapter.bb2016.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;

public class SpectatorsEvaluator extends Evaluator<ReportSpectators> {

    private final StatsState<ReportPoInjury> state;

    public SpectatorsEvaluator(StatsState<ReportPoInjury> state) {
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSpectators specs = (ReportSpectators) report;
        state.setFameHome(specs.getFameHome());
        state.setFameAway(specs.getFameAway());
    }
}
