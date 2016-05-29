/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.Collection;

public class JsonFieldCoordinateArrayOption
extends JsonAbstractOption {
    public JsonFieldCoordinateArrayOption(String pKey) {
        super(pKey);
    }

    public FieldCoordinate[] getFrom(JsonObject pJsonObject) {
        return this.asFieldCoordinates(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, FieldCoordinate[] pValues) {
        this.addValueTo(pJsonObject, this.asJsonValue(pValues));
    }

    public void addTo(JsonObject pJsonObject, Collection<FieldCoordinate> pValues) {
        FieldCoordinate[] fieldCoordinateArray = null;
        if (pValues != null) {
            fieldCoordinateArray = pValues.toArray(new FieldCoordinate[pValues.size()]);
        }
        this.addTo(pJsonObject, fieldCoordinateArray);
    }

    private FieldCoordinate[] asFieldCoordinates(JsonValue pJsonValue) {
        JsonArray jsonArray;
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        JsonArray jsonArray2 = jsonArray = pJsonValue.isArray() ? pJsonValue.asArray() : null;
        if (jsonArray == null) {
            throw new IllegalArgumentException("JsonValue is not a valid FieldCoordinate array.");
        }
        FieldCoordinate[] fieldCoordinates = new FieldCoordinate[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); ++i) {
            fieldCoordinates[i] = UtilJson.toFieldCoordinate(jsonArray.get(i));
        }
        return fieldCoordinates;
    }

    private JsonValue asJsonValue(FieldCoordinate[] pFieldCoordinates) {
        if (pFieldCoordinates == null) {
            return JsonValue.NULL;
        }
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < pFieldCoordinates.length; ++i) {
            jsonArray.add(UtilJson.toJsonValue(pFieldCoordinates[i]));
        }
        return jsonArray;
    }
}

