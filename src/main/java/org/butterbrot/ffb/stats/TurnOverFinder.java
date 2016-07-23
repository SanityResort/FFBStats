package org.butterbrot.ffb.stats;

import org.butterbrot.ffb.stats.analyzer.TurnOverAnalyzer;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.SpecialEffect;
import refactored.com.balancedbytes.games.ffb.model.Player;
import refactored.com.balancedbytes.games.ffb.model.Team;
import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportBlockRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportBribesRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;
import refactored.com.balancedbytes.games.ffb.report.ReportPassRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportReferee;
import refactored.com.balancedbytes.games.ffb.report.ReportScatterBall;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTurnEnd;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TurnOverFinder {

    private boolean homeTeamActive;

    private String activePlayer;

    private Deque<IReport> reports = new ArrayDeque<>();

    private String homeTeam;
    private Set<String> homePlayers = new HashSet<>();

    private Map<String, TurnOverAnalyzer> analyzers = new HashMap<>();

    public void addHomePlayers(Team homeTeam) {
        this.homeTeam = homeTeam.getId();
        for (Player player : homeTeam.getPlayers()) {
            homePlayers.add(player.getId());
        }
    }

    public void setHomeTeamActive(boolean homeTeamActive) {
        this.homeTeamActive = homeTeamActive;
    }

    public void add(IReport report) {
        reports.addLast(report);
    }

    public void add(ReportPlayerAction report) {
        reports.addLast(report);
        activePlayer = report.getActingPlayerId();
    }

    public void reset() {
        reports.clear();
        activePlayer = null;
    }

    public Optional<TurnOver> findTurnover() {
        IReport report = reports.pollFirst();
        while (report != null) {
            if (report instanceof ReportPlayerAction) {
                return findTurnOver((ReportPlayerAction) report);
            } else if (report instanceof ReportSpecialEffectRoll) {
                return findWizardTurnOver((ReportSpecialEffectRoll) report);
            }
            report = reports.pollFirst();
        }
        return Optional.empty();
    }

    private Optional<TurnOver> findTurnOver(ReportPlayerAction action) {
        switch (action.getPlayerAction()) {
    /*        case THROW_TEAM_MATE:
            case THROW_TEAM_MATE_MOVE:
                return findTtmTurnOver();*/
/*            case PASS:
            case PASS_MOVE:
            case HAND_OVER:
            case HAND_OVER_MOVE:
                return findPassHandOffTurnOver(action)*/
        }

        ReportReRoll reportReRoll = null;
        ReportSkillRoll reportSkillRoll = null;
        ReportBlockRoll reportBlockRoll = null;
        boolean blockingPlayerWasInjured = false;
        boolean ballScattered = false;
        boolean successfulPass = false;
        boolean sentOff = false;
        boolean landingFailed = false;
        for (IReport report : reports) {
            if (report instanceof ReportReRoll) {
                reportReRoll = (ReportReRoll) report;
            } else if (report instanceof ReportSkillRoll) {
                ReportSkillRoll skillRoll = (ReportSkillRoll) report;
                reportBlockRoll = null;
                if (!(ReportId.CATCH_ROLL == report.getId() && (homePlayers.contains(skillRoll.getPlayerId()) != homePlayers.contains(activePlayer)))) {
                    // if a successful pass was not caught by an opponent we do not reset the flag
                    successfulPass = false;
                }
                if (skillRoll.isSuccessful()) {
                    if (ReportId.INTERCEPTION_ROLL == report.getId()) {
                        reportSkillRoll = skillRoll;
                    } else if (ReportId.PASS_ROLL == report.getId()) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                        successfulPass = true;
                    } else if (ReportId.CATCH_ROLL == report.getId()) {
                        if (homePlayers.contains(skillRoll.getPlayerId()) == homePlayers.contains(activePlayer)) {
                            if (reportSkillRoll != null && (reportSkillRoll.getId() == ReportId.PASS_ROLL && ((ReportPassRoll) reportSkillRoll).isFumble() || reportSkillRoll.getId() == ReportId.PICK_UP_ROLL)) {
                                continue;
                            } else {
                                reportSkillRoll = null;
                                reportReRoll = null;
                            }
                        } else if (reportSkillRoll == null && !successfulPass) {
                            return Optional.of(new TurnOver(ReportId.HAND_OVER.getTurnOverDesc(), 0, reportReRoll, activePlayer));
                        }

                    } else if (!ballScattered) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                    }
                } else {
                    // failed skill rolls are not causing turn overs if they are safe throw or a pass from an opponent (dump off) or the ball scattered already
                    if (ReportId.SAFE_THROW_ROLL != report.getId() &&
                            !(ReportId.PASS_ROLL == report.getId() && (homePlayers.contains(skillRoll.getPlayerId()) != homePlayers.contains(activePlayer)))
                            && !ballScattered) {
                        reportSkillRoll = skillRoll;
                    }
                }
            } else if (report instanceof ReportInjury) {
                ReportInjury injury = (ReportInjury) report;
                if (homePlayers.contains(injury.getDefenderId()) == homePlayers.contains(activePlayer)) {
                    if (PlayerAction.THROW_BOMB == action.getPlayerAction()) {
                        // if the bomb was fumbled by the active team, we report the fumble
                        if (reportSkillRoll != null && reportSkillRoll instanceof ReportPassRoll && ((ReportPassRoll) reportSkillRoll).isFumble() && (homePlayers.contains(reportSkillRoll.getPlayerId()) == homePlayers.contains(activePlayer))) {
                            return Optional.of(new TurnOver(SpecialEffect.BOMB.getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, reportSkillRoll.getPlayerId()));
                        }
                        return Optional.of(new TurnOver(SpecialEffect.BOMB.getTurnOverDesc(), 0, reportReRoll, activePlayer));
                    } else if (reportBlockRoll != null) {
                        blockingPlayerWasInjured = true;
                    } else if (reportSkillRoll != null && ReportId.CHAINSAW_ROLL == reportSkillRoll.getId() && !injury.isArmorBroken()) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                    } else if (ReportId.RIGHT_STUFF_ROLL == reportSkillRoll.getId()) {
                        if (injury.getDefenderId().equals(reportSkillRoll.getPlayerId())) {
                            landingFailed = true;
                            continue;
                        } else {
                            return Optional.of(new TurnOver(ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), 0, reportReRoll, reportSkillRoll.getPlayerId()));
                        }
                    }
                    break;
                }
            } else if (report instanceof ReportBlockRoll) {
                reportBlockRoll = (ReportBlockRoll) report;
                reportSkillRoll = null;
            } else if (report instanceof ReportScatterBall) {
                if (landingFailed) {
                    return Optional.of(new TurnOver(reportSkillRoll.getId().getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, reportSkillRoll.getPlayerId()));
                }
                ballScattered = true;
            } else if (report instanceof ReportReferee) {
                sentOff = ((ReportReferee) report).isFoulingPlayerBanned();
            } else if (report instanceof ReportBribesRoll) {
                sentOff = !((ReportBribesRoll) report).isSuccessful();
            }
        }

        if (sentOff) {
            return Optional.of(new TurnOver(ReportId.FOUL.getTurnOverDesc(), 0, null, activePlayer));
        }

        if (successfulPass && ballScattered) {
            return Optional.of(new TurnOver(ReportId.PASS_ROLL.getTurnOverDesc(), 0, reportReRoll, activePlayer));
        }

        if (reportSkillRoll != null) {
            if (ReportId.INTERCEPTION_ROLL == reportSkillRoll.getId()) {
                return Optional.of(new TurnOver(reportSkillRoll.getId().getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, activePlayer));
            } else if (ReportId.RIGHT_STUFF_ROLL == reportSkillRoll.getId()) {
                if (ballScattered) {
                    return Optional.of(new TurnOver(reportSkillRoll.getId().getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, reportSkillRoll.getPlayerId()));
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.of(new TurnOver(reportSkillRoll.getId().getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, reportSkillRoll.getPlayerId()));
            }
        }

        if (reportBlockRoll != null && blockingPlayerWasInjured) {
            int blockDiceCount = reportBlockRoll.getBlockRoll().length;
            boolean actingTeamWasChoosing = homePlayers.contains(activePlayer) == (reportBlockRoll.getChoosingTeamId().equals(homeTeam));
            if (!actingTeamWasChoosing) {
                blockDiceCount *= -1;
            }
            return Optional.of(new TurnOver(reportBlockRoll.getId().getTurnOverDesc(), blockDiceCount, reportReRoll, activePlayer));
        }

        return Optional.empty();
    }

    private Optional<TurnOver> findTtmTurnOver() {

        return Optional.empty();
    }

    private Optional<TurnOver> findWizardTurnOver(ReportSpecialEffectRoll wizardReport) {
        boolean actingTeamInjured = false;
        boolean ballBounced = false;
        for (IReport report : reports) {
            if (report instanceof ReportInjury && (homePlayers.contains(((ReportInjury) report).getDefenderId()) == homeTeamActive)) {
                actingTeamInjured = true;
            } else if (report instanceof ReportScatterBall) {
                ballBounced = true;
            } else if (report instanceof ReportTurnEnd && actingTeamInjured && ballBounced) {
                return Optional.of(new TurnOver(wizardReport.getSpecialEffect().getTurnOverDesc(), 0, null, null));
            }
        }

        return Optional.empty();
    }

