package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportKickoffThrowARock;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffThrowARockEvaluator extends Evaluator<ReportKickoffThrowARock> {

    private StatsCollection collection;

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
