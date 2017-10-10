package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportKickoffResult;
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
