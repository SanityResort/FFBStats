/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonLongOption
extends JsonAbstractOption {
    public JsonLongOption(String pKey) {
        super(pKey);
    }

    public long getFrom(JsonObject pJsonObject) {
        JsonValue value = this.getValueFrom(pJsonObject);
        if (value == null || value.isNull()) {
            return 0;
        }
        return value.asLong();
    }

    public void addTo(JsonObject pJsonObject, long pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }
}

