/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandJoin
extends ClientCommand {
    private String fCoach;
    private String fPassword;
    private long fGameId;
    private String fGameName;
    private ClientMode fClientMode;
    private String fTeamId;
    private String fTeamName;

    public ClientCommandJoin() {
    }

    public ClientCommandJoin(ClientMode pClientMode) {
        this.fClientMode = pClientMode;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_JOIN;
    }

    public ClientMode getClientMode() {
        return this.fClientMode;
    }

    public void setClientMode(ClientMode pClientMode) {
        this.fClientMode = pClientMode;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public void setCoach(String pCoach) {
        this.fCoach = pCoach;
    }

    public String getPassword() {
        return this.fPassword;
    }

    public void setPassword(String pPassword) {
        this.fPassword = pPassword;
    }

    public long getGameId() {
        return this.fGameId;
    }

    public void setGameId(long pGameId) {
        this.fGameId = pGameId;
    }

    public String getGameName() {
        return this.fGameName;
    }

    public void setGameName(String pGameName) {
        this.fGameName = pGameName;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public void setTeamId(String pTeamId) {
        this.fTeamId = pTeamId;
    }

    public String getTeamName() {
        return this.fTeamName;
    }

    public void setTeamName(String pTeamName) {
        this.fTeamName = pTeamName;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.CLIENT_MODE.addTo(jsonObject, this.fClientMode);
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        IJsonOption.PASSWORD.addTo(jsonObject, this.fPassword);
        IJsonOption.GAME_ID.addTo(jsonObject, this.fGameId);
        IJsonOption.GAME_NAME.addTo(jsonObject, this.fGameName);
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.TEAM_NAME.addTo(jsonObject, this.fTeamName);
        return jsonObject;
    }

    @Override
    public ClientCommandJoin initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fClientMode = (ClientMode)IJsonOption.CLIENT_MODE.getFrom(jsonObject);
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.fPassword = IJsonOption.PASSWORD.getFrom(jsonObject);
        this.fGameId = IJsonOption.GAME_ID.getFrom(jsonObject);
        this.fGameName = IJsonOption.GAME_NAME.getFrom(jsonObject);
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fTeamName = IJsonOption.TEAM_NAME.getFrom(jsonObject);
        return this;
    }
}

