package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonReadable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Game
        implements IJsonReadable {
    private long fId;

    private Team fTeamHome;
    private Team fTeamAway;

    public Game() {
        this.setTeamHome(new Team());
        this.setTeamAway(new Team());
    }

    private void setId(long pId) {
        if (pId == this.fId) {
            return;
        }
        this.fId = pId;
    }

    public long getId() {
        return this.fId;
    }


    public Team getTeamHome() {
        return this.fTeamHome;
    }

    public Team getTeamAway() {
        return this.fTeamAway;
    }


    private void setTeamHome(Team pTeam) {
        this.fTeamHome = pTeam;
    }

    private void setTeamAway(Team pTeam) {
        this.fTeamAway = pTeam;
    }

    public Game transform() {
        Game transformedGame = new Game();
        transformedGame.setId(this.getId());
        transformedGame.setTeamHome(this.getTeamAway());
        transformedGame.setTeamAway(this.getTeamHome());
        return transformedGame;
    }


    @Override
    public Game initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.GAME_ID.getFrom(jsonObject);
        this.fTeamAway.initFrom(IJsonOption.TEAM_AWAY.getFrom(jsonObject));
        this.fTeamHome.initFrom(IJsonOption.TEAM_HOME.getFrom(jsonObject));
        return this;
    }
}

