/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonEnumWithNameOption
extends JsonAbstractOption {
    private IEnumWithNameFactory fFactory;

    public JsonEnumWithNameOption(String pKey, IEnumWithNameFactory pFactory) {
        super(pKey);
        this.fFactory = pFactory;
        if (this.fFactory == null) {
            throw new IllegalArgumentException("Parameter factory must not be null.");
        }
    }

    public IEnumWithName getFrom(JsonObject pJsonObject) {
        return this.asEnumWithName(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, IEnumWithName pValue) {
        this.addValueTo(pJsonObject, this.asJsonValue(pValue));
    }

    private IEnumWithName asEnumWithName(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        return this.fFactory.forName(pJsonValue.asString());
    }

    private JsonValue asJsonValue(IEnumWithName pEnumWithName) {
        if (pEnumWithName == null) {
            return JsonValue.NULL;
        }
        return JsonValue.valueOf(pEnumWithName.getName());
    }
}

