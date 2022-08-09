package org.butterbrot.ffb.stats.evaluation.turnover.bb2016;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.mechanics.PassResult;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportBlockRoll;
import com.fumbbl.ffb.report.ReportBribesRoll;
import com.fumbbl.ffb.report.ReportConfusionRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.ReportSkillRoll;
import com.fumbbl.ffb.report.ReportSpecialEffectRoll;
import com.fumbbl.ffb.report.bb2016.ReportInjury;
import com.fumbbl.ffb.report.bb2016.ReportPassRoll;
import com.fumbbl.ffb.report.bb2016.ReportReferee;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import org.butterbrot.ffb.stats.adapter.bb2016.TurnOverDescription;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverState;
import org.butterbrot.ffb.stats.model.TurnOver;

import java.util.Optional;

public class TurnOverFinder extends org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder {

    private final TurnOverDescription turnOverDescription = new TurnOverDescription();
    @Override
    protected Optional<TurnOver> findTurnOver(ReportPlayerAction action) {

        TurnOverState state = new TurnOverState();

        for (IReport report : getReports()) {
            if (report instanceof ReportReRoll) {
                state.setReportReRoll((ReportReRoll) report);
            } else if (report instanceof ReportSkillRoll &&
                    // failed confusion rolls do not cause turn overs
                    !(report instanceof ReportConfusionRoll) &&
                    // failed foul appearance rolls do not cause turn overs
                    ! ReportId.FOUL_APPEARANCE_ROLL.equals(report.getId())) {
                ReportSkillRoll skillRoll = (ReportSkillRoll) report;

                state.setReportBlockRoll(null);
                if (!(ReportId.CATCH_ROLL == report.getId() && (getHomePlayers().contains(skillRoll.getPlayerId()) != getHomePlayers().contains(getActivePlayer())))) {
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
                        if (getHomePlayers().contains(skillRoll.getPlayerId()) == getHomePlayers().contains(getActivePlayer())) {
                            if (state.getReportSkillRoll() == null ||
                                    !(state.getReportSkillRoll().getId() == ReportId.PASS_ROLL &&
                                    ((ReportPassRoll) state.getReportSkillRoll()).getResult() == PassResult.FUMBLE ||
                                            state.getReportSkillRoll().getId()== ReportId.PICK_UP_ROLL)) {
                                state.setReportSkillRoll(null);
                                state.setReportReRoll(null);
                            }
                        } else if (state.getReportSkillRoll() == null && !state.isSuccessfulPass()) {
                            return Optional.of(new TurnOver(turnOverDescription.get(ReportId.HAND_OVER),
                                    0, state.getReportReRoll(),
                                    getActivePlayer()));
                        }

                    } else if (!state.isBallScattered()) {
                        state.setReportSkillRoll(null);
                        state.setReportReRoll(null);
                    }
                } else {
                    // failed skill rolls are not causing turn overs if they are safe throw or a pass from an opponent (dump off) or the ball scattered already
                    if (ReportId.SAFE_THROW_ROLL != report.getId() &&
                            !(ReportId.PASS_ROLL == report.getId() && (getHomePlayers().contains(skillRoll.getPlayerId()) != getHomePlayers().contains(getActivePlayer())))
                            && !state.isBallScattered()) {
                        state.setReportSkillRoll(skillRoll);
                    }
                }
            } else if (report instanceof ReportInjury) {
                ReportInjury injury = (ReportInjury) report;
                if (getHomePlayers().contains(injury.getDefenderId()) == getHomePlayers().contains(getActivePlayer())) {
                    if (PlayerAction.THROW_BOMB == action.getPlayerAction()) {
                        // if the bomb was fumbled by the active team, we report the fumble
                        if (state.getReportSkillRoll() != null && state.getReportSkillRoll() instanceof ReportPassRoll
                                && ((ReportPassRoll) state.getReportSkillRoll()).getResult() == PassResult.FUMBLE && (getHomePlayers().contains
                                (state.getReportSkillRoll().getPlayerId()) == getHomePlayers().contains(getActivePlayer()))) {
                            return Optional.of(new TurnOver(turnOverDescription.get(SpecialEffect.BOMB),
                                    state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(),
                                    state.getReportSkillRoll().getPlayerId()));
                        }
                        return Optional.of(new TurnOver(turnOverDescription.get(SpecialEffect.BOMB), 0,
                                state.getReportReRoll(), getActivePlayer()));
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
                            return Optional.of(new TurnOver(turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), 0,
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
                    return Optional.of(new TurnOver(turnOverDescription.get(state.getReportSkillRoll().getId()),
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
            return Optional.of(new TurnOver(turnOverDescription.get(ReportId.FOUL), 0, null, getActivePlayer()));
        }

        if (state.isSuccessfulPass() && state.isBallScattered()) {
            return Optional.of(new TurnOver(turnOverDescription.get(ReportId.PASS_ROLL), 0, state.getReportReRoll(),
              getActivePlayer()));
        }

        if (state.getReportSkillRoll() != null) {
            if (ReportId.INTERCEPTION_ROLL == state.getReportSkillRoll().getId()) {
                return Optional.of(new TurnOver(turnOverDescription.get(state.getReportSkillRoll().getId()),
                        state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), getActivePlayer()));
            } else if (ReportId.RIGHT_STUFF_ROLL == state.getReportSkillRoll().getId()) {
                if (state.isBallScattered()) {
                    return Optional.of(new TurnOver(turnOverDescription.get(state.getReportSkillRoll().getId()),
                            state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), state
                            .getReportSkillRoll().getPlayerId()));
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.of(new TurnOver(turnOverDescription.get(state.getReportSkillRoll().getId()),
                        state.getReportSkillRoll().getMinimumRoll(), state.getReportReRoll(), state.getReportSkillRoll()
                        .getPlayerId()));
            }
        }

        if (state.getReportBlockRoll() != null && state.isBlockingPlayerWasInjured()) {
            int blockDiceCount = state.getReportBlockRoll().getBlockRoll().length;
            boolean actingTeamWasChoosing = getHomePlayers().contains(getActivePlayer()) == (state.getReportBlockRoll()
                    .getChoosingTeamId().equals(getHomeTeam()));
            if (!actingTeamWasChoosing) {
                blockDiceCount *= -1;
            }
            return Optional.of(new TurnOver(turnOverDescription.get(state.getReportBlockRoll().getId()),
                    blockDiceCount, state.getReportReRoll(), getActivePlayer()));
        }

        return Optional.empty();
    }

    @Override
    protected Optional<TurnOver> findWizardTurnOver(ReportSpecialEffectRoll wizardReport) {
        boolean actingTeamInjured = false;
        boolean ballBounced = false;
        for (IReport report : getReports()) {
            if (report instanceof ReportInjury && (getHomePlayers().contains(((ReportInjury) report).getDefenderId()) == isHomeTeamActive())) {
                actingTeamInjured = true;
            } else if (report instanceof ReportScatterBall) {
                ballBounced = true;
            } else if (report instanceof ReportTurnEnd && actingTeamInjured && ballBounced) {
                return Optional.of(new TurnOver(turnOverDescription.get(wizardReport.getSpecialEffect()), 0, null, null));
            }
        }

        return Optional.empty();
    }
}
