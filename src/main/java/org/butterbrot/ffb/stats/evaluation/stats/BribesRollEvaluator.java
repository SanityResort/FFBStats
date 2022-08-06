package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportBribesRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class BribesRollEvaluator extends Evaluator<ReportBribesRoll> {

    private StatsCollection collection;

    public BribesRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportBribesRoll bribe = (ReportBribesRoll) report;
        collection.addBribe(bribe.getPlayerId());
    }
}
