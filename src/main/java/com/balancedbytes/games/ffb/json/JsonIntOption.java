/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonIntOption
extends JsonAbstractOption {
    public JsonIntOption(String pKey) {
        super(pKey);
    }

    public int getFrom(JsonObject pJsonObject) {
        JsonValue value = this.getValueFrom(pJsonObject);
        if (value == null || value.isNull()) {
            return 0;
        }
        return value.asInt();
    }

    public void addTo(JsonObject pJsonObject, int pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }
}

