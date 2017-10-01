/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.json.JsonAbstractOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonPlayerStateOption
extends JsonAbstractOption {
    public JsonPlayerStateOption(String pKey) {
        super(pKey);
    }

    public PlayerState getFrom(JsonObject pJsonObject) {
        return UtilJson.toPlayerState(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, PlayerState pValue) {
        this.addValueTo(pJsonObject, UtilJson.toJsonValue(pValue));
    }
}

