/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonArrayOption
extends JsonAbstractOption {
    public JsonArrayOption(String pKey) {
        super(pKey);
    }

    public JsonArray getFrom(JsonObject pJsonObject) {
        JsonValue jsonValue = this.getValueFrom(pJsonObject);
        if (jsonValue == null || jsonValue.isNull()) {
            return null;
        }
        return jsonValue.asArray();
    }

    public void addTo(JsonObject pJsonObject, JsonArray pValue) {
        this.addValueTo(pJsonObject, pValue);
    }
}

