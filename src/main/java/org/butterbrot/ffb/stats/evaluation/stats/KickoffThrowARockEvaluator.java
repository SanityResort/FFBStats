package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportKickoffThrowARock;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffThrowARockEvaluator extends Evaluator<ReportKickoffThrowARock> {

    private final StatsCollection collection;

    public KickoffThrowARockEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportKickoffThrowARock throwRock = (ReportKickoffThrowARock) report;
        collection.getAway().addSingleRoll(throwRock.getRollAway());
        collection.getHome().addSingleRoll(throwRock.getRollHome());
        collection.addKickOffRolls(new int[]{throwRock.getRollHome()}, new int[]{throwRock.getRollAway()});
    }
}
