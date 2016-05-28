/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandInterceptorChoice
extends NetCommand {
    private String fInterceptorId;

    public ClientCommandInterceptorChoice() {
    }

    public ClientCommandInterceptorChoice(String pInterceptorId) {
        this.fInterceptorId = pInterceptorId;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_INTERCEPTOR_CHOICE;
    }

    public String getInterceptorId() {
        return this.fInterceptorId;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.INTERCEPTOR_ID.addTo(jsonObject, this.fInterceptorId);
        return jsonObject;
    }

    @Override
    public ClientCommandInterceptorChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fInterceptorId = IJsonOption.INTERCEPTOR_ID.getFrom(jsonObject);
        return this;
    }
}

