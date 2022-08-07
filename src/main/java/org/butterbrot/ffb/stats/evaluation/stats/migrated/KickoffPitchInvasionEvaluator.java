package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportKickoffPitchInvasion;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class KickoffPitchInvasionEvaluator extends Evaluator<ReportKickoffPitchInvasion> {

    private final StatsCollection collection;
    private final StatsState state;

    public KickoffPitchInvasionEvaluator(StatsCollection collection, StatsState state) {
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
