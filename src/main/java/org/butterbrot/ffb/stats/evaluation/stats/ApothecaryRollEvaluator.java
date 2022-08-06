package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportApothecaryRoll;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ApothecaryRollEvaluator extends Evaluator<ReportApothecaryRoll> {

    private StatsCollection collection;

    public ApothecaryRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportApothecaryRoll apoRoll = (ReportApothecaryRoll) report;
        collection.addApoUse(apoRoll.getPlayerId());
    }
}
