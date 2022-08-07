package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportTentaclesShadowingRoll;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class TentaclesShadowingRollEvaluator extends Evaluator<ReportTentaclesShadowingRoll> {

    private final StatsCollection collection;

    public TentaclesShadowingRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportTentaclesShadowingRoll tentShadow = (ReportTentaclesShadowingRoll) report;
        collection.addDoubleOpposingRoll(tentShadow.getRoll(), tentShadow.getDefenderId());
    }
}
