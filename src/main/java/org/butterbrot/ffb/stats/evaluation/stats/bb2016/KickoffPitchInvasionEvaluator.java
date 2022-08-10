package org.butterbrot.ffb.stats.evaluation.stats.bb2016;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportKickoffPitchInvasion;
import org.butterbrot.ffb.stats.adapter.bb2016.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffPitchInvasionEvaluator extends Evaluator<ReportKickoffPitchInvasion> {

    private final StatsCollection collection;
    private final StatsState<ReportPoInjury> state;

    public KickoffPitchInvasionEvaluator(StatsCollection collection, StatsState<ReportPoInjury> state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportKickoffPitchInvasion invasion = (ReportKickoffPitchInvasion) report;
        for (int roll : invasion.getRollsHome()) {
            int minimumRoll = 6 - state.getFameAway();
            if (roll > 0) {
                if (roll >= minimumRoll) {
                    collection.getAway().addSuccessRoll(report.getId(), minimumRoll);
                }
            }
        }
        for (int roll : invasion.getRollsAway()) {
            int minimumRoll = 6 - state.getFameHome();
            if (roll > 0) {
                if (roll >= minimumRoll) {
                    collection.getHome().addSuccessRoll(report.getId(), minimumRoll);
                }
            }
        }
        collection.addKickOffRolls(invasion.getRollsHome(), invasion.getRollsAway());
    }
}
