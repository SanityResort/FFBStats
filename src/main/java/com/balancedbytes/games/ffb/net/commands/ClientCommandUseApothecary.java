/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandUseApothecary
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.APOTHECARY_USED.addTo(jsonObject, this.fApothecaryUsed);
        return jsonObject;
    }

    @Override
    public ClientCommandUseApothecary initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fApothecaryUsed = IJsonOption.APOTHECARY_USED.getFrom(jsonObject);
        return this;
    }
}

