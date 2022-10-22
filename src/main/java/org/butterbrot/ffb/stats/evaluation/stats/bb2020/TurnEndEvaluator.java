package org.butterbrot.ffb.stats.evaluation.stats.bb2020;

import com.fumbbl.ffb.HeatExhaustion;
import com.fumbbl.ffb.KnockoutRecovery;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2020.ReportTurnEnd;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.adapter.bb2020.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.springframework.util.StringUtils;

public class TurnEndEvaluator extends Evaluator<ReportTurnEnd> {

	private final StatsCollection collection;
	private final StatsState<ReportPoInjury> state;

	public TurnEndEvaluator(StatsCollection collection, StatsState<ReportPoInjury> state) {
		this.collection = collection;
		this.state = state;
	}

	@Override
	public void evaluate(IReport report) {
		state.setBallScatters(false);
		ReportTurnEnd turn = (ReportTurnEnd) report;
		if (StringUtils.hasLength(turn.getPlayerIdTouchdown())) {
			collection.addTouchdown(turn.getPlayerIdTouchdown());
		}

		if (ArrayTool.isProvided(turn.getKnockoutRecoveries())) {
			for (KnockoutRecovery recovery : turn.getKnockoutRecoveries()) {
				collection.addKoRoll(recovery.getRoll(), recovery.getPlayerId());
			}
		}

		if (ArrayTool.isProvided(turn.getHeatExhaustions())) {
			for (HeatExhaustion exhaustion : turn.getHeatExhaustions()) {
				collection.addHeatRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
			}
		}

		collection.addArmourAndInjuryStats(state.getInjuries());
		state.getInjuries().clear();
	}
}
