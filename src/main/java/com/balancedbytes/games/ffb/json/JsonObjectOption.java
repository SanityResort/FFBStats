/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonObjectOption
extends JsonAbstractOption {
    public JsonObjectOption(String pKey) {
        super(pKey);
    }

    public JsonObject getFrom(JsonObject pJsonObject) {
        JsonValue jsonValue = this.getValueFrom(pJsonObject);
        if (jsonValue == null || jsonValue.isNull()) {
            return null;
        }
        return jsonValue.asObject();
    }

    public void addTo(JsonObject pJsonObject, JsonObject pValue) {
        if (pValue == null) {
            this.addValueTo(pJsonObject, JsonValue.NULL);
        } else {
            this.addValueTo(pJsonObject, pValue);
        }
    }
}

