/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandTalk
extends ClientCommand {
    private String fTalk;

    public ClientCommandTalk() {
    }

    public ClientCommandTalk(String pTalk) {
        this.fTalk = pTalk;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_TALK;
    }

    public String getTalk() {
        return this.fTalk;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.TALK.addTo(jsonObject, this.fTalk);
        return jsonObject;
    }

    @Override
    public ClientCommandTalk initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fTalk = IJsonOption.TALK.getFrom(jsonObject);
        return this;
    }
}

