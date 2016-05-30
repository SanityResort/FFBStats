package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.Collection;

public class JsonStringArrayOption extends JsonAbstractOption {
    JsonStringArrayOption(String pKey) {
        super(pKey);
    }

    public String[] getFrom(JsonObject pJsonObject) {
        JsonValue value = this.getValueFrom(pJsonObject);
        if (value != null && !value.isNull()) {
            return this.toStringArray(value.asArray());
        }
        return null;
    }

    private String[] toStringArray(JsonArray pJsonArray) {
        if (pJsonArray == null) {
            return null;
        }
        String[] stringArray = new String[pJsonArray.size()];
        for (int i = 0; i < stringArray.length; ++i) {
            JsonValue jsonValue = pJsonArray.get(i);
            if (jsonValue == null || jsonValue.isNull()) continue;
            stringArray[i] = jsonValue.asString();
        }
        return stringArray;
    }

    private JsonArray toJsonArray(String[] pStringArray) {
        if (pStringArray == null) {
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for (String aPStringArray : pStringArray) {
            jsonArray.add(aPStringArray);
        }
        return jsonArray;
    }

    public void addTo(JsonObject pJsonObject, String[] pValues) {
        this.addValueTo(pJsonObject, this.toJsonArray(pValues));
    }

    public void addTo(JsonObject pJsonObject, Collection<String> pValues) {
        String[] stringArray = null;
        if (pValues != null) {
            stringArray = pValues.toArray(new String[pValues.size()]);
        }
        this.addTo(pJsonObject, stringArray);
    }
}

