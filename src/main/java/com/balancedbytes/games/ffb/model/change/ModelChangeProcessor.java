/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.BloodSpot;
import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldMarker;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.Inducement;
import com.balancedbytes.games.ffb.LeaderState;
import com.balancedbytes.games.ffb.MoveSquare;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerMarker;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.PushbackSquare;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.TrackNumber;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameOptions;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.model.TeamResult;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.option.IGameOption;
import java.util.Date;

public class ModelChangeProcessor {
    public boolean apply(Game pGame, ModelChange pModelChange) {
        if (pGame == null || pModelChange == null || pModelChange.getChangeId() == null) {
            return false;
        }
        switch (pModelChange.getChangeId()) {
            case ACTING_PLAYER_MARK_SKILL_USED: {
                pGame.getActingPlayer().markSkillUsed((Skill)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_CURRENT_MOVE: {
                pGame.getActingPlayer().setCurrentMove((Integer)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_DODGING: {
                pGame.getActingPlayer().setDodging((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_GOING_FOR_IT: {
                pGame.getActingPlayer().setGoingForIt((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_HAS_BLOCKED: {
                pGame.getActingPlayer().setHasBlocked((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_HAS_FED: {
                pGame.getActingPlayer().setHasFed((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_HAS_FOULED: {
                pGame.getActingPlayer().setHasFouled((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_HAS_MOVED: {
                pGame.getActingPlayer().setHasMoved((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_HAS_PASSED: {
                pGame.getActingPlayer().setHasPassed((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_LEAPING: {
                pGame.getActingPlayer().setLeaping((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_PLAYER_ACTION: {
                pGame.getActingPlayer().setPlayerAction((PlayerAction)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_PLAYER_ID: {
                pGame.getActingPlayer().setPlayerId((String)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_STANDING_UP: {
                pGame.getActingPlayer().setStandingUp((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_STRENGTH: {
                pGame.getActingPlayer().setStrength((Integer)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_SUFFERING_ANIMOSITY: {
                pGame.getActingPlayer().setSufferingAnimosity((Boolean)pModelChange.getValue());
                return true;
            }
            case ACTING_PLAYER_SET_SUFFERING_BLOOD_LUST: {
                pGame.getActingPlayer().setSufferingBloodLust((Boolean)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_BLOOD_SPOT: {
                pGame.getFieldModel().add((BloodSpot)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_CARD: {
                pGame.getFieldModel().addCard(pGame.getPlayerById(pModelChange.getKey()), (Card)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_CARD_EFFECT: {
                pGame.getFieldModel().addCardEffect(pGame.getPlayerById(pModelChange.getKey()), (CardEffect)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_DICE_DECORATION: {
                pGame.getFieldModel().add((DiceDecoration)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_FIELD_MARKER: {
                pGame.getFieldModel().add((FieldMarker)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_MOVE_SQUARE: {
                pGame.getFieldModel().add((MoveSquare)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_PLAYER_MARKER: {
                pGame.getFieldModel().add((PlayerMarker)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_PUSHBACK_SQUARE: {
                pGame.getFieldModel().add((PushbackSquare)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_ADD_TRACK_NUMBER: {
                pGame.getFieldModel().add((TrackNumber)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_CARD: {
                pGame.getFieldModel().removeCard(pGame.getPlayerById(pModelChange.getKey()), (Card)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_CARD_EFFECT: {
                pGame.getFieldModel().removeCardEffect(pGame.getPlayerById(pModelChange.getKey()), (CardEffect)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_DICE_DECORATION: {
                pGame.getFieldModel().remove((DiceDecoration)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_FIELD_MARKER: {
                pGame.getFieldModel().remove((FieldMarker)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_MOVE_SQUARE: {
                pGame.getFieldModel().remove((MoveSquare)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_PLAYER: {
                pGame.getFieldModel().remove(pGame.getPlayerById(pModelChange.getKey()));
                return true;
            }
            case FIELD_MODEL_REMOVE_PLAYER_MARKER: {
                pGame.getFieldModel().remove((PlayerMarker)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_PUSHBACK_SQUARE: {
                pGame.getFieldModel().remove((PushbackSquare)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_REMOVE_TRACK_NUMBER: {
                pGame.getFieldModel().remove((TrackNumber)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_BALL_COORDINATE: {
                pGame.getFieldModel().setBallCoordinate((FieldCoordinate)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_BALL_IN_PLAY: {
                pGame.getFieldModel().setBallInPlay((Boolean)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_BALL_MOVING: {
                pGame.getFieldModel().setBallMoving((Boolean)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_BOMB_COORDINATE: {
                pGame.getFieldModel().setBombCoordinate((FieldCoordinate)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_BOMB_MOVING: {
                pGame.getFieldModel().setBombMoving((Boolean)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_PLAYER_COORDINATE: {
                pGame.getFieldModel().setPlayerCoordinate(pGame.getPlayerById(pModelChange.getKey()), (FieldCoordinate)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_PLAYER_STATE: {
                pGame.getFieldModel().setPlayerState(pGame.getPlayerById(pModelChange.getKey()), (PlayerState)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_RANGE_RULER: {
                pGame.getFieldModel().setRangeRuler((RangeRuler)pModelChange.getValue());
                return true;
            }
            case FIELD_MODEL_SET_WEATHER: {
                pGame.getFieldModel().setWeather((Weather)pModelChange.getValue());
                return true;
            }
            case GAME_SET_CONCESSION_POSSIBLE: {
                pGame.setConcessionPossible((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_DEFENDER_ACTION: {
                pGame.setDefenderAction((PlayerAction)pModelChange.getValue());
                return true;
            }
            case GAME_SET_DEFENDER_ID: {
                pGame.setDefenderId(pModelChange.getKey());
                return true;
            }
            case GAME_SET_DIALOG_PARAMETER: {
                pGame.setDialogParameter((IDialogParameter)pModelChange.getValue());
                return true;
            }
            case GAME_SET_FINISHED: {
                pGame.setFinished((Date)pModelChange.getValue());
                return true;
            }
            case GAME_SET_HALF: {
                pGame.setHalf((Integer)pModelChange.getValue());
                return true;
            }
            case GAME_SET_HOME_FIRST_OFFENSE: {
                pGame.setHomeFirstOffense((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_HOME_PLAYING: {
                pGame.setHomePlaying((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_ID: {
                pGame.setId((Long)pModelChange.getValue());
                return true;
            }
            case GAME_SET_PASS_COORDINATE: {
                pGame.setPassCoordinate((FieldCoordinate)pModelChange.getValue());
                return true;
            }
            case GAME_SET_SCHEDULED: {
                pGame.setScheduled((Date)pModelChange.getValue());
                return true;
            }
            case GAME_SET_SETUP_OFFENSE: {
                pGame.setSetupOffense((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_STARTED: {
                pGame.setStarted((Date)pModelChange.getValue());
                return true;
            }
            case GAME_SET_TESTING: {
                pGame.setTesting((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_ADMIN_MODE: {
                pGame.setAdminMode((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_THROWER_ID: {
                pGame.setThrowerId(pModelChange.getKey());
                return true;
            }
            case GAME_SET_THROWER_ACTION: {
                pGame.setThrowerAction((PlayerAction)pModelChange.getValue());
                return true;
            }
            case GAME_SET_TIMEOUT_ENFORCED: {
                pGame.setTimeoutEnforced((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_TIMEOUT_POSSIBLE: {
                pGame.setTimeoutPossible((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_SET_TURN_MODE: {
                pGame.setTurnMode((TurnMode)pModelChange.getValue());
                return true;
            }
            case GAME_SET_LAST_TURN_MODE: {
                pGame.setLastTurnMode((TurnMode)pModelChange.getValue());
                return true;
            }
            case GAME_SET_WAITING_FOR_OPPONENT: {
                pGame.setWaitingForOpponent((Boolean)pModelChange.getValue());
                return true;
            }
            case GAME_OPTIONS_ADD_OPTION: {
                pGame.getOptions().addOption((IGameOption)pModelChange.getValue());
                return true;
            }
            case INDUCEMENT_SET_ACTIVATE_CARD: {
                this.getInducementSet(pGame, this.isHomeData(pModelChange)).activateCard((Card)pModelChange.getValue());
                return true;
            }
            case INDUCEMENT_SET_ADD_AVAILABLE_CARD: {
                this.getInducementSet(pGame, this.isHomeData(pModelChange)).addAvailableCard((Card)pModelChange.getValue());
                return true;
            }
            case INDUCEMENT_SET_ADD_INDUCEMENT: {
                this.getInducementSet(pGame, this.isHomeData(pModelChange)).addInducement((Inducement)pModelChange.getValue());
                return true;
            }
            case INDUCEMENT_SET_DEACTIVATE_CARD: {
                this.getInducementSet(pGame, this.isHomeData(pModelChange)).deactivateCard((Card)pModelChange.getValue());
                return true;
            }
            case INDUCEMENT_SET_REMOVE_AVAILABLE_CARD: {
                this.getInducementSet(pGame, this.isHomeData(pModelChange)).removeAvailableCard((Card)pModelChange.getValue());
                return true;
            }
            case INDUCEMENT_SET_REMOVE_INDUCEMENT: {
                this.getInducementSet(pGame, this.isHomeData(pModelChange)).removeInducement((Inducement)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_BLOCKS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setBlocks((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_CASUALTIES: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setCasualties((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_COMPLETIONS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setCompletions((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_CURRENT_SPPS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setCurrentSpps((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_DEFECTING: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setDefecting((Boolean)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_FOULS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setFouls((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_HAS_USED_SECRET_WEAPON: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setHasUsedSecretWeapon((Boolean)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_INTERCEPTIONS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setInterceptions((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_PASSING: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setPassing((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_PLAYER_AWARDS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setPlayerAwards((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_RUSHING: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setRushing((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_SEND_TO_BOX_BY_PLAYER_ID: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setSendToBoxByPlayerId((String)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_SEND_TO_BOX_HALF: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setSendToBoxHalf((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_SEND_TO_BOX_REASON: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setSendToBoxReason((SendToBoxReason)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_SEND_TO_BOX_TURN: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setSendToBoxTurn((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_SERIOUS_INJURY: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setSeriousInjury((SeriousInjury)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_SERIOUS_INJURY_DECAY: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setSeriousInjuryDecay((SeriousInjury)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_TOUCHDOWNS: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setTouchdowns((Integer)pModelChange.getValue());
                return true;
            }
            case PLAYER_RESULT_SET_TURNS_PLAYED: {
                this.getPlayerResult(pGame, pModelChange.getKey()).setTurnsPlayed((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_CONCEDED: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setConceded((Boolean)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_FAME: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setFame((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_BADLY_HURT_SUFFERED: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setBadlyHurtSuffered((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_FAN_FACTOR_MODIFIER: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setFanFactorModifier((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_PETTY_CASH_TRANSFERRED: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setPettyCashTransferred((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_PETTY_CASH_USED: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setPettyCashUsed((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_RAISED_DEAD: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setRaisedDead((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_RIP_SUFFERED: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setRipSuffered((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_SCORE: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setScore((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_SERIOUS_INJURY_SUFFERED: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setSeriousInjurySuffered((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_SPECTATORS: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setSpectators((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_SPIRALLING_EXPENSES: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setSpirallingExpenses((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_TEAM_VALUE: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setTeamValue((Integer)pModelChange.getValue());
                return true;
            }
            case TEAM_RESULT_SET_WINNINGS: {
                this.getTeamResult(pGame, this.isHomeData(pModelChange)).setWinnings((Integer)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_APOTHECARIES: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setApothecaries((Integer)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_BLITZ_USED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setBlitzUsed((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_FIRST_TURN_AFTER_KICKOFF: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setFirstTurnAfterKickoff((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_FOUL_USED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setFoulUsed((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_HAND_OVER_USED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setHandOverUsed((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_LEADER_STATE: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setLeaderState((LeaderState)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_PASS_USED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setPassUsed((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_RE_ROLLS: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setReRolls((Integer)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_RE_ROLL_USED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setReRollUsed((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_TURN_NR: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setTurnNr((Integer)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_TURN_STARTED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setTurnStarted((Boolean)pModelChange.getValue());
                return true;
            }
            case TURN_DATA_SET_COACH_BANNED: {
                this.getTurnData(pGame, this.isHomeData(pModelChange)).setCoachBanned((Boolean)pModelChange.getValue());
                return true;
            }
        }
        return false;
    }

    public ModelChange transform(ModelChange pModelChange) {
        if (pModelChange == null || pModelChange.getChangeId() == null) {
            return null;
        }
        switch (pModelChange.getChangeId()) {
            case FIELD_MODEL_ADD_BLOOD_SPOT: {
                BloodSpot bloodSpot = (BloodSpot)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), bloodSpot != null ? bloodSpot.transform() : null);
            }
            case FIELD_MODEL_ADD_DICE_DECORATION: 
            case FIELD_MODEL_REMOVE_DICE_DECORATION: {
                DiceDecoration diceDecoration = (DiceDecoration)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), diceDecoration != null ? diceDecoration.transform() : null);
            }
            case FIELD_MODEL_ADD_FIELD_MARKER: 
            case FIELD_MODEL_REMOVE_FIELD_MARKER: {
                FieldMarker fieldMarker = (FieldMarker)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), fieldMarker != null ? fieldMarker.transform() : null);
            }
            case FIELD_MODEL_ADD_MOVE_SQUARE: 
            case FIELD_MODEL_REMOVE_MOVE_SQUARE: {
                MoveSquare moveSquare = (MoveSquare)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), moveSquare != null ? moveSquare.transform() : null);
            }
            case FIELD_MODEL_ADD_PLAYER_MARKER: 
            case FIELD_MODEL_REMOVE_PLAYER_MARKER: {
                PlayerMarker playerMarker = (PlayerMarker)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), playerMarker != null ? playerMarker.transform() : null);
            }
            case FIELD_MODEL_ADD_PUSHBACK_SQUARE: 
            case FIELD_MODEL_REMOVE_PUSHBACK_SQUARE: {
                PushbackSquare pushbackSquare = (PushbackSquare)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), pushbackSquare != null ? pushbackSquare.transform() : null);
            }
            case FIELD_MODEL_ADD_TRACK_NUMBER: 
            case FIELD_MODEL_REMOVE_TRACK_NUMBER: {
                TrackNumber trackNumber = (TrackNumber)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), trackNumber != null ? trackNumber.transform() : null);
            }
            case FIELD_MODEL_SET_BALL_COORDINATE: {
                FieldCoordinate ballCoordinate = (FieldCoordinate)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), ballCoordinate != null ? ballCoordinate.transform() : null);
            }
            case FIELD_MODEL_SET_BOMB_COORDINATE: {
                FieldCoordinate bombCoordinate = (FieldCoordinate)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), bombCoordinate != null ? bombCoordinate.transform() : null);
            }
            case FIELD_MODEL_SET_PLAYER_COORDINATE: {
                FieldCoordinate playerCoordinate = (FieldCoordinate)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), playerCoordinate != null ? playerCoordinate.transform() : null);
            }
            case FIELD_MODEL_SET_RANGE_RULER: {
                RangeRuler rangeRuler = (RangeRuler)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), rangeRuler != null ? rangeRuler.transform() : null);
            }
            case GAME_SET_DIALOG_PARAMETER: {
                IDialogParameter dialogParameter = (IDialogParameter)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), dialogParameter != null ? dialogParameter.transform() : null);
            }
            case GAME_SET_HOME_FIRST_OFFENSE: 
            case GAME_SET_HOME_PLAYING: {
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), (Boolean)pModelChange.getValue() == false);
            }
            case GAME_SET_PASS_COORDINATE: {
                FieldCoordinate passCoordinate = (FieldCoordinate)pModelChange.getValue();
                return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), passCoordinate != null ? passCoordinate.transform() : null);
            }
            case INDUCEMENT_SET_ACTIVATE_CARD: 
            case INDUCEMENT_SET_ADD_AVAILABLE_CARD: 
            case INDUCEMENT_SET_ADD_INDUCEMENT: 
            case INDUCEMENT_SET_DEACTIVATE_CARD: 
            case INDUCEMENT_SET_REMOVE_AVAILABLE_CARD: 
            case INDUCEMENT_SET_REMOVE_INDUCEMENT: {
                return new ModelChange(pModelChange.getChangeId(), this.isHomeData(pModelChange) ? "away" : "home", pModelChange.getValue());
            }
            case TEAM_RESULT_SET_CONCEDED: 
            case TEAM_RESULT_SET_FAME: 
            case TEAM_RESULT_SET_BADLY_HURT_SUFFERED: 
            case TEAM_RESULT_SET_FAN_FACTOR_MODIFIER: 
            case TEAM_RESULT_SET_PETTY_CASH_TRANSFERRED: 
            case TEAM_RESULT_SET_PETTY_CASH_USED: 
            case TEAM_RESULT_SET_RAISED_DEAD: 
            case TEAM_RESULT_SET_RIP_SUFFERED: 
            case TEAM_RESULT_SET_SCORE: 
            case TEAM_RESULT_SET_SERIOUS_INJURY_SUFFERED: 
            case TEAM_RESULT_SET_SPECTATORS: 
            case TEAM_RESULT_SET_SPIRALLING_EXPENSES: 
            case TEAM_RESULT_SET_TEAM_VALUE: 
            case TEAM_RESULT_SET_WINNINGS: 
            case TURN_DATA_SET_APOTHECARIES: 
            case TURN_DATA_SET_BLITZ_USED: 
            case TURN_DATA_SET_FIRST_TURN_AFTER_KICKOFF: 
            case TURN_DATA_SET_FOUL_USED: 
            case TURN_DATA_SET_HAND_OVER_USED: 
            case TURN_DATA_SET_LEADER_STATE: 
            case TURN_DATA_SET_PASS_USED: 
            case TURN_DATA_SET_RE_ROLLS: 
            case TURN_DATA_SET_RE_ROLL_USED: 
            case TURN_DATA_SET_TURN_NR: 
            case TURN_DATA_SET_TURN_STARTED: 
            case TURN_DATA_SET_COACH_BANNED: {
                return new ModelChange(pModelChange.getChangeId(), this.isHomeData(pModelChange) ? "away" : "home", pModelChange.getValue());
            }
        }
        return new ModelChange(pModelChange.getChangeId(), pModelChange.getKey(), pModelChange.getValue());
    }

    private boolean isHomeData(ModelChange pChange) {
        return "home".equals(pChange.getKey());
    }

    private TeamResult getTeamResult(Game pGame, boolean pHomeData) {
        return pHomeData ? pGame.getGameResult().getTeamResultHome() : pGame.getGameResult().getTeamResultAway();
    }

    private TurnData getTurnData(Game pGame, boolean pHomeData) {
        return pHomeData ? pGame.getTurnDataHome() : pGame.getTurnDataAway();
    }

    private InducementSet getInducementSet(Game pGame, boolean pHomeData) {
        return pHomeData ? pGame.getTurnDataHome().getInducementSet() : pGame.getTurnDataAway().getInducementSet();
    }

    private PlayerResult getPlayerResult(Game pGame, String pPlayerId) {
        return pGame.getGameResult().getPlayerResult(pGame.getPlayerById(pPlayerId));
    }

}

