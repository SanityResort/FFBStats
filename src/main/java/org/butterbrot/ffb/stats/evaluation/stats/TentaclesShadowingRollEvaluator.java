package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportTentaclesShadowingRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class TentaclesShadowingRollEvaluator extends Evaluator<ReportTentaclesShadowingRoll> {

    private StatsCollection collection;

    public TentaclesShadowingRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportTentaclesShadowingRoll tentShadow = (ReportTentaclesShadowingRoll) report;
        collection.addDoubleOpposingRoll(tentShadow.getRoll(), tentShadow.getDefenderId());
    }
}
