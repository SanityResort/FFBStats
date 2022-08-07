package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportKickoffResult;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffResultEvaluator extends Evaluator<ReportKickoffResult> {

    private StatsCollection collection;
    private StatsState state;

    public KickoffResultEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportKickoffResult kickoff = (ReportKickoffResult) report;
        collection.addDrive(kickoff.getKickoffResult());
    }
}
