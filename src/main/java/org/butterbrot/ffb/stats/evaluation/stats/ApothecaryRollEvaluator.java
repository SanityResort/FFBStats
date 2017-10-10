package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportApothecaryRoll;
import com.balancedbytes.games.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ApothecaryRollEvaluator extends Evaluator<ReportApothecaryRoll> {

    private StatsCollection collection;

    public ApothecaryRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportApothecaryRoll apoRoll = (ReportApothecaryRoll) report;
        if (ArrayTool.isProvided(apoRoll.getCasualtyRoll())) {
            collection.addSingleOpposingRoll(apoRoll.getCasualtyRoll()[0], apoRoll.getPlayerId());
        }
        collection.addApoUse(apoRoll.getPlayerId());

    }
}
