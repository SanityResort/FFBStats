package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportBribesRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class BribesRollEvaluator extends Evaluator<ReportBribesRoll> {

    private StatsCollection collection;

    public BribesRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportBribesRoll bribe = (ReportBribesRoll) report;
        collection.addSingleRoll(bribe.getRoll(), bribe.getPlayerId());
        collection.addBribe(bribe.getPlayerId());
        if (bribe.isSuccessful()) {
            collection.addSuccessRoll(bribe.getPlayerId(), bribe.getId(), 2);
        }
    }
}
