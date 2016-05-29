/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.PlayerState;
import com.eclipsesource.json.JsonObject;

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

