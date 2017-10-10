package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ReRollEvaluator extends Evaluator<ReportReRoll> {

    private StatsState state;
    private StatsCollection collection;

    public ReRollEvaluator(StatsState state, StatsCollection collection) {
        this.state = state;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        if (state.isLastReportWasBlockRoll()) {
            state.setLastReportWasBlockRoll(false);
            state.setBlockRerolled(true);
        }
        ReportReRoll reportReRoll = (ReportReRoll) report;
        if (ReRollSource.TEAM_RE_ROLL == reportReRoll.getReRollSource() || ReRollSource.LEADER == reportReRoll.getReRollSource()) {
            collection.addReroll(reportReRoll.getPlayerId());
        }
    }
}
