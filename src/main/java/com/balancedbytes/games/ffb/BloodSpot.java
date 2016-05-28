/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonPlayerStateOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class BloodSpot
implements IJsonSerializable {
    private PlayerState fInjury;
    private FieldCoordinate fCoordinate;
    private transient String fIconProperty;

    public BloodSpot() {
    }

    public BloodSpot(FieldCoordinate pCoordinate, PlayerState pInjury) {
        this.fInjury = pInjury;
        this.fCoordinate = pCoordinate;
    }

    public PlayerState getInjury() {
        return this.fInjury;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public void setIconProperty(String pIconProperty) {
        this.fIconProperty = pIconProperty;
    }

    public String getIconProperty() {
        return this.fIconProperty;
    }

    public BloodSpot transform() {
        BloodSpot transformedBloodspot = new BloodSpot(this.getCoordinate().transform(), this.getInjury());
        transformedBloodspot.setIconProperty(this.getIconProperty());
        return transformedBloodspot;
    }

    public static BloodSpot transform(BloodSpot pBloodspot) {
        return pBloodspot != null ? pBloodspot.transform() : null;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.INJURY.addTo(jsonObject, this.fInjury);
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        return jsonObject;
    }

    @Override
    public BloodSpot initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fInjury = IJsonOption.INJURY.getFrom(jsonObject);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        return this;
    }
}

