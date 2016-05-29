/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.Collection;

public class JsonIntArrayOption
extends JsonAbstractOption {
    public JsonIntArrayOption(String pKey) {
        super(pKey);
    }

    public int[] getFrom(JsonObject pJsonObject) {
        JsonValue value = this.getValueFrom(pJsonObject);
        if (value != null && !value.isNull()) {
            return this.toIntArray(value.asArray());
        }
        return null;
    }

    private int[] toIntArray(JsonArray pJsonArray) {
        if (pJsonArray == null) {
            return null;
        }
        int[] intArray = new int[pJsonArray.size()];
        for (int i = 0; i < intArray.length; ++i) {
            intArray[i] = pJsonArray.get(i).asInt();
        }
        return intArray;
    }

    private JsonArray toJsonArray(int[] pIntArray) {
        if (pIntArray == null) {
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < pIntArray.length; ++i) {
            jsonArray.add(pIntArray[i]);
        }
        return jsonArray;
    }

    public void addTo(JsonObject pJsonObject, int[] pValues) {
        this.addValueTo(pJsonObject, this.toJsonArray(pValues));
    }

    public void addTo(JsonObject pJsonObject, Collection<Integer> pValues) {
        int[] intArray = null;
        if (pValues != null) {
            Integer[] integerArray = pValues.toArray(new Integer[pValues.size()]);
            intArray = new int[integerArray.length];
            for (int i = 0; i < intArray.length; ++i) {
                intArray[i] = integerArray[i];
            }
        }
        this.addTo(pJsonObject, intArray);
    }
}

