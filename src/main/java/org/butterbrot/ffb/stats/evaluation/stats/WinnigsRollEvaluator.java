package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportWinningsRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class WinnigsRollEvaluator extends Evaluator<ReportWinningsRoll> {

    private StatsCollection collection;

    public WinnigsRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportWinningsRoll winnings = (ReportWinningsRoll) report;
        if (winnings.getWinningsRollHome() > 0) {
            collection.getHome().addSingleRoll(winnings.getWinningsRollHome());
        }
        if (winnings.getWinningsRollAway() > 0) {
            collection.getAway().addSingleRoll(winnings.getWinningsRollAway());
        }
        collection.setFinished(true);

    }
}
