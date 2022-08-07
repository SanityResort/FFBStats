package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportSkillRoll;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SkillRollEvaluator extends Evaluator<ReportSkillRoll> {

	private final StatsCollection collection;
	private final StatsState state;

	public SkillRollEvaluator(StatsCollection collection, StatsState state) {
		this.collection = collection;
		this.state = state;
	}

	@Override
	public void evaluate(IReport report) {
		ReportSkillRoll skillReport = ((ReportSkillRoll) report);
		if (skillReport.getRoll() == 0 && ReportId.DODGE_ROLL == skillReport.getId()) {
			// skill roll was 0 for a dodge, which means the dodge failed due to diving tackle and we have to remove the previously reported success and report the failure instead.
			collection.addFailedRoll(skillReport.getPlayerId(), skillReport.getId(), skillReport.getMinimumRoll());
			collection.removeSuccessRoll(skillReport.getPlayerId(), skillReport.getId(), skillReport.getMinimumRoll() - 2);
		}

		// set the block roll to null, when some other skill roll was made, like dodge or gfi.
		// this should take care that a fanatic falling down due to a gfi is not counted as a failed block.
		state.setCurrentBlockRoll(null);
	}
}
