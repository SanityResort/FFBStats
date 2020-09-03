package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportRiotousRookies;
import com.balancedbytes.games.ffb.report.ReportSwarmingRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ReportRiotousRookiesEvaluator extends Evaluator<ReportRiotousRookies> {

    private StatsCollection collection;

    public ReportRiotousRookiesEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportRiotousRookies rookiesRoll = (ReportRiotousRookies) report;
        for (int roll: rookiesRoll.getRoll()) {
            collection.addSingleRoll(roll * 2, rookiesRoll.getTeamId());
        }
    }
}
