package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportWinningsRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class WinnigsRollEvaluator extends Evaluator<ReportWinningsRoll> {

    private final StatsCollection collection;

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
