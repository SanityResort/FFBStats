/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonFieldCoordinateOption
extends JsonAbstractOption {
    public JsonFieldCoordinateOption(String pKey) {
        super(pKey);
    }

    public FieldCoordinate getFrom(JsonObject pJsonObject) {
        return UtilJson.toFieldCoordinate(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, FieldCoordinate pValue) {
        this.addValueTo(pJsonObject, UtilJson.toJsonValue(pValue));
    }
}

