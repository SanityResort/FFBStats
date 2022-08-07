package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportKickoffExtraReRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffExtraReRollEvaluator extends Evaluator<ReportKickoffExtraReRoll> {

    private final StatsCollection collection;

    public KickoffExtraReRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportKickoffExtraReRoll extraReRoll = (ReportKickoffExtraReRoll) report;
        collection.addKickOffRolls(new int[]{extraReRoll.getRollHome()}, new int[]{extraReRoll.getRollAway()});
    }
}
