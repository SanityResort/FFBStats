package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;

import com.fumbbl.ffb.report.bb2016.ReportFanFactorRollPostMatch;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class FanFactorRollEvaluator extends Evaluator<ReportFanFactorRollPostMatch> {

    private final StatsCollection collection;

    public FanFactorRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportFanFactorRollPostMatch ffReport = ((ReportFanFactorRollPostMatch) report);
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
