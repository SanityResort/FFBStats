package org.butterbrot.ffb.stats.evaluation.stats.bb2020;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2020.ReportKickoffOfficiousRef;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffOfficiousRefEvaluator extends Evaluator<ReportKickoffOfficiousRef> {

    private final StatsCollection collection;

    public KickoffOfficiousRefEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportKickoffOfficiousRef ref = (ReportKickoffOfficiousRef) report;
        collection.addKickOffRolls(new int[]{ref.getRollHome()}, new int[]{ref.getRollAway()});
    }
}
