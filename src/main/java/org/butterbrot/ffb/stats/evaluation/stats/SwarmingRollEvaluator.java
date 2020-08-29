package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportKickoffExtraReRoll;
import com.balancedbytes.games.ffb.report.ReportSwarmingRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SwarmingRollEvaluator extends Evaluator<ReportSwarmingRoll> {

    private StatsCollection collection;

    public SwarmingRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSwarmingRoll swarmingRoll = (ReportSwarmingRoll) report;
        collection.addSingleRoll(swarmingRoll.getAmount() * 2, swarmingRoll.getTeamId());
    }
}
