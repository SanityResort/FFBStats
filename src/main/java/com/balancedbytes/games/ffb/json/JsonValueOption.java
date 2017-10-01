/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonValueOption
extends JsonAbstractOption {
    public JsonValueOption(String pKey) {
        super(pKey);
    }

    public JsonValue getFrom(JsonObject pJsonObject) {
        return this.getValueFrom(pJsonObject);
    }

    public void addTo(JsonObject pJsonObject, JsonValue pValue) {
        this.addValueTo(pJsonObject, pValue);
    }
}

