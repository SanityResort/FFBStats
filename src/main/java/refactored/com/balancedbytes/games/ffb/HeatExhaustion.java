/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.IJsonReadable;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class HeatExhaustion
implements IJsonReadable {
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
    public HeatExhaustion initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fExhausted = IJsonOption.EXHAUSTED.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

