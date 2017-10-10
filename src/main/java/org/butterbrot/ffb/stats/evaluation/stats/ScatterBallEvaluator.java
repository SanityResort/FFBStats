package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportScatterBall;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class ScatterBallEvaluator extends Evaluator<ReportScatterBall> {

    private StatsState state;
    private StatsCollection collection;

    public ScatterBallEvaluator(StatsState state, StatsCollection collection) {
        this.state = state;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        if ( !state.isBallScatters()) {
            collection.addScatter(state.isHomePlaying());
            state.setBallScatters(true);
        }
    }
}
