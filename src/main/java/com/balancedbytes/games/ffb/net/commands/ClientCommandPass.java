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

public class ClientCommandPass
extends ClientCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private FieldCoordinate fTargetCoordinate;

    public ClientCommandPass() {
    }

    public ClientCommandPass(String pActingPlayerId, FieldCoordinate pTargetCoordinate) {
        this.fActingPlayerId = pActingPlayerId;
        this.fTargetCoordinate = pTargetCoordinate;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_PASS;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public FieldCoordinate getTargetCoordinate() {
        return this.fTargetCoordinate;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.TARGET_COORDINATE.addTo(jsonObject, this.fTargetCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandPass initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fTargetCoordinate = IJsonOption.TARGET_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

