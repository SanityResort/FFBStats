/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandSetMarker
extends ClientCommand {
    private String fPlayerId;
    private FieldCoordinate fCoordinate;
    private String fText;

    public ClientCommandSetMarker() {
    }

    public ClientCommandSetMarker(FieldCoordinate pCoordinate, String pText) {
        this.fCoordinate = pCoordinate;
        this.fText = pText;
    }

    public ClientCommandSetMarker(String pPlayerId, String pText) {
        this.fPlayerId = pPlayerId;
        this.fText = pText;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_SET_MARKER;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public String getText() {
        return this.fText;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.TEXT.addTo(jsonObject, this.fText);
        return jsonObject;
    }

    @Override
    public ClientCommandSetMarker initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fText = IJsonOption.TEXT.getFrom(jsonObject);
        return this;
    }
}

