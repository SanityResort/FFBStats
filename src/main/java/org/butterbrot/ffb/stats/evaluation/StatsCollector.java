package org.butterbrot.ffb.stats.evaluation;

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
import org.butterbrot.ffb.stats.adapter.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.springframework.util.StringUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StatsCollector {
    private List<ServerCommand> replayCommands;
    private StatsCollection collection = new StatsCollection();
    private TurnOverFinder turnOverFinder = new TurnOverFinder();

    public void setHomeTeam(Team team) {
        collection.setHomeTeam(team);
        turnOverFinder.addHomePlayers(team);
    }

    public StatsCollector() {
        this(new ArrayList<>());
    }

    public StatsCollector(List<ServerCommand> replayCommands) {
        this.replayCommands = replayCommands;
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
        boolean isHomePlaying = false;
        TurnMode turnMode = null;
        int turnNumber = 0;
        collection.setReplayId(replayId);
        String activePlayer = null;
        ReportBlockRoll currentBlockRoll = null;
        boolean lastReportWasBlockRoll = false;
        boolean blockRerolled = false;
        boolean startSecondHalf = false;
        boolean startOvertime = false;
        ReportPilingOn poReport = null;
        boolean isActionTurn = false;
        boolean ballScatters = false;
        Deque<ReportPoInjury> injuries = new ArrayDeque<>();
        for (ServerCommand command : replayCommands) {
            if (command instanceof ServerCommandModelSync) {
                ServerCommandModelSync modelSync = (ServerCommandModelSync) command;
                for (ModelChange change : modelSync.getModelChanges().getChanges()) {
                    if (ModelChangeId.GAME_SET_HOME_PLAYING == change.getChangeId()) {
                        isHomePlaying = (boolean) change.getValue();
                        turnOverFinder.setHomeTeamActive(isHomePlaying);
                    }

                    if (ModelChangeId.GAME_SET_TURN_MODE == change.getChangeId()) {
                        turnMode = (TurnMode) change.getValue();
                    }

                    if (ModelChangeId.TURN_DATA_SET_TURN_NR == change.getChangeId()) {
                        turnNumber = (int) change.getValue();
                    }
                }

                ReportList reportList = modelSync.getReportList();
                for (IReport report : reportList.getReports()) {
                    if (isActionTurn) {
                        turnOverFinder.add(report);
                    }
                    // DEBUG LOGGING
                    //System.out.println(new Gson().toJson(report));
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
                        if (ArrayTool.isProvided(injury.getArmorRoll())) {
                            if (poReport == null || (poReport.isUsed() && !poReport.isReRollInjury())) {
                                collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
                            }
                        }
                        if (injury.isArmorBroken()) {
                            injuries.addLast(new ReportPoInjury(injury, poReport));
                            poReport = null;
                            // if the armour is broken report the injury roll, but only if both injury dice are not 0. this
                            // should prevent errors when fanatic armour is broken, as this might be reported with weird data.
                            if (ArrayTool.isProvided(injury.getInjuryRoll()) && injury.getInjuryRoll()[0] * injury.getInjuryRoll()[1] > 0) {
                                collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
                                if (ArrayTool.isProvided(injury.getCasualtyRoll())) {
                                    collection.addSingleRoll(injury.getCasualtyRoll()[0], injury.getDefenderId());
                                }
                                if (ArrayTool.isProvided(injury.getCasualtyRollDecay())) {
                                    collection.addSingleRoll(injury.getCasualtyRollDecay()[0], injury.getDefenderId());
                                }
                            }
                        }
                        if (activePlayer != null && currentBlockRoll != null) {
                            collection.addBlockKnockDown(currentBlockRoll.getBlockRoll().length, injury.getDefenderId(),
                                    currentBlockRoll.getChoosingTeamId(), activePlayer);
                        }
                    } else if (report instanceof ReportTentaclesShadowingRoll) {
                        ReportTentaclesShadowingRoll tentShadow = (ReportTentaclesShadowingRoll) report;
                        collection.addDoubleOpposingRoll(tentShadow.getRoll(), tentShadow.getDefenderId());
                    } else if (report instanceof ReportApothecaryRoll) {
                        ReportApothecaryRoll apoRoll = (ReportApothecaryRoll) report;
                        if (ArrayTool.isProvided(apoRoll.getCasualtyRoll())) {
                            collection.addSingleOpposingRoll(apoRoll.getCasualtyRoll()[0], apoRoll.getPlayerId());
                        }
                        collection.addApoUse(apoRoll.getPlayerId());
                    } else if (report instanceof ReportSpectators) {
                        ReportSpectators specs = (ReportSpectators) report;
                        collection.getHome().addDoubleRoll(specs.getSpectatorRollHome());
                        collection.getAway().addDoubleRoll(specs.getSpectatorRollAway());
                        fameHome = specs.getFameHome();
                        fameAway = specs.getFameAway();
                    } else if (report instanceof ReportKickoffResult) {
                        ReportKickoffResult kickoff = (ReportKickoffResult) report;
                        collection.addDrive(kickoff.getKickoffResult());
                    } else if (report instanceof ReportKickoffThrowARock) {
                        ReportKickoffThrowARock throwRock = (ReportKickoffThrowARock) report;
                        collection.getAway().addSingleRoll(throwRock.getRollAway());
                        collection.getHome().addSingleRoll(throwRock.getRollHome());
                        collection.addKickOffRolls(new int[]{throwRock.getRollHome()}, new int[]{throwRock.getRollAway()});
                    } else if (report instanceof ReportKickoffExtraReRoll) {
                        ReportKickoffExtraReRoll extraReRoll = (ReportKickoffExtraReRoll) report;
                        collection.getAway().addSingleRoll(extraReRoll.getRollAway());
                        collection.getHome().addSingleRoll(extraReRoll.getRollHome());
                        collection.addKickOffRolls(new int[]{extraReRoll.getRollHome()}, new int[]{extraReRoll.getRollAway()});
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
                        collection.addKickOffRolls(invasion.getRollsHome(), invasion.getRollsAway());
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
                        collection.addBribe(bribe.getPlayerId());
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
                        ballScatters = false;
                        lastReportWasBlockRoll = false;
                        blockRerolled = false;
                        ReportPlayerAction action = ((ReportPlayerAction) report);
                        currentBlockRoll = null;
                        poReport = null;
                        turnOverFinder.reset();
                        turnOverFinder.add(action);
                        switch (action.getPlayerAction()) {
                            case BLITZ:
                            case BLITZ_MOVE:
                            case BLOCK:
                            case MULTIPLE_BLOCK:
                                activePlayer = action.getActingPlayerId();
                                break;
                            case MOVE:
                                activePlayer = action.getActingPlayerId();
                                break;
                            default:
                                activePlayer = null;
                        }
                    } else if (report instanceof ReportBlockRoll) {
                        ReportBlockRoll block = (ReportBlockRoll) report;
                        collection.addBlockRolls(block.getBlockRoll(), activePlayer, block.getChoosingTeamId(), blockRerolled);
                        currentBlockRoll = block;
                        lastReportWasBlockRoll = true;
                    } else if (report instanceof ReportReRoll) {
                        if (lastReportWasBlockRoll) {
                            lastReportWasBlockRoll = false;
                            blockRerolled = true;
                        }
                        ReportReRoll reportReRoll = (ReportReRoll) report;
                        if (ReRollSource.TEAM_RE_ROLL == reportReRoll.getReRollSource() || ReRollSource.LEADER == reportReRoll.getReRollSource()) {
                            collection.addReroll(reportReRoll.getPlayerId());
                        }
                    } else if (report instanceof ReportTurnEnd) {
                        ballScatters = false;
                        ReportTurnEnd turn = (ReportTurnEnd) report;
                        if (!StringUtils.isEmpty(turn.getPlayerIdTouchdown())) {
                            collection.addTouchdown(turn.getPlayerIdTouchdown());
                        }

                        if (ArrayTool.isProvided(turn.getKnockoutRecoveries())) {
                            for (KnockoutRecovery recovery : turn.getKnockoutRecoveries()) {
                                collection.addSingleRoll(recovery.getRoll(), recovery.getPlayerId());
                                collection.addKoRoll(recovery.getRoll(), recovery.getPlayerId());
                                if (recovery.isRecovering()) {
                                    collection.addSuccessRoll(recovery.getPlayerId(), report.getId(), 4 - recovery.getBloodweiserBabes());
                                }
                            }
                        }

                        if (ArrayTool.isProvided(turn.getHeatExhaustions())) {
                            for (HeatExhaustion exhaustion : turn.getHeatExhaustions()) {
                                collection.addSingleOpposingRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
                                collection.addHeatRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
                                if (!exhaustion.isExhausted()) {
                                    collection.addSuccessRoll(exhaustion.getPlayerId(), report.getId(), 2);
                                }
                            }
                        }

                        collection.addArmourAndInjuryStats(injuries);
                        injuries.clear();
                        if (isActionTurn) {
                            turnOverFinder.findTurnover().ifPresent(turnOver -> collection.addTurnOver(turnOver));
                        }

                        turnOverFinder.reset();
                        if (startSecondHalf) {
                            collection.startSecondHalf();
                            startSecondHalf = false;
                        }
                        if (startOvertime) {
                            collection.startOvertime();
                            startOvertime = false;
                        }

                        if (TurnMode.BLITZ == turnMode || TurnMode.REGULAR == turnMode) {
                            collection.addTurn(isHomePlaying, turnMode, turnNumber);
                            isActionTurn = true;
                        } else {
                            isActionTurn = false;
                        }


                    } else if (report instanceof ReportPilingOn) {
                        if (((ReportPilingOn) report).isUsed()) {
                            poReport = ((ReportPilingOn) report);
                            injuries.pollLast();
                        }
                    } else if (report instanceof ReportStandUpRoll) {
                        ReportStandUpRoll standUpRoll = (ReportStandUpRoll) report;
                        collection.addSingleRoll(standUpRoll.getRoll(), standUpRoll.getPlayerId());
                        if (standUpRoll.isSuccessful()) {
                            collection.addSuccessRoll(standUpRoll.getPlayerId(), standUpRoll.getId(), 4);
                        }
                    } else if (report instanceof ReportWeather) {
                        collection.setWeather(((ReportWeather) report).getWeather().getName());
                    } else if (report instanceof ReportStartHalf) {
                        if (((ReportStartHalf) report).getHalf() == 2) {
                            startSecondHalf = true;
                        } else if (((ReportStartHalf) report).getHalf() > 2) {
                            startOvertime = true;
                            startSecondHalf = false;
                        }
                    } else if (report instanceof ReportTimeoutEnforced) {
                        collection.addTimeOut(isHomePlaying);
                    } else if (report instanceof ReportScatterBall && !ballScatters) {
                        collection.addScatter(isHomePlaying);
                        ballScatters = true;
                    } else if (report instanceof ReportWizardUse) {
                        collection.addWizardUse(isHomePlaying);
                    } else if (report instanceof ReportKickoffResult) {
                        ballScatters = true;
                    }
                }
            }
        }

        return collection;
    }
}
