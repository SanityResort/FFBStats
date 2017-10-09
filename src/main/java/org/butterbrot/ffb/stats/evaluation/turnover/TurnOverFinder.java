package org.butterbrot.ffb.stats.evaluation.turnover;

import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import com.balancedbytes.games.ffb.report.ReportBribesRoll;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.report.ReportPassRoll;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import com.balancedbytes.games.ffb.report.ReportReferee;
import com.balancedbytes.games.ffb.report.ReportScatterBall;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import org.butterbrot.ffb.stats.adapter.TurnOverDescription;
import org.butterbrot.ffb.stats.model.TurnOver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TurnOverFinder {

    private boolean homeTeamActive;

    private String activePlayer;

    private Deque<IReport> reports = new ArrayDeque<>();

    private String homeTeam;
    private Set<String> homePlayers = new HashSet<>();

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

        TurnOverState state = new TurnOverState();

        for (IReport report : reports) {
            if (report instanceof ReportReRoll) {
                state.setReportReRoll((ReportReRoll) report);
            } else if (report instanceof ReportSkillRoll) {
                ReportSkillRoll skillRoll = (ReportSkillRoll) report;
                state.setReportBlockRoll(null);
                if (!(ReportId.CATCH_ROLL == report.getId() && (homePlayers.contains(skillRoll.getPlayerId()) != homePlayers.contains(activePlayer)))) {
                    // if a successful pass was not caught by an opponent we do not reset the flag
                    state.setSuccessfulPass(false);
                }
                if (skillRoll.isSuccessful()) {
                    if (ReportId.INTERCEPTION_ROLL == report.getId()) {
                        state.setReportSkillRoll(skillRoll);
                    } else if (ReportId.PASS_ROLL == report.getId()) {
                        state.setReportSkillRoll(null);
                        state.setReportReRoll(null);
                        state.setSuccessfulPass(true);
                    } else if (ReportId.CATCH_ROLL == report.getId()) {
                        if (homePlayers.contains(skillRoll.getPlayerId()) == homePlayers.contains(activePlayer)) {
                            if (state.getReportSkillRoll() == null ||
                                    !(state.getReportSkillRoll().getId() == ReportId.PASS_ROLL &&
                                    ((ReportPassRoll) state.getReportSkillRoll()).isFumble() ||
                                            state.getReportSkillRoll().getId()== ReportId.PICK_UP_ROLL)) {
                                state.setReportSkillRoll(null);
                                state.setReportReRoll(null);
                            }
                        } else if (state.getReportSkillRoll() == null && !state.isSuccessfulPass()) {
                            return Optional.of(new TurnOver(TurnOverDescription.get(ReportId.HAND_OVER),
                                    0, state.getReportReRoll(),
                                    activePlayer));
                        }

                    } else if (!state.isBallScattered()) {
                        state.setReportSkillRoll(null);
                        state.setReportReRoll(null);
                    }
                } else {
                    // failed skill rolls are not causing turn overs if they are safe throw or a pass from an opponent (dump off) or the ball scattered already
                    if (ReportId.SAFE_THROW_ROLL != report.getId() &&
                            !(ReportId.PASS_ROLL == report.getId() && (homePlayers.contains(skillRoll.getPlayerId()) != homePlayers.contains(activePlayer)))
                            && !state.isBallScattered()) {
                        state.setReportSkillRoll(skillRoll);
                    }
                }
            } else if (report instanceof ReportInjury) {
                ReportInjury injury = (ReportInjury) report;
                if (homePlayers.contains(injury.getDefenderId()) == homePlayers.contains(activePlayer)) {
                    if (PlayerAction.THROW_BOMB == action.getPlayerAction()) {
                        // if the bomb was fumbled by the active team, we report the fumble
                        if (state.getReportSkillRoll() != null && state.getReportSkillRoll() instanceof ReportPassRoll
                                && ((ReportPassRoll) state.getReportSkillRoll()).isFumble() && (homePlayers.contains
                                (state.getReportSkillRoll().getPlayerId()) == homePlayers.contains(activePlayer))) {
                            return Optional.of(new TurnOver(TurnOverDescription.get(SpecialEffect.BOMB),
                                    state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(),
                                    state.getReportSkillRoll().getPlayerId()));
                        }
                        return Optional.of(new TurnOver(TurnOverDescription.get(SpecialEffect.BOMB), 0,
                                state.getReportReRoll(), activePlayer));
                    } else if (state.getReportBlockRoll() != null) {
                        state.setBlockingPlayerWasInjured(true);
                    } else if (state.getReportSkillRoll() != null && ReportId.CHAINSAW_ROLL == state.getReportSkillRoll().getId() &&
                            !injury.isArmorBroken()) {
                        state.setReportSkillRoll(null);
                        state.setReportReRoll(null);
                    } else if (state.getReportSkillRoll() != null && ReportId.RIGHT_STUFF_ROLL == state
                            .getReportSkillRoll().getId()) {
                        if (injury.getDefenderId().equals(state.getReportSkillRoll().getPlayerId())) {
                            state.setLandingFailed(true);
                            continue;
                        } else {
                            return Optional.of(new TurnOver(TurnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), 0,
                                    state.getReportReRoll(), state.getReportSkillRoll().getPlayerId()));
                        }
                    }
                    break;
                }
            } else if (report instanceof ReportBlockRoll) {
                state.setReportBlockRoll((ReportBlockRoll) report);
                state.setReportSkillRoll(null);
            } else if (report instanceof ReportScatterBall) {
                if (state.isLandingFailed()) {
                    return Optional.of(new TurnOver(TurnOverDescription.get(state.getReportSkillRoll().getId()),
                            state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), state
                            .getReportSkillRoll().getPlayerId()));
                }
                state.setBallScattered(true);
            } else if (report instanceof ReportReferee) {
                state.setSentOff(((ReportReferee) report).isFoulingPlayerBanned());
            } else if (report instanceof ReportBribesRoll) {
                state.setSentOff(!((ReportBribesRoll) report).isSuccessful());
            }
        }

        if (state.isSentOff()) {
            return Optional.of(new TurnOver(TurnOverDescription.get(ReportId.FOUL), 0, null, activePlayer));
        }

        if (state.isSuccessfulPass() && state.isBallScattered()) {
            return Optional.of(new TurnOver(TurnOverDescription.get(ReportId.PASS_ROLL), 0, state.getReportReRoll(),
                    activePlayer));
        }

        if (state.getReportSkillRoll() != null) {
            if (ReportId.INTERCEPTION_ROLL == state.getReportSkillRoll().getId()) {
                return Optional.of(new TurnOver(TurnOverDescription.get(state.getReportSkillRoll().getId()),
                        state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), activePlayer));
            } else if (ReportId.RIGHT_STUFF_ROLL == state.getReportSkillRoll().getId()) {
                if (state.isBallScattered()) {
                    return Optional.of(new TurnOver(TurnOverDescription.get(state.getReportSkillRoll().getId()),
                            state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), state
                            .getReportSkillRoll().getPlayerId()));
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.of(new TurnOver(TurnOverDescription.get(state.getReportSkillRoll().getId()),
                        state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), state.getReportSkillRoll()
                        .getPlayerId()));
            }
        }

        if (state.getReportBlockRoll() != null && state.isBlockingPlayerWasInjured()) {
            int blockDiceCount = state.getReportBlockRoll().getBlockRoll().length;
            boolean actingTeamWasChoosing = homePlayers.contains(activePlayer) == (state.getReportBlockRoll()
                    .getChoosingTeamId().equals(homeTeam));
            if (!actingTeamWasChoosing) {
                blockDiceCount *= -1;
            }
            return Optional.of(new TurnOver(TurnOverDescription.get(state.getReportBlockRoll().getId()),
                    blockDiceCount, state.getReportReRoll(), activePlayer));
        }

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
                return Optional.of(new TurnOver(TurnOverDescription.get(wizardReport.getSpecialEffect()), 0, null, null));
            }
        }

        return Optional.empty();
    }
}
