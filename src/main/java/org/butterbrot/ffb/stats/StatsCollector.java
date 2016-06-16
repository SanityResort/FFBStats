package org.butterbrot.ffb.stats;

import org.butterbrot.ffb.stats.collections.StatsCollection;
import refactored.com.balancedbytes.games.ffb.HeatExhaustion;
import refactored.com.balancedbytes.games.ffb.KnockoutRecovery;
import refactored.com.balancedbytes.games.ffb.SpecialEffect;
import refactored.com.balancedbytes.games.ffb.model.Team;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommand;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportApothecaryRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportBlockRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportBribesRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportFanFactorRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;
import refactored.com.balancedbytes.games.ffb.report.ReportKickoffPitchInvasion;
import refactored.com.balancedbytes.games.ffb.report.ReportKickoffThrowARock;
import refactored.com.balancedbytes.games.ffb.report.ReportList;
import refactored.com.balancedbytes.games.ffb.report.ReportMasterChefRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportPenaltyShootout;
import refactored.com.balancedbytes.games.ffb.report.ReportPilingOn;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportSpectators;
import refactored.com.balancedbytes.games.ffb.report.ReportStandUpRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTentaclesShadowingRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTurnEnd;
import refactored.com.balancedbytes.games.ffb.report.ReportWeather;
import refactored.com.balancedbytes.games.ffb.report.ReportWinningsRoll;
import refactored.com.balancedbytes.games.ffb.util.ArrayTool;

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

    public StatsCollection evaluate(String replayId) {
        int fameHome = 0;
        int fameAway = 0;
        collection.setReplayId(replayId);
        String currentBlocker = null;
        String currentMover = null;
        ReportBlockRoll currentBlockRoll = null;
        boolean lastReportWasBlockRoll = false;
        boolean blockRerolled = false;
        boolean reRollingInjury = false;
        for (ServerCommand command : replayCommands) {
            ServerCommandModelSync modelSync = (ServerCommandModelSync) command;
            ReportList reportList = modelSync.getReportList();
            for (IReport report : reportList.getReports()) {
                if (report instanceof ReportSkillRoll) {
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


                    // set the block roll to null, when some other skill roll was made, like dodge or gfi.
                    // this should take are that a fanatic falling down due to a gfi is not counted as a failed block.
                    currentBlockRoll = null;
                } else if (report instanceof ReportFanFactorRoll) {
                    ReportFanFactorRoll ffReport = ((ReportFanFactorRoll) report);
                    if (ArrayTool.isProvided(ffReport.getFanFactorRollAway())) {
                        for (int roll : ffReport.getFanFactorRollAway()) {
                            collection.getAway().addSingleRoll(roll);
                        }
                    }
                    if (ArrayTool.isProvided(ffReport.getFanFactorRollHome())) {
                        for (int roll : ffReport.getFanFactorRollHome()) {
                            collection.getHome().addSingleRoll(roll);
                        }
                    }
                } else if (report instanceof ReportInjury) {
                    ReportInjury injury = (ReportInjury) report;
                    if (!reRollingInjury && ArrayTool.isProvided(injury.getArmorRoll())) {
                        collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
                    }
                    // if the armour is broken report the injury roll, but only if both injury dice are not 0. this
                    // should prevent errors when fanatic armour is broken, as this might be reported with weird data.
                    if (injury.isArmorBroken() && ArrayTool.isProvided(injury.getInjuryRoll()) && injury.getInjuryRoll()[0] * injury.getInjuryRoll()[1] > 0) {
                        collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
                        if (ArrayTool.isProvided(injury.getCasualtyRoll())) {
                            collection.addSingleRoll(injury.getCasualtyRoll()[0], injury.getDefenderId());
                        }
                        if (ArrayTool.isProvided(injury.getCasualtyRollDecay())) {
                            collection.addSingleRoll(injury.getCasualtyRollDecay()[0], injury.getDefenderId());
                        }
                    }
                    if ((currentMover != null || currentBlocker != null) && currentBlockRoll != null) {
                        collection.addBlockKnockDown(currentBlockRoll.getBlockRoll().length, injury.getDefenderId(),
                                currentBlockRoll.getChoosingTeamId(),
                                currentBlocker == null ? currentMover : currentBlocker);
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
                    fameHome = specs.getFameHome();
                    fameAway = specs.getFameAway();
                } else if (report instanceof ReportKickoffThrowARock) {
                    ReportKickoffThrowARock throwRock = (ReportKickoffThrowARock) report;
                    collection.getAway().addSingleRoll(throwRock.getRollAway());
                    collection.getHome().addSingleRoll(throwRock.getRollHome());
                } else if (report instanceof ReportKickoffPitchInvasion) {
                    ReportKickoffPitchInvasion invasion = (ReportKickoffPitchInvasion) report;
                    for (int roll : invasion.getRollsHome()) {
                        int minimumRoll = 6 - fameAway;
                        if (roll > 0) {
                            collection.getAway().addSingleRoll(roll);
                            if (roll >= minimumRoll) {
                                collection.getAway().addSuccessRoll(report.getId(), minimumRoll);
                            }
                        }
                    }
                    for (int roll : invasion.getRollsAway()) {
                        int minimumRoll = 6 - fameHome;
                        if (roll > 0) {
                            collection.getHome().addSingleRoll(roll);
                            if (roll >= minimumRoll) {
                                collection.getHome().addSuccessRoll(report.getId(), minimumRoll);
                            }
                        }
                    }
                } else if (report instanceof ReportWinningsRoll) {
                    ReportWinningsRoll winnings = (ReportWinningsRoll) report;
                    if (winnings.getWinningsRollHome() > 0) {
                        collection.getHome().addSingleRoll(winnings.getWinningsRollHome());
                    }
                    if (winnings.getWinningsRollAway() > 0) {
                        collection.getAway().addSingleRoll(winnings.getWinningsRollAway());
                    }
                    collection.setFinished(true);
                } else if (report instanceof ReportBribesRoll) {
                    ReportBribesRoll bribe = (ReportBribesRoll) report;
                    collection.addSingleRoll(bribe.getRoll(), bribe.getPlayerId());
                    if (bribe.isSuccessful()) {
                        collection.addSuccessRoll(bribe.getPlayerId(), bribe.getId(), 2);
                    }
                } else if (report instanceof ReportMasterChefRoll) {
                    ReportMasterChefRoll chef = (ReportMasterChefRoll) report;
                    for (int roll : chef.getMasterChefRoll()) {
                        collection.addSingleRoll(roll, chef.getTeamId());
                        if (roll > 3) {
                            collection.addSuccessRoll(chef.getTeamId(), chef.getId(), 4);
                        }
                    }
                } else if (report instanceof ReportPenaltyShootout) {
                    ReportPenaltyShootout shootout = (ReportPenaltyShootout) report;
                    collection.getAway().addSingleRoll(shootout.getRollAway());
                    collection.getHome().addSingleRoll(shootout.getRollHome());
                } else if (report instanceof ReportSpecialEffectRoll) {
                    ReportSpecialEffectRoll effect = (ReportSpecialEffectRoll) report;
                    // bomb fumbles have no dice roll for the bomber (goes down automatically), also fireballs on lying
                    // players prolly have that effect
                    if (effect.getRoll() > 0) {
                        collection.addSingleOpposingRoll(effect.getRoll(), effect.getPlayerId());
                        if (effect.isSuccessful()) {
                            collection.addOpposingSuccessRoll(effect.getPlayerId(), effect.getId(), effect.getSpecialEffect() == SpecialEffect.LIGHTNING ? 2 : 4);
                        }
                    }
                } else if (report instanceof ReportPlayerAction) {
                    lastReportWasBlockRoll = false;
                    blockRerolled = false;
                    ReportPlayerAction action = ((ReportPlayerAction) report);
                    currentBlockRoll = null;
                    reRollingInjury = false;
                    switch (action.getPlayerAction()) {
                        case BLITZ:
                        case BLITZ_MOVE:
                        case BLOCK:
                        case MULTIPLE_BLOCK:
                            currentBlocker = action.getActingPlayerId();
                            currentMover = null;
                            break;
                        case MOVE:
                            currentBlocker = null;
                            currentMover = action.getActingPlayerId();
                            break;
                        default:
                            currentMover = null;
                            currentBlocker = null;
                    }
                } else if (report instanceof ReportBlockRoll) {
                    ReportBlockRoll block = (ReportBlockRoll) report;
                    // if the currentBlocker is null, then we must use the currentMover as blocker. this happens e.g.
                    // for fanatics, as they do not declare block but only move actions.
                    collection.addBlockRolls(block.getBlockRoll(),
                            currentBlocker == null ? currentMover : currentBlocker, block.getChoosingTeamId(),
                            blockRerolled);
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
                            if (recovery.isRecovering()) {
                                collection.addSuccessRoll(recovery.getPlayerId(), report.getId(), 4 - recovery.getBloodweiserBabes());
                            }
                        }
                    }

                    if (ArrayTool.isProvided(turn.getHeatExhaustions())) {
                        for (HeatExhaustion exhaustion : turn.getHeatExhaustions()) {
                            collection.addSingleOpposingRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
                            if (!exhaustion.isExhausted()) {
                                collection.addSuccessRoll(exhaustion.getPlayerId(), report.getId(), 2);
                            }
                        }
                    }

                } else if (report instanceof ReportPilingOn) {
                    reRollingInjury = ((ReportPilingOn) report).isReRollInjury();
                } else if (report instanceof ReportStandUpRoll) {
                    ReportStandUpRoll standUpRoll = (ReportStandUpRoll) report;
                    collection.addSingleRoll(standUpRoll.getRoll(), standUpRoll.getPlayerId());
                    if (standUpRoll.isSuccessful()) {
                        collection.addSuccessRoll(standUpRoll.getPlayerId(), standUpRoll.getId(), 4);
                    }
                } else if (report instanceof ReportWeather) {
                    collection.setWeather(((ReportWeather) report).getWeather().getName());
                }
            }
        }

        return collection;
    }
}
