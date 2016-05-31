package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.HeatExhaustion;
import com.balancedbytes.games.ffb.KnockoutRecovery;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportApothecaryRoll;
import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import com.balancedbytes.games.ffb.report.ReportBribesRoll;
import com.balancedbytes.games.ffb.report.ReportFanFactorRoll;
import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.report.ReportKickoffPitchInvasion;
import com.balancedbytes.games.ffb.report.ReportKickoffThrowARock;
import com.balancedbytes.games.ffb.report.ReportList;
import com.balancedbytes.games.ffb.report.ReportMasterChefRoll;
import com.balancedbytes.games.ffb.report.ReportPenaltyShootout;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import com.balancedbytes.games.ffb.report.ReportSpectators;
import com.balancedbytes.games.ffb.report.ReportTentaclesShadowingRoll;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import com.balancedbytes.games.ffb.report.ReportWinningsRoll;
import com.balancedbytes.games.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.collections.StatsCollection;

import java.util.List;

public class StatsCollector {
    private List<ServerCommand> replayCommands;
    private StatsCollection collection = new StatsCollection();

    StatsCollector(final List<ServerCommand> replayCommands) {
        this.replayCommands = replayCommands;
    }

    public void setHomeTeam(Team team) {
        collection.setHomeTeam(team);
    }

    public void setAwayTeam(Team team) {
        collection.setAwayTeam(team);
    }

    public List<ServerCommand> getReplayCommands() {
        return replayCommands;
    }

