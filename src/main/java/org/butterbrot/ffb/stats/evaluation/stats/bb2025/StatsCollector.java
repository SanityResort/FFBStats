package org.butterbrot.ffb.stats.evaluation.stats.bb2025;

import com.fumbbl.ffb.net.commands.ServerCommand;
import com.fumbbl.ffb.report.ReportStartHalf;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.adapter.bb2025.PlayerActionMapping;
import org.butterbrot.ffb.stats.adapter.mixed.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.mixed.ApothecaryRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.mixed.KickoffExtraReRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.mixed.StartHalfEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.mixed.TurnEndEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.bb2020.KickoffOfficiousRefEvaluator;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StatsCollector extends org.butterbrot.ffb.stats.evaluation.stats.StatsCollector<ReportPoInjury> {

    private static final Logger logger = LoggerFactory.getLogger(StatsCollector.class);

    @SuppressWarnings("unused")
    public StatsCollector() {
        this(new ArrayList<>());
    }

    public StatsCollector(List<ServerCommand> replayCommands) {
        super(replayCommands);
        evaluators.add(new ApothecaryRollEvaluator(collection));
        evaluators.add(new InjuryEvaluator(collection, state));
        evaluators.add(new KickoffExtraReRollEvaluator(collection));
        evaluators.add(new KickoffOfficiousRefEvaluator(collection));
        evaluators.add(new TurnEndEvaluator(collection, state));
        evaluators.add(new CheeringFansEvaluator(collection));
    }

    @Override
    protected org.butterbrot.ffb.stats.adapter.PlayerActionMapping createPlayerActionMapping() {
        return new PlayerActionMapping();
    }

    @Override
    protected StatsState<ReportPoInjury> createStatsState() {
        return new org.butterbrot.ffb.stats.evaluation.stats.mixed.StatsState();
    }

    @Override
    protected TurnOverFinder createTurnOverFinder() {
        return new org.butterbrot.ffb.stats.evaluation.turnover.mixed.TurnOverFinder();
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
