/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandGameTime
extends ServerCommand {
    private long fGameTime;
    private long fTurnTime;

    public ServerCommandGameTime() {
    }

    public ServerCommandGameTime(long gameTime, long turnTime) {
        this.fGameTime = gameTime;
        this.fTurnTime = turnTime;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_GAME_TIME;
    }

    public void setGameTime(long gameTime) {
        this.fGameTime = gameTime;
    }

    public long getGameTime() {
        return this.fGameTime;
    }

    public void setTurnTime(long turnTime) {
        this.fTurnTime = turnTime;
    }

    public long getTurnTime() {
        return this.fTurnTime;
    }

    @Override
    public boolean isReplayable() {
        return false;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COMMAND_NR.addTo(jsonObject, this.getCommandNr());
        IJsonOption.GAME_TIME.addTo(jsonObject, this.fGameTime);
        IJsonOption.TURN_TIME.addTo(jsonObject, this.fTurnTime);
        return jsonObject;
    }

    @Override
    public ServerCommandGameTime initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fGameTime = IJsonOption.GAME_TIME.getFrom(jsonObject);
        this.fTurnTime = IJsonOption.TURN_TIME.getFrom(jsonObject);
        return this;
    }
}

