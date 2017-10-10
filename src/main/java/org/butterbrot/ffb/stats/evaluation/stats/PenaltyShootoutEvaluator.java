package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportPenaltyShootout;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class PenaltyShootoutEvaluator extends Evaluator<ReportPenaltyShootout> {

    private StatsCollection collection;

    public PenaltyShootoutEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportPenaltyShootout shootout = (ReportPenaltyShootout) report;
        collection.getAway().addSingleRoll(shootout.getRollAway());
        collection.getHome().addSingleRoll(shootout.getRollHome());
    }
}
