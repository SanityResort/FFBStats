/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonBooleanOption
extends JsonAbstractOption {
    public JsonBooleanOption(String pKey) {
        super(pKey);
    }

    public Boolean getFrom(JsonObject pJsonObject) {
        if (this.isDefinedIn(pJsonObject)) {
            return this.getValueFrom(pJsonObject).asBoolean();
        }
        return null;
    }

    public void addTo(JsonObject pJsonObject, Boolean pValue) {
        if (pValue != null) {
            this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
        }
    }
}

