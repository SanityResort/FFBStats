/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class KnockoutRecovery
implements IJsonSerializable {
    private String fPlayerId;
    private boolean fRecovering;
    private int fRoll;
    private int fBloodweiserBabes;

    public KnockoutRecovery() {
    }

    public KnockoutRecovery(String pPlayerId, boolean pRecovering, int pRoll, int pBloodweiserBabes) {
        this.fPlayerId = pPlayerId;
        this.fRecovering = pRecovering;
        this.fRoll = pRoll;
        this.fBloodweiserBabes = pBloodweiserBabes;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isRecovering() {
        return this.fRecovering;
    }

    public int getRoll() {
        return this.fRoll;
    }

    public int getBloodweiserBabes() {
        return this.fBloodweiserBabes;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.RECOVERING.addTo(jsonObject, this.fRecovering);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        IJsonOption.BLOODWEISER_BABES.addTo(jsonObject, this.fBloodweiserBabes);
        return jsonObject;
    }

    @Override
    public KnockoutRecovery initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRecovering = IJsonOption.RECOVERING.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fBloodweiserBabes = IJsonOption.BLOODWEISER_BABES.getFrom(jsonObject);
        return this;
    }
}

