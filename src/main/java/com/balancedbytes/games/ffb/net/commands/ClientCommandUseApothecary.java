/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandUseApothecary
extends ClientCommand {
    private String fPlayerId;
    private boolean fApothecaryUsed;

    public ClientCommandUseApothecary() {
    }

    public ClientCommandUseApothecary(String pPlayerId, boolean pApothecaryUsed) {
        this.fPlayerId = pPlayerId;
        this.fApothecaryUsed = pApothecaryUsed;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_USE_APOTHECARY;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isApothecaryUsed() {
        return this.fApothecaryUsed;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.APOTHECARY_USED.addTo(jsonObject, this.fApothecaryUsed);
        return jsonObject;
    }

    @Override
    public ClientCommandUseApothecary initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fApothecaryUsed = IJsonOption.APOTHECARY_USED.getFrom(jsonObject);
        return this;
    }
}

