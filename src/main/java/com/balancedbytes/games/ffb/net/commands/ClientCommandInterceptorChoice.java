/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandInterceptorChoice
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.INTERCEPTOR_ID.addTo(jsonObject, this.fInterceptorId);
        return jsonObject;
    }

    @Override
    public ClientCommandInterceptorChoice initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fInterceptorId = IJsonOption.INTERCEPTOR_ID.getFrom(jsonObject);
        return this;
    }
}

