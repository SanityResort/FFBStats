/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandTouchback
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.BALL_COORDINATE.addTo(jsonObject, this.fBallCoordinate);
        return jsonObject;
    }

    @Override
    public ClientCommandTouchback initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fBallCoordinate = IJsonOption.BALL_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

