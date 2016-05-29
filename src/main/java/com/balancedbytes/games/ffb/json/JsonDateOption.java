/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.eclipsesource.json.JsonObject;

import java.util.Date;

public class JsonDateOption
extends JsonAbstractOption {
    public JsonDateOption(String pKey) {
        super(pKey);
    }

    public Date getFrom(JsonObject pJsonObject) {
        return UtilJson.toDate(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, Date pValue) {
        this.addValueTo(pJsonObject, UtilJson.toJsonValue(pValue));
    }
}

