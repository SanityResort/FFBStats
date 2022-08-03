package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportMasterChefRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class MasterChefRollEvaluator extends Evaluator<ReportMasterChefRoll> {

    private StatsState state;

    public MasterChefRollEvaluator(StatsState state) {
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        state.setChefRoll((ReportMasterChefRoll) report);
    }
}
