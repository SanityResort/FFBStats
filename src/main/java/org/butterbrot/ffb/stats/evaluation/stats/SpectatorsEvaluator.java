package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportSpectators;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SpectatorsEvaluator extends Evaluator<ReportSpectators> {

    private StatsCollection collection;
    private StatsState state;

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
