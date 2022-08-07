package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportReRoll;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ReRollEvaluator extends Evaluator<ReportReRoll> {

    private final StatsState state;
    private final StatsCollection collection;

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
        if (ReRollSources.TEAM_RE_ROLL == reportReRoll.getReRollSource() || ReRollSources.LEADER == reportReRoll.getReRollSource()) {
            collection.addReroll(reportReRoll.getPlayerId());
        }
    }
}
