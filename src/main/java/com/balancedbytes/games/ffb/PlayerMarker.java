/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class PlayerMarker
implements IJsonSerializable {
    private String fPlayerId;
    private String fHomeText;
    private String fAwayText;

    public PlayerMarker() {
    }

    public PlayerMarker(String pPlayerId) {
        this.fPlayerId = pPlayerId;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public void setHomeText(String pHomeText) {
        this.fHomeText = pHomeText;
    }

    public String getHomeText() {
        return this.fHomeText;
    }

    public void setAwayText(String pAwayText) {
        this.fAwayText = pAwayText;
    }

    public String getAwayText() {
        return this.fAwayText;
    }

    public int hashCode() {
        return this.getPlayerId().hashCode();
    }

    public boolean equals(Object pObj) {
        return pObj instanceof PlayerMarker && this.getPlayerId().equals(((PlayerMarker)pObj).getPlayerId());
    }

    public PlayerMarker transform() {
        PlayerMarker transformedMarker = new PlayerMarker(this.getPlayerId());
        transformedMarker.setAwayText(this.getHomeText());
        transformedMarker.setHomeText(this.getAwayText());
        return transformedMarker;
    }

    public static PlayerMarker transform(PlayerMarker pFieldMarker) {
        return pFieldMarker != null ? pFieldMarker.transform() : null;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.HOME_TEXT.addTo(jsonObject, this.fHomeText);
        IJsonOption.AWAY_TEXT.addTo(jsonObject, this.fAwayText);
        return jsonObject;
    }

    @Override
    public PlayerMarker initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fHomeText = IJsonOption.HOME_TEXT.getFrom(jsonObject);
        this.fAwayText = IJsonOption.AWAY_TEXT.getFrom(jsonObject);
        return this;
    }
}

