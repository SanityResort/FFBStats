/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandSetupPlayer
extends ClientCommand {
    private String fPlayerId;
    private FieldCoordinate fCoordinate;

    public ClientCommandSetupPlayer() {
    }

    public ClientCommandSetupPlayer(String pPlayerId, FieldCoordinate pCoordinate) {
        this.fPlayerId = pPlayerId;
        this.fCoordinate = pCoordinate;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_SETUP_PLAYER;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandSetupPlayer initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        return this;
    }
}

