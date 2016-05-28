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

public class FieldMarker
implements IJsonSerializable {
    private FieldCoordinate fCoordinate;
    private String fHomeText;
    private String fAwayText;

    public FieldMarker() {
    }

    public FieldMarker(FieldCoordinate pCoordinate) {
        this.fCoordinate = pCoordinate;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public void setHomeText(String pHomeText) {
        this.fHomeText = pHomeText;
    }

    public String getHomeText() {
        return this.fHomeText;
    }

    public void setAwayText(String pAwayText) {
        this.fAwayText = pAwayText;
    }

    public String getAwayText() {
        return this.fAwayText;
    }

    public int hashCode() {
        return this.getCoordinate().hashCode();
    }

    public boolean equals(Object pObj) {
        return pObj instanceof FieldMarker && this.getCoordinate().equals(((FieldMarker)pObj).getCoordinate());
    }

    public FieldMarker transform() {
        FieldMarker transformedMarker = new FieldMarker(this.getCoordinate().transform());
        transformedMarker.setAwayText(this.getHomeText());
        transformedMarker.setHomeText(this.getAwayText());
        return transformedMarker;
    }

    public static FieldMarker transform(FieldMarker pFieldMarker) {
        return pFieldMarker != null ? pFieldMarker.transform() : null;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        IJsonOption.HOME_TEXT.addTo(jsonObject, this.fHomeText);
        IJsonOption.AWAY_TEXT.addTo(jsonObject, this.fAwayText);
        return jsonObject;
    }

    @Override
    public FieldMarker initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        this.fHomeText = IJsonOption.HOME_TEXT.getFrom(jsonObject);
        this.fAwayText = IJsonOption.AWAY_TEXT.getFrom(jsonObject);
        return this;
    }
}

