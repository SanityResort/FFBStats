/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Date;

public class Game
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
    private Team fTeamHome;
    private Team fTeamAway;
    private transient long fGameTime;

    public Game() {
        this.fHomePlaying = true;
        this.setTeamHome(new Team());
        this.setTeamAway(new Team());
    }

    public void setId(long pId) {
        if (pId == this.fId) {
            return;
        }
        this.fId = pId;
    }

    public long getId() {
        return this.fId;
    }

    public int getHalf() {
        return this.fHalf;
    }


    public TurnMode getTurnMode() {
        return this.fTurnMode;
    }



    public Team getTeamHome() {
        return this.fTeamHome;
    }

    public Team getTeamAway() {
        return this.fTeamAway;
    }


    public boolean isHomePlaying() {
        return this.fHomePlaying;
    }


    public FieldCoordinate getPassCoordinate() {
        return this.fPassCoordinate;
    }


    public boolean isHomeFirstOffense() {
        return this.fHomeFirstOffense;
    }


    public boolean isSetupOffense() {
        return this.fSetupOffense;
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
    }

    public void setTeamAway(Team pTeam) {
        this.fTeamAway = pTeam;
    }

    public Date getStarted() {
        return this.fStarted;
    }


    public String getDefenderId() {
        return this.fDefenderId;
    }


    public String getThrowerId() {
        return this.fThrowerId;
    }



    public PlayerAction getThrowerAction() {
        return this.fThrowerAction;
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

    public Game transform() {
        Game transformedGame = new Game();
        transformedGame.setId(this.getId());
        transformedGame.setTurnTime(this.getTurnTime());
        transformedGame.setGameTime(this.getGameTime());
        transformedGame.setTeamHome(this.getTeamAway());
        transformedGame.setTeamAway(this.getTeamHome());
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
        IJsonOption.TEAM_HOME.addTo(jsonObject, this.fTeamHome.toJsonValue());

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
        this.fTurnMode = (TurnMode) IJsonOption.TURN_MODE.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fDefenderAction = (PlayerAction) IJsonOption.DEFENDER_ACTION.getFrom(jsonObject);
        this.fPassCoordinate = IJsonOption.PASS_COORDINATE.getFrom(jsonObject);
        this.fThrowerId = IJsonOption.THROWER_ID.getFrom(jsonObject);
        this.fThrowerAction = (PlayerAction) IJsonOption.THROWER_ACTION.getFrom(jsonObject);
        this.fTeamAway.initFrom(IJsonOption.TEAM_AWAY.getFrom(jsonObject));
        this.fTeamHome.initFrom(IJsonOption.TEAM_HOME.getFrom(jsonObject));

        return this;
    }
}

