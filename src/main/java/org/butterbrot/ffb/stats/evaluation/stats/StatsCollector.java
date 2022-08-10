package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.net.commands.ServerCommand;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.adapter.PlayerActionMapping;
import org.butterbrot.ffb.stats.evaluation.stats.common.BlockRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.KickoffResultEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.MasterChefRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.PillingOnEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.PlayerActionEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.ReRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.ScatterBallEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.SkillRollEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.StartHalfEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.TimeoutEnforcedEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.WeatherEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.UploadEvaluator;
import org.butterbrot.ffb.stats.evaluation.stats.common.WizardUseEvaluator;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;

import java.util.ArrayList;
import java.util.List;

public abstract class StatsCollector<T extends ExposingInjuryReport> {

    protected final List<ServerCommand> replayCommands;
    protected final StatsCollection collection;
    protected final TurnOverFinder turnOverFinder;
    protected final StatsState<T> state;
    protected final List<Evaluator<?>> evaluators = new ArrayList<>();
    protected final StartHalfEvaluator halfEvaluator;

    protected StatsCollector(List<ServerCommand> replayCommands) {
        collection = new StatsCollection(createPlayerActionMapping());
        turnOverFinder = createTurnOverFinder();
        state = createStatsState();
        this.replayCommands = replayCommands;
        halfEvaluator = new StartHalfEvaluator(state, turnOverFinder, collection );
        evaluators.add(halfEvaluator);
        evaluators.add(new BlockRollEvaluator(collection, state));
        evaluators.add(new KickoffResultEvaluator(collection, state));
        evaluators.add(new MasterChefRollEvaluator(state));
        evaluators.add(new PillingOnEvaluator(state));
        evaluators.add(new PlayerActionEvaluator(collection, state, turnOverFinder));
        evaluators.add(new ReRollEvaluator(state, collection));
        evaluators.add(new ScatterBallEvaluator(state, collection));
        evaluators.add(new SkillRollEvaluator(collection, state));
        evaluators.add(new TimeoutEnforcedEvaluator(collection, state));
        evaluators.add(new WeatherEvaluator(collection));
        evaluators.add(new WizardUseEvaluator(state, collection));
        evaluators.add(new UploadEvaluator(collection));
    }

    private void setAwayTeam(Team team) {
        collection.setAwayTeam(team);
    }

    private void setHomeTeam(Team team) {
        collection.setHomeTeam(team);
        turnOverFinder.addHomePlayers(team);
    }

    public void setGame(Game game) {
        setAwayTeam(game.getTeamAway());
        setHomeTeam(game.getTeamHome());
        state.setGame(game);
        collection.setGame(game);
    }

    public List<ServerCommand> getReplayCommands() {
        return replayCommands;
    }

    public abstract StatsCollection evaluate(String replayId);

    protected abstract PlayerActionMapping createPlayerActionMapping();

    protected abstract StatsState<T> createStatsState();

    protected abstract org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder createTurnOverFinder();
}