/*    private Optional<TurnOver> findPassHandOffTurnOver(ReportPlayerAction action) {

        ReportReRoll reportReRoll = null;
        ReportBlockRoll reportBlockRoll = null;
        boolean blockingPlayerWasInjured = false;
        boolean ballScattered = false;
        for (IReport report : reports) {
            if (report instanceof ReportReRoll) {
                reportReRoll = (ReportReRoll) report;
            } else if (report instanceof ReportSkillRoll) {
                reportBlockRoll = null;
                if (((ReportSkillRoll) report).isSuccessful()) {
                    if (!ballScattered) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                    }
                } else {
                    reportSkillRoll = (ReportSkillRoll) report;
                }
            } else if (report instanceof ReportInjury) {
                ReportInjury injury = (ReportInjury) report;
                if (injury.getDefenderId().equals(activePlayer)) {
                    if (reportBlockRoll != null) {
                        blockingPlayerWasInjured = true;
                    } else if (reportSkillRoll != null && ReportId.CHAINSAW_ROLL == reportSkillRoll.getId() && !injury.isArmorBroken()) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                    }
                    break;
                }
            } else if (report instanceof ReportBlockRoll) {
                reportBlockRoll = (ReportBlockRoll) report;
                reportSkillRoll = null;
            } else if (report instanceof ReportScatterBall) {
                ballScattered = true;
            }
        }

        if (reportSkillRoll != null) {
            return Optional.of(new TurnOver(reportSkillRoll.getId().getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, action.getActingPlayerId()));
        }

        return Optional.empty();
    }
*/
/*
    public Optional<TurnOver> findTurnover() {
        Optional<String> keyOpt = getKey();
        if (keyOpt.isPresent()) {
            String key = keyOpt.get();
            if (analyzers.containsKey(key)) {
                return (analyzers.get(key).analyze(reports));
            }
        }
        return Optional.empty();
    }


    private Optional<String> getKey() {
        IReport report = reports.pollFirst();
        if (report instanceof ReportPlayerAction) {
            return Optional.ofNullable(((ReportPlayerAction) report).getPlayerAction().getName());
        } else if (report instanceof ReportSpecialEffectRoll) {
            return Optional.ofNullable(report.getId().getName());
        }
        return Optional.empty();
    }*/
}
