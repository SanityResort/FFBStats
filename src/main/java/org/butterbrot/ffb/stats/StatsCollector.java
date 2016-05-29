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
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import com.balancedbytes.games.ffb.report.ReportSpectators;
import com.balancedbytes.games.ffb.report.ReportTentaclesShadowingRoll;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import com.balancedbytes.games.ffb.report.ReportWinningsRoll;
import com.balancedbytes.games.ffb.util.ArrayTool;

import java.util.List;

public class StatsCollector {
    private List<ServerCommand> replayCommands;
    private StatsCollection collection = new StatsCollection();

    public StatsCollector(final List<ServerCommand> replayCommands) {
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

    public StatsCollection evaluate() {

        int replayCount = 0;
        String currentBlocker = null;
        ReportBlockRoll currentBlockRoll = null;
        boolean lastReportWasBlockRoll = false;
        boolean blockRerolled = false;
        for (ServerCommand command : replayCommands) {
            replayCount++;
            //              System.out.println("Aggregating command " + replayCount + " of " + replayCommands.size());
            ServerCommandModelSync modelSync = (ServerCommandModelSync) command;
            ReportList reportList = modelSync.getReportList();
            //             System.out.println("Starting report aggregation for " + reportList.size() + " reports");
            int reportCount = 0;
            for (IReport report : reportList.getReports()) {
                reportCount++;
                //            System.out.println("Aggregating report " + reportCount + " of " + reportList.size());
                System.out.println("replaycount: " + replayCount);
                System.out.println("reportcount: " + reportCount);
                System.out.println("Current blocker: " + currentBlocker);
                System.out.println("Current block roll: " + (currentBlockRoll == null ? null : currentBlockRoll.toJsonValue()));
                System.out.println(report.toJsonValue());
                switch (report.getId()) {
                    case ALWAYS_HUNGRY_ROLL:
                    case ANIMOSITY_ROLL:
                    case CATCH_ROLL:
                    case CONFUSION_ROLL:
                    case DAUNTLESS_ROLL:
                    case DODGE_ROLL:
                    case ESCAPE_ROLL:
                    case FOUL_APPEARANCE_ROLL:
                    case GO_FOR_IT_ROLL:
                    case INTERCEPTION_ROLL:
                    case LEAP_ROLL:
                    case PASS_ROLL:
                    case PICK_UP_ROLL:
                    case REGENERATION_ROLL:
                    case RIGHT_STUFF_ROLL:
                    case SAFE_THROW_ROLL:
                    case THROW_TEAM_MATE_ROLL:
                    case JUMP_UP_ROLL:
                    case STAND_UP_ROLL:
                    case CHAINSAW_ROLL:
                    case BLOOD_LUST_ROLL:
                    case HYPNOTIC_GAZE_ROLL:

                        ReportSkillRoll skillReport = ((ReportSkillRoll) report);
                        collection.addSingleRoll(skillReport.getRoll(), skillReport.getPlayerId());
                        break;
                    case FAN_FACTOR_ROLL:
                        ReportFanFactorRoll ffReport = ((ReportFanFactorRoll) report);
                        for (int roll : ffReport.getFanFactorRollAway()) {
                            collection.getAway().addSingleRoll(roll);
                        }
                        for (int roll : ffReport.getFanFactorRollHome()) {
                            collection.getHome().addSingleRoll(roll);
                        }
                        break;
                    case INJURY:
                        ReportInjury injury = (ReportInjury) report;
                        if (ArrayTool.isProvided(injury.getArmorRoll())) {
                            //     System.out.println("Armour roll: " + injury.getArmorRoll().length + "  Player " + injury.getAttackerId());
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
                        break;
                    case TENTACLES_SHADOWING_ROLL:
                        ReportTentaclesShadowingRoll tentShadow = (ReportTentaclesShadowingRoll) report;
                        collection.addDoubleOpposingRoll(tentShadow.getRoll(), tentShadow.getDefenderId());
                        break;
                    case APOTHECARY_ROLL:
                        ReportApothecaryRoll apoRoll = (ReportApothecaryRoll) report;
                        if (ArrayTool.isProvided(apoRoll.getCasualtyRoll())) {
                            collection.addSingleOpposingRoll(apoRoll.getCasualtyRoll()[0], apoRoll.getPlayerId());
                        }
                        break;
                    case SPECTATORS:
                        ReportSpectators specs = (ReportSpectators) report;
                        collection.getHome().addDoubleRoll(specs.getSpectatorRollHome());
                        collection.getAway().addDoubleRoll(specs.getSpectatorRollAway());
                        break;
                    case KICKOFF_THROW_A_ROCK:
                        ReportKickoffThrowARock throwRock = (ReportKickoffThrowARock) report;
                        collection.getAway().addSingleRoll(throwRock.getRollAway());
                        collection.getHome().addSingleRoll(throwRock.getRollHome());
                        break;
                    case KICKOFF_PITCH_INVASION:
                        ReportKickoffPitchInvasion invasion = (ReportKickoffPitchInvasion) report;
                        for (int roll : invasion.getRollsHome()) {
                            collection.getAway().addSingleRoll(roll);
                        }
                        for (int roll : invasion.getRollsAway()) {
                            collection.getHome().addSingleRoll(roll);
                        }
                        break;
                    case WINNINGS_ROLL:
                        ReportWinningsRoll winnings = (ReportWinningsRoll) report;
                        collection.getHome().addSingleRoll(winnings.getWinningsRollHome());
                        collection.getAway().addSingleRoll(winnings.getWinningsRollAway());
                        break;
                    case BRIBES_ROLL:
                        ReportBribesRoll bribe = (ReportBribesRoll) report;
                        collection.addSingleRoll(bribe.getRoll(), bribe.getPlayerId());
                        break;
                    case MASTER_CHEF_ROLL:
                        ReportMasterChefRoll chef = (ReportMasterChefRoll) report;
                        for (int roll : chef.getMasterChefRoll()) {
                            collection.addSingleRoll(roll, chef.getTeamId());
                        }
                        break;
                    case PENALTY_SHOOTOUT:
                        ReportPenaltyShootout shootout = (ReportPenaltyShootout) report;
                        collection.getAway().addSingleRoll(shootout.getRollAway());
                        collection.getHome().addSingleRoll(shootout.getRollHome());
                        break;
                    case SPELL_EFFECT_ROLL:
                        ReportSpecialEffectRoll effect = (ReportSpecialEffectRoll) report;
                        collection.addSingleOpposingRoll(effect.getRoll(), effect.getPlayerId());
                        break;
                    case PLAYER_ACTION:
                        lastReportWasBlockRoll = false;
                        blockRerolled = false;
                        ReportPlayerAction action = ((ReportPlayerAction) report);
                        System.out.println("Player Action: " + action.getPlayerAction());
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
                        break;
                    case BLOCK_ROLL:
                        ReportBlockRoll block = (ReportBlockRoll) report;
                        collection.addBlockRolls(block.getBlockRoll(), currentBlocker, block.getChoosingTeamId(), blockRerolled);
                        currentBlockRoll = block;
                        lastReportWasBlockRoll = true;
                        break;
                    case RE_ROLL:
                        if (lastReportWasBlockRoll) {
                            lastReportWasBlockRoll = false;
                            blockRerolled = true;
                        }
                        break;
                    case TURN_END:
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
                    default:
                        System.out.println("Ignoring report " + report.getId());
                }
            }
        }

        return collection;
    }
}
