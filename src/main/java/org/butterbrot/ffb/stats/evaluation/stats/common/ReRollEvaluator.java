package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportReRoll;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ReRollEvaluator extends Evaluator<ReportReRoll> {

    private final StatsState<? extends ExposingInjuryReport> state;
    private final StatsCollection collection;

    public ReRollEvaluator(StatsState<? extends ExposingInjuryReport> state, StatsCollection collection) {
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
