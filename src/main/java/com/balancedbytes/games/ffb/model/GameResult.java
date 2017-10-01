/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class GameResult
implements IJsonSerializable {
    private TeamResult fTeamResultHome;
    private TeamResult fTeamResultAway;
    private transient Game fGame;

    public GameResult(Game pGame) {
        this(pGame, null, null);
    }

    private GameResult(Game pGame, TeamResult pTeamResultHome, TeamResult pTeamResultAway) {
        this.fGame = pGame;
        this.fTeamResultHome = pTeamResultHome;
        if (this.fTeamResultHome == null) {
            this.fTeamResultHome = new TeamResult(this, true);
            this.fTeamResultHome.setTeam(this.fGame.getTeamHome());
        }
        this.fTeamResultAway = pTeamResultAway;
        if (this.fTeamResultAway == null) {
            this.fTeamResultAway = new TeamResult(this, false);
            this.fTeamResultAway.setTeam(this.fGame.getTeamAway());
        }
    }

    public Game getGame() {
        return this.fGame;
    }

    public TeamResult getTeamResultHome() {
        return this.fTeamResultHome;
    }

    public TeamResult getTeamResultAway() {
        return this.fTeamResultAway;
    }

    public int getScoreHome() {
        return this.getTeamResultHome().getScore();
    }

    public int getScoreAway() {
        return this.getTeamResultAway().getScore();
    }

    public GameResult transform() {
        TeamResult transformedTeamResultHome = new TeamResult(this, true);
        transformedTeamResultHome.setTeam(this.getTeamResultAway().getTeam());
        transformedTeamResultHome.init(this.getTeamResultAway());
        TeamResult transformedTeamResultAway = new TeamResult(this, false);
        transformedTeamResultAway.setTeam(this.getTeamResultHome().getTeam());
        transformedTeamResultAway.init(this.getTeamResultHome());
        return new GameResult(this.getGame(), transformedTeamResultHome, transformedTeamResultAway);
    }

    public PlayerResult getPlayerResult(Player pPlayer) {
        if (this.getGame().getTeamHome().hasPlayer(pPlayer)) {
            return this.getTeamResultHome().getPlayerResult(pPlayer);
        }
        return this.getTeamResultAway().getPlayerResult(pPlayer);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.TEAM_RESULT_HOME.addTo(jsonObject, this.fTeamResultHome.toJsonValue());
        IJsonOption.TEAM_RESULT_AWAY.addTo(jsonObject, this.fTeamResultAway.toJsonValue());
        return jsonObject;
    }

    @Override
    public GameResult initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fTeamResultHome.initFrom(IJsonOption.TEAM_RESULT_HOME.getFrom(jsonObject));
        this.fTeamResultAway.initFrom(IJsonOption.TEAM_RESULT_AWAY.getFrom(jsonObject));
        return this;
    }
}

