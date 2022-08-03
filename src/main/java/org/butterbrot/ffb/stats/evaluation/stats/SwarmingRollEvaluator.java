package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportSwarmingRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SwarmingRollEvaluator extends Evaluator<ReportSwarmingRoll> {

    private StatsCollection collection;

    public SwarmingRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSwarmingRoll swarmingRoll = (ReportSwarmingRoll) report;
        collection.addSingleRollWithoutDrive(swarmingRoll.getAmount() * 2, swarmingRoll.getTeamId());
    }
}
