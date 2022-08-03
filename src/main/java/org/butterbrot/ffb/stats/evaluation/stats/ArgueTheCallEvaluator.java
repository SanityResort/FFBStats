package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportArgueTheCallRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ArgueTheCallEvaluator extends Evaluator<ReportArgueTheCallRoll> {

    private final StatsCollection collection;

    public ArgueTheCallEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportArgueTheCallRoll argue = (ReportArgueTheCallRoll) report;
        collection.addSingleRoll(argue.getRoll(), argue.getPlayerId());
    }
}
