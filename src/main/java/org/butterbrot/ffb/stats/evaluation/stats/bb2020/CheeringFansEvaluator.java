package org.butterbrot.ffb.stats.evaluation.stats.bb2020;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2020.ReportCheeringFans;
import com.fumbbl.ffb.report.bb2020.ReportKickoffExtraReRoll;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class CheeringFansEvaluator extends Evaluator<ReportCheeringFans> {

    private final StatsCollection collection;

    public CheeringFansEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportCheeringFans cheeringFans = (ReportCheeringFans) report;
        collection.addKickOffRolls(new int[]{cheeringFans.getRollHome()}, new int[]{cheeringFans.getRollAway()});
    }
}
