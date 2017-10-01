/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonEnumWithNameOption
extends JsonAbstractOption {
    private INamedObjectFactory fFactory;

    public JsonEnumWithNameOption(String pKey, INamedObjectFactory pFactory) {
        super(pKey);
        this.fFactory = pFactory;
        if (this.fFactory == null) {
            throw new IllegalArgumentException("Parameter factory must not be null.");
        }
    }

    public INamedObject getFrom(JsonObject pJsonObject) {
        return this.asEnumWithName(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, INamedObject pValue) {
        this.addValueTo(pJsonObject, this.asJsonValue(pValue));
    }

    private INamedObject asEnumWithName(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        return this.fFactory.forName(pJsonValue.asString());
    }

    private JsonValue asJsonValue(INamedObject pEnumWithName) {
        if (pEnumWithName == null) {
            return JsonValue.NULL;
        }
        return JsonValue.valueOf(pEnumWithName.getName());
    }
}

