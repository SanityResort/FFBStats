package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportMasterChefRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class MasterChefRollEvaluator extends Evaluator<ReportMasterChefRoll> {

    private StatsCollection collection;

    public MasterChefRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportMasterChefRoll chef = (ReportMasterChefRoll) report;
        for (int roll : chef.getMasterChefRoll()) {
            collection.addSingleRoll(roll, chef.getTeamId());
            if (roll > 3) {
                collection.addSuccessRoll(chef.getTeamId(), chef.getId(), 4);
            }
        }
    }
}
