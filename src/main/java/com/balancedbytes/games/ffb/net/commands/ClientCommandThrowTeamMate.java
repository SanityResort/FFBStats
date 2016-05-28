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
import com.balancedbytes.games.ffb.net.commands.ICommandWithActingPlayer;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandThrowTeamMate
extends NetCommand
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.THROWN_PLAYER_ID.addTo(jsonObject, this.fThrownPlayerId);
        IJsonOption.TARGET_COORDINATE.addTo(jsonObject, this.fTargetCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandThrowTeamMate initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fThrownPlayerId = IJsonOption.THROWN_PLAYER_ID.getFrom(jsonObject);
        this.fTargetCoordinate = IJsonOption.TARGET_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

