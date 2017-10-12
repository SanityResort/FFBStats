package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.HeatExhaustion;
import com.balancedbytes.games.ffb.KnockoutRecovery;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportApothecaryRoll;
import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import com.balancedbytes.games.ffb.report.ReportBribesRoll;
import com.balancedbytes.games.ffb.report.ReportConfusionRoll;
import com.balancedbytes.games.ffb.report.ReportFanFactorRoll;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.report.ReportKickoffExtraReRoll;
import com.balancedbytes.games.ffb.report.ReportKickoffPitchInvasion;
import com.balancedbytes.games.ffb.report.ReportKickoffResult;
import com.balancedbytes.games.ffb.report.ReportKickoffThrowARock;
import com.balancedbytes.games.ffb.report.ReportList;
import com.balancedbytes.games.ffb.report.ReportMasterChefRoll;
import com.balancedbytes.games.ffb.report.ReportPenaltyShootout;
import com.balancedbytes.games.ffb.report.ReportPilingOn;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import com.balancedbytes.games.ffb.report.ReportScatterBall;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import com.balancedbytes.games.ffb.report.ReportSpectators;
import com.balancedbytes.games.ffb.report.ReportStandUpRoll;
import com.balancedbytes.games.ffb.report.ReportStartHalf;
import com.balancedbytes.games.ffb.report.ReportTentaclesShadowingRoll;
import com.balancedbytes.games.ffb.report.ReportTimeoutEnforced;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import com.balancedbytes.games.ffb.report.ReportWeather;
import com.balancedbytes.games.ffb.report.ReportWinningsRoll;
import com.balancedbytes.games.ffb.report.ReportWizardUse;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.google.gson.Gson;
import org.butterbrot.ffb.stats.adapter.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StatsCollector {
    private List<ServerCommand> replayCommands;
    private StatsCollection collection = new StatsCollection();
    private TurnOverFinder turnOverFinder = new TurnOverFinder();
    private StatsState state = new StatsState();
    private List<Evaluator> evaluators = new ArrayList<>();

    public void setHomeTeam(Team team) {
        collection.setHomeTeam(team);
        turnOverFinder.addHomePlayers(team);
    }

    public StatsCollector() {
        this(new ArrayList<>());
    }

    public StatsCollector(List<ServerCommand> replayCommands) {
        this.replayCommands = replayCommands;
        evaluators.add(new ApothecaryRollEvaluator(collection));
        evaluators.add(new BlockRollEvaluator(collection, state));
        evaluators.add(new BribesRollEvaluator(collection));
        evaluators.add(new FanFactorRollEvaluator(collection));
        evaluators.add(new InjuryEvaluator(collection, state));
        evaluators.add(new KickoffExtraReRollEvaluator(collection));
        evaluators.add(new KickoffPitchInvasionEvaluator(collection, state));
        evaluators.add(new KickoffResultEvaluator(collection, state));
        evaluators.add(new KickoffThrowARockEvaluator(collection));
        evaluators.add(new MasterChefRollEvaluator(collection));
        evaluators.add(new PenaltyShootoutEvaluator(collection));
        evaluators.add(new PillingOnEvaluator(state));
        evaluators.add(new PlayerActionEvaluator(state, turnOverFinder));
        evaluators.add(new ReRollEvaluator(state, collection));
        evaluators.add(new ScatterBallEvaluator(state, collection));
        evaluators.add(new SkillRollEvaluator(collection, state));
        evaluators.add(new SpecialEffectRollEvaluator(collection));
        evaluators.add(new SpectatorsEvaluator(collection, state));
        evaluators.add(new StandUpRollEvaluator(collection));
        evaluators.add(new StartHalfEvaluator(state));
        evaluators.add(new TentaclesShadowingRollEvaluator(collection));
        evaluators.add(new TimeoutEnforcedEvaluator(state));
        evaluators.add(new TurnEndEvaluator(collection, state, turnOverFinder));
        evaluators.add(new WeatherEvaluator(collection));
        evaluators.add(new WinnigsRollEvaluator(collection));
        evaluators.add(new WizardUseEvaluator(state, collection));
    }

    public void setAwayTeam(Team team) {
        collection.setAwayTeam(team);
    }


    public List<ServerCommand> getReplayCommands() {
        return replayCommands;
    }

    public StatsCollection evaluate(String replayId) {
        collection.setReplayId(replayId);

        for (ServerCommand command : replayCommands) {
            if (command instanceof ServerCommandModelSync) {
                ServerCommandModelSync modelSync = (ServerCommandModelSync) command;


                ReportList reportList = modelSync.getReportList();
                for (IReport report : reportList.getReports()) {
                    if (state.isActionTurn()) {
                        turnOverFinder.add(report);
                    }
                    // DEBUG LOGGING
                        System.out.println(new Gson().toJson(report));
                    for (Evaluator evaluator: evaluators) {
                        if (evaluator.handles(report)){
                            evaluator.evaluate(report);
                            break;
                        }
                    }
                }

                boolean newTurnMode = false;

                for (ModelChange change : modelSync.getModelChanges().getChanges()) {
                    if (ModelChangeId.GAME_SET_HOME_PLAYING == change.getChangeId()) {
                        state.setHomePlaying((boolean) change.getValue());
                        turnOverFinder.setHomeTeamActive(state.isHomePlaying());
                    }

                    if (ModelChangeId.GAME_SET_TURN_MODE == change.getChangeId()) {
                        state.setTurnMode((TurnMode) change.getValue());
                        newTurnMode = true;
                    }

                    if (ModelChangeId.TURN_DATA_SET_TURN_NR == change.getChangeId()) {
                        state.setTurnNumber((int) change.getValue());
                        newTurnMode = true;
                    }
                }

                state.setActionTurn(TurnMode.BLITZ == state.getTurnMode() || TurnMode.REGULAR == state.getTurnMode());

                if (newTurnMode && state.isActionTurn()){
                    collection.addTurn(state.isHomePlaying(), state.getTurnMode(), state.getTurnNumber());
                }
            }
        }

        return collection;
    }
}
