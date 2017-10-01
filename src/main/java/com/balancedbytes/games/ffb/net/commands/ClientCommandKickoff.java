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

public class ClientCommandKickoff
extends ClientCommand {
    private FieldCoordinate fBallCoordinate;

    public ClientCommandKickoff() {
    }

    public ClientCommandKickoff(FieldCoordinate pBallCoordinate) {
        this.fBallCoordinate = pBallCoordinate;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_KICKOFF;
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
    public ClientCommandKickoff initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fBallCoordinate = IJsonOption.BALL_COORDINATE.getFrom(jsonObject);
        return this;
    }
}

