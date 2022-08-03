package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportStandUpRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class StandUpRollEvaluator extends Evaluator<ReportStandUpRoll> {

    private StatsCollection collection;

    public StandUpRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportStandUpRoll standUpRoll = (ReportStandUpRoll) report;
        collection.addSingleRoll(standUpRoll.getRoll(), standUpRoll.getPlayerId());
        if (standUpRoll.isSuccessful()) {
            collection.addSuccessRoll(standUpRoll.getPlayerId(), standUpRoll.getId(), 4);
        }
    }
}
