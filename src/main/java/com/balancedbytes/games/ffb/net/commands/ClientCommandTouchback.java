/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandTouchback
extends NetCommand {
    private FieldCoordinate fBallCoordinate;

    public ClientCommandTouchback() {
    }

    public ClientCommandTouchback(FieldCoordinate pBallCoordinate) {
        this.fBallCoordinate = pBallCoordinate;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_TOUCHBACK;
    }

    public FieldCoordinate getBallCoordinate() {
        return this.fBallCoordinate;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.BALL_COORDINATE.addTo(jsonObject, this.fBallCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandTouchback initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fBallCoordinate = IJsonOption.BALL_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

