/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.dialog.DialogParameterFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonDateOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonLongOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.GameOptions;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TeamResult;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.model.change.ModelChangeObservable;
import com.balancedbytes.games.ffb.util.DateTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilActingPlayer;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Date;

public class Game
extends ModelChangeObservable
implements IJsonSerializable {
    private long fId;
    private Date fScheduled;
    private Date fStarted;
    private Date fFinished;
    private int fHalf;
    private TurnMode fTurnMode;
    private FieldCoordinate fPassCoordinate;
    private boolean fHomePlaying;
    private boolean fHomeFirstOffense;
    private boolean fSetupOffense;
    private boolean fWaitingForOpponent;
    private String fDefenderId;
    private PlayerAction fDefenderAction;
    private String fThrowerId;
    private PlayerAction fThrowerAction;
    private long fTurnTime;
    private boolean fTimeoutPossible;
    private boolean fTimeoutEnforced;
    private boolean fConcessionPossible;
    private boolean fTesting;
    private IDialogParameter fDialogParameter;
    private FieldModel fFieldModel;
    private Team fTeamHome;
    private Team fTeamAway;
    private TurnData fTurnDataHome;
    private TurnData fTurnDataAway;
    private ActingPlayer fActingPlayer;
    private GameResult fGameResult;
    private GameOptions fOptions;
    private transient long fGameTime;

    public Game() {
        this.setFieldModel(new FieldModel(this));
        this.fTurnDataHome = new TurnData(this, true);
        this.fTurnDataAway = new TurnData(this, false);
        this.fActingPlayer = new ActingPlayer(this);
        this.fGameResult = new GameResult(this);
        this.fHomePlaying = true;
        this.setTeamHome(new Team());
        this.setTeamAway(new Team());
        this.fOptions = new GameOptions(this);
    }

    public void setId(long pId) {
        if (pId == this.fId) {
            return;
        }
        this.fId = pId;
        this.notifyObservers(ModelChangeId.GAME_SET_ID, null, this.fId);
    }

    public long getId() {
        return this.fId;
    }

    public GameResult getGameResult() {
        return this.fGameResult;
    }

    public TurnData getTurnDataHome() {
        return this.fTurnDataHome;
    }

    public TurnData getTurnDataAway() {
        return this.fTurnDataAway;
    }

    public int getHalf() {
        return this.fHalf;
    }

    public void setHalf(int pHalf) {
        if (pHalf == this.fHalf) {
            return;
        }
        this.fHalf = pHalf;
        this.notifyObservers(ModelChangeId.GAME_SET_HALF, null, this.fHalf);
    }

    public TurnMode getTurnMode() {
        return this.fTurnMode;
    }

    public void setTurnMode(TurnMode pTurnMode) {
        if (pTurnMode == this.fTurnMode) {
            return;
        }
        this.fTurnMode = pTurnMode;
        this.notifyObservers(ModelChangeId.GAME_SET_TURN_MODE, null, this.fTurnMode);
    }

    public ActingPlayer getActingPlayer() {
        return this.fActingPlayer;
    }

    public Team getTeamHome() {
        return this.fTeamHome;
    }

    public Team getTeamAway() {
        return this.fTeamAway;
    }

    public FieldModel getFieldModel() {
        return this.fFieldModel;
    }

    public void setFieldModel(FieldModel pFieldModel) {
        this.fFieldModel = pFieldModel;
    }

    public boolean isHomePlaying() {
        return this.fHomePlaying;
    }

    public void setHomePlaying(boolean pHomePlaying) {
        if (pHomePlaying == this.fHomePlaying) {
            return;
        }
        this.fHomePlaying = pHomePlaying;
        this.notifyObservers(ModelChangeId.GAME_SET_HOME_PLAYING, null, this.fHomePlaying);
    }

    public FieldCoordinate getPassCoordinate() {
        return this.fPassCoordinate;
    }

    public void setPassCoordinate(FieldCoordinate pPassCoordinate) {
        if (FieldCoordinate.equals(pPassCoordinate, this.fPassCoordinate)) {
            return;
        }
        this.fPassCoordinate = pPassCoordinate;
        this.notifyObservers(ModelChangeId.GAME_SET_PASS_COORDINATE, null, this.fPassCoordinate);
    }

    public boolean isHomeFirstOffense() {
        return this.fHomeFirstOffense;
    }

    public void setHomeFirstOffense(boolean pHomeFirstOffense) {
        if (pHomeFirstOffense == this.fHomeFirstOffense) {
            return;
        }
        this.fHomeFirstOffense = pHomeFirstOffense;
        this.notifyObservers(ModelChangeId.GAME_SET_HOME_FIRST_OFFENSE, null, this.fHomeFirstOffense);
    }

    public void startTurn() {
        this.setPassCoordinate(null);
        UtilActingPlayer.changeActingPlayer(this, null, null, false);
        this.getTurnDataHome().startTurn();
        this.getTurnDataAway().startTurn();
        this.setThrowerId(null);
        this.setThrowerAction(null);
        this.setDefenderId(null);
        this.setDefenderAction(null);
        this.setWaitingForOpponent(false);
        this.setTimeoutPossible(false);
        this.setTimeoutEnforced(false);
        this.setConcessionPossible(true);
    }

    public TurnData getTurnData() {
        return this.isHomePlaying() ? this.getTurnDataHome() : this.getTurnDataAway();
    }

    public boolean isSetupOffense() {
        return this.fSetupOffense;
    }

    public void setSetupOffense(boolean pSetupOffense) {
        if (pSetupOffense == this.fSetupOffense) {
            return;
        }
        this.fSetupOffense = pSetupOffense;
        this.notifyObservers(ModelChangeId.GAME_SET_SETUP_OFFENSE, null, this.fSetupOffense);
    }

    public Team getTeamById(String pTeamId) {
        Team team = null;
        if (pTeamId != null) {
            if (this.getTeamHome() != null && pTeamId.equals(this.getTeamHome().getId())) {
                team = this.getTeamHome();
            }
            if (this.getTeamAway() != null && pTeamId.equals(this.getTeamAway().getId())) {
                team = this.getTeamAway();
            }
        }
        return team;
    }

    public Player getPlayerById(String pPlayerId) {
        Player player = null;
        if (this.getTeamHome() != null) {
            player = this.getTeamHome().getPlayerById(pPlayerId);
        }
        if (player == null && this.getTeamAway() != null) {
            player = this.getTeamAway().getPlayerById(pPlayerId);
        }
        return player;
    }

    public Player[] getPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        Player[] playersHome = this.getTeamHome().getPlayers();
        for (int i = 0; i < playersHome.length; ++i) {
            allPlayers.add(playersHome[i]);
        }
        Player[] playersAway = this.getTeamAway().getPlayers();
        for (int i2 = 0; i2 < playersAway.length; ++i2) {
            allPlayers.add(playersAway[i2]);
        }
        return allPlayers.toArray(new Player[allPlayers.size()]);
    }

    public void setTeamHome(Team pTeam) {
        this.fTeamHome = pTeam;
        this.fGameResult.getTeamResultHome().setTeam(pTeam);
    }

    public void setTeamAway(Team pTeam) {
        this.fTeamAway = pTeam;
        this.fGameResult.getTeamResultAway().setTeam(pTeam);
    }

    public Date getScheduled() {
        return this.fScheduled;
    }

    public void setScheduled(Date pScheduled) {
        if (DateTool.isEqual(pScheduled, this.fScheduled)) {
            return;
        }
        this.fScheduled = pScheduled;
        this.notifyObservers(ModelChangeId.GAME_SET_SCHEDULED, null, this.fScheduled);
    }

    public Date getStarted() {
        return this.fStarted;
    }

    public void setStarted(Date pStarted) {
        if (DateTool.isEqual(pStarted, this.fStarted)) {
            return;
        }
        this.fStarted = pStarted;
        this.notifyObservers(ModelChangeId.GAME_SET_STARTED, null, this.fStarted);
    }

    public void setDefenderId(String pDefenderId) {
        if (StringTool.isEqual(pDefenderId, this.fDefenderId)) {
            return;
        }
        this.fDefenderId = pDefenderId;
        this.notifyObservers(ModelChangeId.GAME_SET_DEFENDER_ID, this.fDefenderId, null);
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public Player getDefender() {
        return this.getPlayerById(this.getDefenderId());
    }

    public void setDefenderAction(PlayerAction pDefenderAction) {
        if (pDefenderAction == this.fDefenderAction) {
            return;
        }
        this.fDefenderAction = pDefenderAction;
        this.notifyObservers(ModelChangeId.GAME_SET_DEFENDER_ACTION, null, this.fDefenderAction);
    }

    public PlayerAction getDefenderAction() {
        return this.fDefenderAction;
    }

    public void setThrowerId(String pThrowerId) {
        if (StringTool.isEqual(pThrowerId, this.fThrowerId)) {
            return;
        }
        this.fThrowerId = pThrowerId;
        this.notifyObservers(ModelChangeId.GAME_SET_THROWER_ID, this.fThrowerId, null);
    }

    public String getThrowerId() {
        return this.fThrowerId;
    }

    public Player getThrower() {
        return this.getPlayerById(this.getThrowerId());
    }

    public void setThrowerAction(PlayerAction pThrowerAction) {
        if (pThrowerAction == this.fThrowerAction) {
            return;
        }
        this.fThrowerAction = pThrowerAction;
        this.notifyObservers(ModelChangeId.GAME_SET_THROWER_ACTION, null, this.fThrowerAction);
    }

    public PlayerAction getThrowerAction() {
        return this.fThrowerAction;
    }

    public void setWaitingForOpponent(boolean pWaitingForOpponent) {
        if (pWaitingForOpponent == this.fWaitingForOpponent) {
            return;
        }
        this.fWaitingForOpponent = pWaitingForOpponent;
        this.notifyObservers(ModelChangeId.GAME_SET_WAITING_FOR_OPPONENT, null, this.fWaitingForOpponent);
    }

    public boolean isWaitingForOpponent() {
        return this.fWaitingForOpponent;
    }

    public void setDialogParameter(IDialogParameter pDialogParameter) {
        if (pDialogParameter == null && this.fDialogParameter == null) {
            return;
        }
        this.fDialogParameter = pDialogParameter;
        this.notifyObservers(ModelChangeId.GAME_SET_DIALOG_PARAMETER, null, this.fDialogParameter);
    }

    public IDialogParameter getDialogParameter() {
        return this.fDialogParameter;
    }

    public Date getFinished() {
        return this.fFinished;
    }

    public void setFinished(Date pFinished) {
        if (DateTool.isEqual(pFinished, this.fFinished)) {
            return;
        }
        this.fFinished = pFinished;
        this.notifyObservers(ModelChangeId.GAME_SET_FINISHED, null, this.fFinished);
    }

    public long getGameTime() {
        return this.fGameTime;
    }

    public void setGameTime(long pGameTime) {
        this.fGameTime = pGameTime;
    }

    public long getTurnTime() {
        return this.fTurnTime;
    }

    public void setTurnTime(long pTurnTime) {
        this.fTurnTime = pTurnTime;
    }

    public boolean isTurnTimeEnabled() {
        return this.getFinished() == null && (TurnMode.REGULAR == this.getTurnMode() || TurnMode.BLITZ == this.getTurnMode());
    }

    public boolean isTimeoutPossible() {
        return this.fTimeoutPossible;
    }

    public void setTimeoutPossible(boolean pTimeout) {
        if (pTimeout == this.fTimeoutPossible) {
            return;
        }
        this.fTimeoutPossible = pTimeout;
        this.notifyObservers(ModelChangeId.GAME_SET_TIMEOUT_POSSIBLE, null, this.fTimeoutPossible);
    }

    public void setTimeoutEnforced(boolean pTimeoutEnforced) {
        if (pTimeoutEnforced == this.fTimeoutEnforced) {
            return;
        }
        this.fTimeoutEnforced = pTimeoutEnforced;
        this.notifyObservers(ModelChangeId.GAME_SET_TIMEOUT_ENFORCED, null, this.fTimeoutEnforced);
    }

    public boolean isTimeoutEnforced() {
        return this.fTimeoutEnforced;
    }

    public void setConcessionPossible(boolean pConcessionPossible) {
        if (pConcessionPossible == this.fConcessionPossible) {
            return;
        }
        this.fConcessionPossible = pConcessionPossible;
        this.notifyObservers(ModelChangeId.GAME_SET_CONCESSION_POSSIBLE, null, this.fConcessionPossible);
    }

    public boolean isConcessionPossible() {
        return this.fConcessionPossible;
    }

    public void setTesting(boolean pTesting) {
        if (pTesting == this.fTesting) {
            return;
        }
        this.fTesting = pTesting;
        this.notifyObservers(ModelChangeId.GAME_SET_TESTING, null, this.fTesting);
    }

    public boolean isTesting() {
        return this.fTesting;
    }

    public GameOptions getOptions() {
        return this.fOptions;
    }

    public Team findTeam(Player pPlayer) {
        if (this.getTeamHome().hasPlayer(pPlayer)) {
            return this.getTeamHome();
        }
        if (this.getTeamAway().hasPlayer(pPlayer)) {
            return this.getTeamAway();
        }
        return null;
    }

    private void notifyObservers(ModelChangeId pChangeId, String pKey, Object pValue) {
        if (pChangeId == null) {
            return;
        }
        ModelChange modelChange = new ModelChange(pChangeId, pKey, pValue);
        this.notifyObservers(modelChange);
    }

    public Game transform() {
        Game transformedGame = new Game();
        transformedGame.setId(this.getId());
        transformedGame.setTurnMode(this.getTurnMode());
        transformedGame.setHalf(this.getHalf());
        transformedGame.fActingPlayer = this.getActingPlayer();
        transformedGame.setScheduled(this.getScheduled());
        transformedGame.setStarted(this.getStarted());
        transformedGame.setFinished(this.getFinished());
        transformedGame.setSetupOffense(this.isSetupOffense());
        transformedGame.setWaitingForOpponent(this.isWaitingForOpponent());
        transformedGame.setDialogParameter(this.getDialogParameter());
        transformedGame.setDefenderId(this.getDefenderId());
        transformedGame.setDefenderAction(this.getDefenderAction());
        transformedGame.setTurnTime(this.getTurnTime());
        transformedGame.setGameTime(this.getGameTime());
        transformedGame.setTimeoutPossible(this.isTimeoutPossible());
        transformedGame.setTimeoutEnforced(this.isTimeoutEnforced());
        transformedGame.setTesting(this.isTesting());
        transformedGame.setThrowerId(this.getThrowerId());
        transformedGame.setThrowerAction(this.getThrowerAction());
        transformedGame.getOptions().init(this.getOptions());
        transformedGame.setHomePlaying(!this.isHomePlaying());
        transformedGame.setHomeFirstOffense(!this.isHomeFirstOffense());
        transformedGame.fFieldModel = this.getFieldModel().transform();
        transformedGame.setTeamHome(this.getTeamAway());
        transformedGame.getTurnDataHome().init(this.getTurnDataAway());
        transformedGame.setTeamAway(this.getTeamHome());
        transformedGame.getTurnDataAway().init(this.getTurnDataHome());
        if (this.getPassCoordinate() != null) {
            transformedGame.setPassCoordinate(this.getPassCoordinate().transform());
        }
        transformedGame.fGameResult = this.getGameResult().transform();
        return transformedGame;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.GAME_ID.addTo(jsonObject, this.fId);
        IJsonOption.SCHEDULED.addTo(jsonObject, this.fScheduled);
        IJsonOption.STARTED.addTo(jsonObject, this.fStarted);
        IJsonOption.FINISHED.addTo(jsonObject, this.fFinished);
        IJsonOption.HOME_PLAYING.addTo(jsonObject, this.fHomePlaying);
        IJsonOption.HALF.addTo(jsonObject, this.fHalf);
        IJsonOption.HOME_FIRST_OFFENSE.addTo(jsonObject, this.fHomeFirstOffense);
        IJsonOption.SETUP_OFFENSE.addTo(jsonObject, this.fSetupOffense);
        IJsonOption.WAITING_FOR_OPPONENT.addTo(jsonObject, this.fWaitingForOpponent);
        IJsonOption.TURN_TIME.addTo(jsonObject, this.fTurnTime);
        IJsonOption.GAME_TIME.addTo(jsonObject, this.fGameTime);
        IJsonOption.TIMEOUT_POSSIBLE.addTo(jsonObject, this.fTimeoutPossible);
        IJsonOption.TIMEOUT_ENFORCED.addTo(jsonObject, this.fTimeoutEnforced);
        IJsonOption.CONCESSION_POSSIBLE.addTo(jsonObject, this.fConcessionPossible);
        IJsonOption.TESTING.addTo(jsonObject, this.fTesting);
        IJsonOption.TURN_MODE.addTo(jsonObject, this.fTurnMode);
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        IJsonOption.DEFENDER_ACTION.addTo(jsonObject, this.fDefenderAction);
        IJsonOption.PASS_COORDINATE.addTo(jsonObject, this.fPassCoordinate);
        IJsonOption.THROWER_ID.addTo(jsonObject, this.fThrowerId);
        IJsonOption.THROWER_ACTION.addTo(jsonObject, this.fThrowerAction);
        IJsonOption.TEAM_AWAY.addTo(jsonObject, this.fTeamAway.toJsonValue());
        IJsonOption.TURN_DATA_AWAY.addTo(jsonObject, this.fTurnDataAway.toJsonValue());
        IJsonOption.TEAM_HOME.addTo(jsonObject, this.fTeamHome.toJsonValue());
        IJsonOption.TURN_DATA_HOME.addTo(jsonObject, this.fTurnDataHome.toJsonValue());
        IJsonOption.FIELD_MODEL.addTo(jsonObject, this.fFieldModel.toJsonValue());
        IJsonOption.ACTING_PLAYER.addTo(jsonObject, this.fActingPlayer.toJsonValue());
        IJsonOption.GAME_RESULT.addTo(jsonObject, this.fGameResult.toJsonValue());
        IJsonOption.GAME_OPTIONS.addTo(jsonObject, this.fOptions.toJsonValue());
        if (this.fDialogParameter != null) {
            IJsonOption.DIALOG_PARAMETER.addTo(jsonObject, this.fDialogParameter.toJsonValue());
        }
        return jsonObject;
    }

    @Override
    public Game initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.GAME_ID.getFrom(jsonObject);
        this.fScheduled = IJsonOption.SCHEDULED.getFrom(jsonObject);
        this.fStarted = IJsonOption.STARTED.getFrom(jsonObject);
        this.fFinished = IJsonOption.FINISHED.getFrom(jsonObject);
        this.fHomePlaying = IJsonOption.HOME_PLAYING.getFrom(jsonObject);
        this.fHalf = IJsonOption.HALF.getFrom(jsonObject);
        this.fHomeFirstOffense = IJsonOption.HOME_FIRST_OFFENSE.getFrom(jsonObject);
        this.fSetupOffense = IJsonOption.SETUP_OFFENSE.getFrom(jsonObject);
        this.fWaitingForOpponent = IJsonOption.WAITING_FOR_OPPONENT.getFrom(jsonObject);
        this.fTurnTime = IJsonOption.TURN_TIME.getFrom(jsonObject);
        this.fGameTime = IJsonOption.GAME_TIME.getFrom(jsonObject);
        this.fTimeoutPossible = IJsonOption.TIMEOUT_POSSIBLE.getFrom(jsonObject);
        this.fTimeoutEnforced = IJsonOption.TIMEOUT_ENFORCED.getFrom(jsonObject);
        this.fConcessionPossible = IJsonOption.CONCESSION_POSSIBLE.getFrom(jsonObject);
        this.fTesting = IJsonOption.TESTING.getFrom(jsonObject);
        this.fTurnMode = (TurnMode)IJsonOption.TURN_MODE.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fDefenderAction = (PlayerAction)IJsonOption.DEFENDER_ACTION.getFrom(jsonObject);
        this.fPassCoordinate = IJsonOption.PASS_COORDINATE.getFrom(jsonObject);
        this.fThrowerId = IJsonOption.THROWER_ID.getFrom(jsonObject);
        this.fThrowerAction = (PlayerAction)IJsonOption.THROWER_ACTION.getFrom(jsonObject);
        this.fTeamAway.initFrom(IJsonOption.TEAM_AWAY.getFrom(jsonObject));
        this.fTurnDataAway.initFrom(IJsonOption.TURN_DATA_AWAY.getFrom(jsonObject));
        this.fTeamHome.initFrom(IJsonOption.TEAM_HOME.getFrom(jsonObject));
        this.fTurnDataHome.initFrom(IJsonOption.TURN_DATA_HOME.getFrom(jsonObject));
        this.fFieldModel.initFrom(IJsonOption.FIELD_MODEL.getFrom(jsonObject));
        this.fActingPlayer.initFrom(IJsonOption.ACTING_PLAYER.getFrom(jsonObject));
        this.fGameResult.initFrom(IJsonOption.GAME_RESULT.getFrom(jsonObject));
        this.fOptions.initFrom(IJsonOption.GAME_OPTIONS.getFrom(jsonObject));
        this.fDialogParameter = null;
        JsonObject dialogParameterObject = IJsonOption.DIALOG_PARAMETER.getFrom(jsonObject);
        if (dialogParameterObject != null) {
            this.fDialogParameter = new DialogParameterFactory().forJsonValue(dialogParameterObject);
        }
        return this;
    }
}

