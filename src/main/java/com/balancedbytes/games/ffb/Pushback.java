/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Pushback
implements IJsonSerializable {
    private String fPlayerId;
    private FieldCoordinate fCoordinate;

    public Pushback() {
    }

    public Pushback(String pPlayerId, FieldCoordinate pCoordinate) {
        this.fPlayerId = pPlayerId;
        this.fCoordinate = pCoordinate;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public Pushback transform() {
        return new Pushback(this.getPlayerId(), FieldCoordinate.transform(this.getCoordinate()));
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        return jsonObject;
    }

    @Override
    public Pushback initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        return this;
    }
}

