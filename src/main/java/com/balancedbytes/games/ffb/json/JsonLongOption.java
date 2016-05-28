/*
 * Decompiled with CFR 0_114.
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
        return this.getValueFrom(pJsonObject).asLong();
    }

    public void addTo(JsonObject pJsonObject, long pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }
}

