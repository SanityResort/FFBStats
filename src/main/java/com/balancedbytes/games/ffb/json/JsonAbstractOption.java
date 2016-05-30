package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public abstract class JsonAbstractOption {
    private String fKey;

    public JsonAbstractOption(String pKey) {
        if (pKey == null || pKey.length() == 0) {
            throw new IllegalArgumentException("Parameter key must not be null or empty.");
        }
        this.fKey = pKey;
    }

    private String getKey() {
        return this.fKey;
    }

    boolean isDefinedIn(JsonObject pJsonObject) {
        return this.getValueFrom(pJsonObject) != null;
    }

    protected JsonValue getValueFrom(JsonObject pJsonObject) {
        if (pJsonObject == null) {
            this.throwJsonObjectIsNullException();
        }
        return pJsonObject.get(this.getKey());
    }

    protected void addValueTo(JsonObject pJsonObject, JsonValue pValue) {
        if (pJsonObject == null) {
            this.throwJsonObjectIsNullException();
        }
        if (pValue != null) {
            pJsonObject.add(this.getKey(), pValue);
        } else {
            pJsonObject.add(this.getKey(), JsonValue.NULL);
        }
    }

    private void throwJsonObjectIsNullException() {
        throw new IllegalArgumentException("Parameter jsonObject must not be null.");
    }
}

