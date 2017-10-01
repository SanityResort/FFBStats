/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
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

