package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportFanFactorRoll;
import com.balancedbytes.games.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class FanFactorRollEvaluator extends Evaluator<ReportFanFactorRoll> {

    private StatsCollection collection;

    public FanFactorRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportFanFactorRoll ffReport = ((ReportFanFactorRoll) report);
        if (ArrayTool.isProvided(ffReport.getFanFactorRollAway())) {
            for (int roll : ffReport.getFanFactorRollAway()) {
                collection.getAway().addSingleRoll(roll);
            }
        }
        if (ArrayTool.isProvided(ffReport.getFanFactorRollHome())) {
            for (int roll : ffReport.getFanFactorRollHome()) {
                collection.getHome().addSingleRoll(roll);
            }
        }
    }
}