    StatsCollection evaluate() {

        String currentBlocker = null;
        ReportBlockRoll currentBlockRoll = null;
        boolean lastReportWasBlockRoll = false;
        boolean blockRerolled = false;
        for (ServerCommand command : replayCommands) {
            ServerCommandModelSync modelSync = (ServerCommandModelSync) command;
            ReportList reportList = modelSync.getReportList();
            for (IReport report : reportList.getReports()) {
                if (report instanceof ReportSkillRoll) {
                    ReportSkillRoll skillReport = ((ReportSkillRoll) report);
                    collection.addSingleRoll(skillReport.getRoll(), skillReport.getPlayerId());
                } else if (report instanceof ReportFanFactorRoll) {
                    ReportFanFactorRoll ffReport = ((ReportFanFactorRoll) report);
                    for (int roll : ffReport.getFanFactorRollAway()) {
                        collection.getAway().addSingleRoll(roll);
                    }
                    for (int roll : ffReport.getFanFactorRollHome()) {
                        collection.getHome().addSingleRoll(roll);
                    }
                } else if (report instanceof ReportInjury) {
                    ReportInjury injury = (ReportInjury) report;
                    if (ArrayTool.isProvided(injury.getArmorRoll())) {
                        collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
                    }
                    if (injury.isArmorBroken()) {
                        collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
                        if (ArrayTool.isProvided(injury.getCasualtyRoll())) {
                            collection.addSingleRoll(injury.getCasualtyRoll()[0], injury.getDefenderId());
                        }
                        if (ArrayTool.isProvided(injury.getCasualtyRollDecay())) {
                            collection.addSingleRoll(injury.getCasualtyRollDecay()[0], injury.getDefenderId());
                        }
                    }
                    if (currentBlocker != null && currentBlockRoll != null) {
                        collection.addBlockKnockDown(currentBlockRoll.getBlockRoll().length, injury.getDefenderId(), currentBlockRoll.getChoosingTeamId(), currentBlocker);
                    }
                } else if (report instanceof ReportTentaclesShadowingRoll) {
                    ReportTentaclesShadowingRoll tentShadow = (ReportTentaclesShadowingRoll) report;
                    collection.addDoubleOpposingRoll(tentShadow.getRoll(), tentShadow.getDefenderId());
                } else if (report instanceof ReportApothecaryRoll) {
                    ReportApothecaryRoll apoRoll = (ReportApothecaryRoll) report;
                    if (ArrayTool.isProvided(apoRoll.getCasualtyRoll())) {
                        collection.addSingleOpposingRoll(apoRoll.getCasualtyRoll()[0], apoRoll.getPlayerId());
                    }
                } else if (report instanceof ReportSpectators) {
                    ReportSpectators specs = (ReportSpectators) report;
                    collection.getHome().addDoubleRoll(specs.getSpectatorRollHome());
                    collection.getAway().addDoubleRoll(specs.getSpectatorRollAway());
                } else if (report instanceof ReportKickoffThrowARock) {
                    ReportKickoffThrowARock throwRock = (ReportKickoffThrowARock) report;
                    collection.getAway().addSingleRoll(throwRock.getRollAway());
                    collection.getHome().addSingleRoll(throwRock.getRollHome());
                } else if (report instanceof ReportKickoffPitchInvasion) {
                    ReportKickoffPitchInvasion invasion = (ReportKickoffPitchInvasion) report;
                    for (int roll : invasion.getRollsHome()) {
                        collection.getAway().addSingleRoll(roll);
                    }
                    for (int roll : invasion.getRollsAway()) {
                        collection.getHome().addSingleRoll(roll);
                    }
                } else if (report instanceof ReportWinningsRoll) {
                    ReportWinningsRoll winnings = (ReportWinningsRoll) report;
                    collection.getHome().addSingleRoll(winnings.getWinningsRollHome());
                    collection.getAway().addSingleRoll(winnings.getWinningsRollAway());
                } else if (report instanceof ReportBribesRoll) {
                    ReportBribesRoll bribe = (ReportBribesRoll) report;
                    collection.addSingleRoll(bribe.getRoll(), bribe.getPlayerId());
                } else if (report instanceof ReportMasterChefRoll) {
                    ReportMasterChefRoll chef = (ReportMasterChefRoll) report;
                    for (int roll : chef.getMasterChefRoll()) {
                        collection.addSingleRoll(roll, chef.getTeamId());
                    }
                } else if (report instanceof ReportPenaltyShootout) {
                    ReportPenaltyShootout shootout = (ReportPenaltyShootout) report;
                    collection.getAway().addSingleRoll(shootout.getRollAway());
                    collection.getHome().addSingleRoll(shootout.getRollHome());
                } else if (report instanceof ReportSpecialEffectRoll) {
                    ReportSpecialEffectRoll effect = (ReportSpecialEffectRoll) report;
                    collection.addSingleOpposingRoll(effect.getRoll(), effect.getPlayerId());
                } else if (report instanceof ReportPlayerAction) {
                    lastReportWasBlockRoll = false;
                    blockRerolled = false;
                    ReportPlayerAction action = ((ReportPlayerAction) report);
                    switch (action.getPlayerAction()) {
                        case BLITZ:
                        case BLITZ_MOVE:
                        case BLOCK:
                            currentBlocker = action.getActingPlayerId();
                            currentBlockRoll = null;
                            break;
                        default:
                            currentBlocker = null;
                            currentBlockRoll = null;
                    }
                } else if (report instanceof ReportBlockRoll) {
                    ReportBlockRoll block = (ReportBlockRoll) report;
                    collection.addBlockRolls(block.getBlockRoll(), currentBlocker, block.getChoosingTeamId(), blockRerolled);
                    currentBlockRoll = block;
                    lastReportWasBlockRoll = true;
                } else if (report instanceof ReportReRoll) {
                    if (lastReportWasBlockRoll) {
                        lastReportWasBlockRoll = false;
                        blockRerolled = true;
                    }
                } else if (report instanceof ReportTurnEnd) {
                    ReportTurnEnd turn = (ReportTurnEnd) report;
                    if (ArrayTool.isProvided(turn.getKnockoutRecoveries())) {
                        for (KnockoutRecovery recovery : turn.getKnockoutRecoveries()) {
                            collection.addSingleRoll(recovery.getRoll(), recovery.getPlayerId());
                        }
                    }

                    if (ArrayTool.isProvided(turn.getHeatExhaustions())) {
                        for (HeatExhaustion exhaustion : turn.getHeatExhaustions()) {
                            collection.addSingleOpposingRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
                        }
                    }

                }
            }
        }

        return collection;
    }
}
