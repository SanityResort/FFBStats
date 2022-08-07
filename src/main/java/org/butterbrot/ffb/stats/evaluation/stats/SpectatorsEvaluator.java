package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportSpectators;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SpectatorsEvaluator extends Evaluator<ReportSpectators> {

    private final StatsCollection collection;
    private final StatsState state;

    public SpectatorsEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSpectators specs = (ReportSpectators) report;
        collection.getHome().addDoubleRoll(specs.getSpectatorRollHome());
        collection.getAway().addDoubleRoll(specs.getSpectatorRollAway());
        state.setFameHome(specs.getFameHome());
        state.setFameAway(specs.getFameAway());
    }
}
