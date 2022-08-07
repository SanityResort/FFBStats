package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportSpectators;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SpectatorsEvaluator extends Evaluator<ReportSpectators> {

    private final StatsState state;

    public SpectatorsEvaluator(StatsState state) {
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSpectators specs = (ReportSpectators) report;
        state.setFameHome(specs.getFameHome());
        state.setFameAway(specs.getFameAway());
    }
}
