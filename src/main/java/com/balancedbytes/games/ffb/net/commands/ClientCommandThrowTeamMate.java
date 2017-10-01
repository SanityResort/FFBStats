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

public class ClientCommandThrowTeamMate
extends ClientCommand
implements ICommandWithActingPlayer {
    private FieldCoordinate fTargetCoordinate;
    private String fThrownPlayerId;
    private String fActingPlayerId;

    public ClientCommandThrowTeamMate() {
    }

    public ClientCommandThrowTeamMate(String pActingPlayerId, String pThrownPlayerId) {
        this.fActingPlayerId = pActingPlayerId;
        this.fThrownPlayerId = pThrownPlayerId;
        this.fTargetCoordinate = null;
    }

    public ClientCommandThrowTeamMate(String pActingPlayerId, FieldCoordinate pTargetCoordinate) {
        this.fActingPlayerId = pActingPlayerId;
        this.fTargetCoordinate = pTargetCoordinate;
        this.fThrownPlayerId = null;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_THROW_TEAM_MATE;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public String getThrownPlayerId() {
        return this.fThrownPlayerId;
    }

    public FieldCoordinate getTargetCoordinate() {
        return this.fTargetCoordinate;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.THROWN_PLAYER_ID.addTo(jsonObject, this.fThrownPlayerId);
        IJsonOption.TARGET_COORDINATE.addTo(jsonObject, this.fTargetCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandThrowTeamMate initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fThrownPlayerId = IJsonOption.THROWN_PLAYER_ID.getFrom(jsonObject);
        this.fTargetCoordinate = IJsonOption.TARGET_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

