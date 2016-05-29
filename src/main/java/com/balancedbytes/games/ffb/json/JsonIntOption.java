/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonIntOption
extends JsonAbstractOption {
    public JsonIntOption(String pKey) {
        super(pKey);
    }

    public int getFrom(JsonObject pJsonObject) {
        return this.getValueFrom(pJsonObject).asInt();
    }

    public void addTo(JsonObject pJsonObject, int pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }
}

