/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonStringOption
extends JsonAbstractOption {
    public JsonStringOption(String pKey) {
        super(pKey);
    }

    public String getFrom(JsonObject pJsonObject) {
        return this.asString(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, String pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }

    private String asString(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        return pJsonValue.asString();
    }
}

