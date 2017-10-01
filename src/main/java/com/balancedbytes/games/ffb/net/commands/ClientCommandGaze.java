/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandGaze
extends ClientCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private String fVictimId;

    public ClientCommandGaze() {
    }

    public ClientCommandGaze(String pActingPlayerId, String pCatcherId) {
        this.fActingPlayerId = pActingPlayerId;
        this.fVictimId = pCatcherId;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_GAZE;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public String getVictimId() {
        return this.fVictimId;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.VICTIM_ID.addTo(jsonObject, this.fVictimId);
        return jsonObject;
    }

    @Override
    public ClientCommandGaze initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fVictimId = IJsonOption.VICTIM_ID.getFrom(jsonObject);
        return this;
    }
}

