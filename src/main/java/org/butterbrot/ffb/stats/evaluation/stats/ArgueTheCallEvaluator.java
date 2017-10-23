package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportArgueTheCallRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ArgueTheCallEvaluator extends Evaluator<ReportArgueTheCallRoll> {

    private StatsCollection collection;

    public ArgueTheCallEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportArgueTheCallRoll argue = (ReportArgueTheCallRoll) report;
        collection.addSingleRoll(argue.getRoll(), argue.getPlayerId());
    }
}
