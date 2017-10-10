package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportConfusionRoll;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SkillRollEvaluator extends Evaluator<ReportSkillRoll> {

    private StatsCollection collection;
    private StatsState state;

    public SkillRollEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSkillRoll skillReport = ((ReportSkillRoll) report);
        if (skillReport.getRoll() > 0) {
            collection.addSingleRoll(skillReport.getRoll(), skillReport.getPlayerId());
            if (skillReport.isSuccessful()) {
                collection.addSuccessRoll(skillReport.getPlayerId(), skillReport.getId(), skillReport.getMinimumRoll());
            } else {
                collection.addFailedRoll(skillReport.getPlayerId(), skillReport.getId(), skillReport.getMinimumRoll());
            }
        } else if (ReportId.DODGE_ROLL == skillReport.getId()) {
            // skill roll was 0 for a dodge, which means the dodge failed due to diving tackle and we have to remove the previously reported success and report the failure instead.
            collection.addFailedRoll(skillReport.getPlayerId(), skillReport.getId(), skillReport.getMinimumRoll());
            collection.removeSuccessRoll(skillReport.getPlayerId(), skillReport.getId(), skillReport.getMinimumRoll() - 2);
        }

        if (skillReport.isSuccessful()) {
            switch (skillReport.getId()) {
                case BLOOD_LUST_ROLL:
                    collection.addBloodLust(skillReport.getPlayerId());
                    break;
                case CONFUSION_ROLL:
                    ReportConfusionRoll reportConfusionRoll = (ReportConfusionRoll) skillReport;
                    switch (reportConfusionRoll.getConfusionSkill()) {
                        case TAKE_ROOT:
                            collection.addTakeRoot(reportConfusionRoll.getPlayerId());
                            break;
                        case WILD_ANIMAL:
                            collection.addWildAnimal(reportConfusionRoll.getPlayerId());
                            break;
                        default:
                            collection.addConfusion(reportConfusionRoll.getPlayerId());
                    }
                    break;
            }
        } else {
            switch (skillReport.getId()) {
                case HYPNOTIC_GAZE_ROLL:
                    collection.addHypnoticGaze(skillReport.getPlayerId());
                    break;
            }
        }

        // set the block roll to null, when some other skill roll was made, like dodge or gfi.
        // this should take care that a fanatic falling down due to a gfi is not counted as a failed block.
        state.setCurrentBlockRoll(null);
    }
}
