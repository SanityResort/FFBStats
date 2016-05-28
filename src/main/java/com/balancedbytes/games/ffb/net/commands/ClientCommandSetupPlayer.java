/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandSetupPlayer
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandSetupPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        return this;
    }
}

