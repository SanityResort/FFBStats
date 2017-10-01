/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.Pushback;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandPushback
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.PUSHBACK.addTo(jsonObject, this.fPushback.toJsonValue());
        return jsonObject;
    }

    @Override
    public ClientCommandPushback initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fPushback = new Pushback();
        this.fPushback.initFrom(IJsonOption.PUSHBACK.getFrom(jsonObject));
        return this;
    }
}

