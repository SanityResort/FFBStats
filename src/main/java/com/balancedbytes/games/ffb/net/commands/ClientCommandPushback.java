/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.Pushback;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandPushback
extends NetCommand {
    private Pushback fPushback;

    public ClientCommandPushback() {
    }

    public ClientCommandPushback(Pushback pPushback) {
        if (pPushback == null) {
            throw new IllegalArgumentException("Parameter pushback must not be null.");
        }
        this.fPushback = pPushback;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_PUSHBACK;
    }

    public Pushback getPushback() {
        return this.fPushback;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.PUSHBACK.addTo(jsonObject, this.fPushback.toJsonValue());
        return jsonObject;
    }

    @Override
    public ClientCommandPushback initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fPushback = new Pushback();
        this.fPushback.initFrom(IJsonOption.PUSHBACK.getFrom(jsonObject));
        return this;
    }
}

