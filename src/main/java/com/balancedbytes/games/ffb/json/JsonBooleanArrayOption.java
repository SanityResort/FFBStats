/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Collection;

public class JsonBooleanArrayOption
extends JsonAbstractOption {
    public JsonBooleanArrayOption(String pKey) {
        super(pKey);
    }

    public boolean[] getFrom(JsonObject pJsonObject) {
        return this.toBooleanArray(this.getValueFrom(pJsonObject).asArray());
    }

    private boolean[] toBooleanArray(JsonArray pJsonArray) {
        if (pJsonArray == null) {
            return null;
        }
        boolean[] booleanArray = new boolean[pJsonArray.size()];
        for (int i = 0; i < booleanArray.length; ++i) {
            booleanArray[i] = pJsonArray.get(i).asBoolean();
        }
        return booleanArray;
    }

    private JsonArray toJsonArray(boolean[] pBooleanArray) {
        if (pBooleanArray == null) {
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < pBooleanArray.length; ++i) {
            jsonArray.add(pBooleanArray[i]);
        }
        return jsonArray;
    }

    public void addTo(JsonObject pJsonObject, boolean[] pValues) {
        this.addValueTo(pJsonObject, this.toJsonArray(pValues));
    }

    public void addTo(JsonObject pJsonObject, Collection<Boolean> pValues) {
        boolean[] booleanArray = null;
        if (pValues != null) {
            Boolean[] booleanObjectArray = pValues.toArray(new Boolean[pValues.size()]);
            booleanArray = new boolean[booleanObjectArray.length];
            for (int i = 0; i < booleanArray.length; ++i) {
                booleanArray[i] = booleanObjectArray[i];
            }
        }
        this.addTo(pJsonObject, booleanArray);
    }
}

