/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandDebugClientState
extends NetCommand {
    private ClientStateId fClientStateId;

    public ClientCommandDebugClientState() {
    }

    public ClientCommandDebugClientState(ClientStateId pClientStateId) {
        this();
        this.fClientStateId = pClientStateId;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_DEBUG_CLIENT_STATE;
    }

    public ClientStateId getClientStateId() {
        return this.fClientStateId;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.CLIENT_STATE_ID.addTo(jsonObject, this.fClientStateId);
        return jsonObject;
    }

    @Override
    public ClientCommandDebugClientState initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fClientStateId = (ClientStateId)IJsonOption.CLIENT_STATE_ID.getFrom(jsonObject);
        return this;
    }
}

