/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class HeatExhaustion
implements IJsonSerializable {
    private String fPlayerId;
    private boolean fExhausted;
    private int fRoll;

    public HeatExhaustion() {
    }

    public HeatExhaustion(String pPlayerId, boolean pExhausted, int pRoll) {
        this.fPlayerId = pPlayerId;
        this.fExhausted = pExhausted;
        this.fRoll = pRoll;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isExhausted() {
        return this.fExhausted;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.EXHAUSTED.addTo(jsonObject, this.fExhausted);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        return jsonObject;
    }

    @Override
    public HeatExhaustion initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fExhausted = IJsonOption.EXHAUSTED.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

