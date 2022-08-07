package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportBlockRoll;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class BlockRollEvaluator extends Evaluator<ReportBlockRoll> {

    private StatsCollection collection;
    private StatsState state;

    public BlockRollEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportBlockRoll block = (ReportBlockRoll) report;
        collection.addBlockRolls(block.getBlockRoll(), state.getActivePlayer(), block.getChoosingTeamId(), state.isBlockRerolled());
        state.setCurrentBlockRoll(block);
        state.setLastReportWasBlockRoll(true);
    }
}
