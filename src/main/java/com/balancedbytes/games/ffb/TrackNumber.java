/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class TrackNumber
implements IJsonSerializable {
    private FieldCoordinate fCoordinate;
    private int fNumber;

    public TrackNumber() {
    }

    public TrackNumber(FieldCoordinate pCoordinate, int pNumber) {
        this.fCoordinate = pCoordinate;
        this.fNumber = pNumber;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public int getNumber() {
        return this.fNumber;
    }

    public int hashCode() {
        return this.getCoordinate().hashCode();
    }

    public boolean equals(Object pObj) {
        return pObj instanceof TrackNumber && this.getCoordinate().equals(((TrackNumber)pObj).getCoordinate());
    }

    public TrackNumber transform() {
        return new TrackNumber(this.getCoordinate().transform(), this.getNumber());
    }

    public static TrackNumber transform(TrackNumber pTrackNumber) {
        return pTrackNumber != null ? pTrackNumber.transform() : null;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NUMBER.addTo(jsonObject, this.fNumber);
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        return jsonObject;
    }

    @Override
    public TrackNumber initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fNumber = IJsonOption.NUMBER.getFrom(jsonObject);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        return this;
    }
}

