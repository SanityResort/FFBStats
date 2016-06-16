/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

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

