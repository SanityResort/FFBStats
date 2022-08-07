package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportApothecaryRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ApothecaryRollEvaluator extends Evaluator<ReportApothecaryRoll> {

    private StatsCollection collection;

    public ApothecaryRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        // do not move this to the reportIdMapper as the usage is not recorded for the same team as the potential die roll
        ReportApothecaryRoll apoRoll = (ReportApothecaryRoll) report;
        collection.addApoUse(apoRoll.getPlayerId());
    }
}
