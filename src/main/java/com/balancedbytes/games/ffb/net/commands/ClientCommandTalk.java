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

public class ClientCommandTalk
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.TALK.addTo(jsonObject, this.fTalk);
        return jsonObject;
    }

    @Override
    public ClientCommandTalk initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fTalk = IJsonOption.TALK.getFrom(jsonObject);
        return this;
    }
}

