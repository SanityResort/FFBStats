package org.butterbrot.ffb.stats.evaluation.stats.bb2016;

import com.fumbbl.ffb.net.commands.ServerCommand;
import com.fumbbl.ffb.report.ReportStartHalf;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.adapter.bb2016.ReportPoInjury;
import org.butterbrot.ffb.stats.adapter.mixed.PlayerActionMapping;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.common.UploadEvaluator;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StatsCollector extends org.butterbrot.ffb.stats.evaluation.stats.StatsCollector<ReportPoInjury> {

	private static final Logger logger = LoggerFactory.getLogger(StatsCollector.class);

	public StatsCollector(List<ServerCommand> replayCommands) {
		super(replayCommands);
		evaluators.add(new ApothecaryRollEvaluator(collection));
		evaluators.add(new InjuryEvaluator(collection, state));
		evaluators.add(new KickoffExtraReRollEvaluator(collection));
		evaluators.add(new KickoffPitchInvasionEvaluator(collection, state));
		evaluators.add(new KickoffThrowARockEvaluator(collection));
		evaluators.add(new SpectatorsEvaluator(state));
		evaluators.add(new TurnEndEvaluator(collection, state));
		evaluators.add(new UploadEvaluator(collection));
	}

	@Override
	protected org.butterbrot.ffb.stats.adapter.PlayerActionMapping createPlayerActionMapping() {
		return new PlayerActionMapping();
	}

	@Override
	protected StatsState<ReportPoInjury> createStatsState() {
		return new org.butterbrot.ffb.stats.evaluation.stats.bb2016.StatsState();
	}

	@Override
	protected TurnOverFinder createTurnOverFinder() {
		return new org.butterbrot.ffb.stats.evaluation.turnover.bb2016.TurnOverFinder();
	}

	@Override
	protected Evaluator<ReportStartHalf> createHalfEvaluator(StatsState<? extends ExposingInjuryReport> state, TurnOverFinder turnOverFinder, StatsCollection statsCollection) {
		return new StartHalfEvaluator(state, turnOverFinder, statsCollection);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
