/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandReceiveChoice
extends NetCommand {
    private boolean fChoiceReceive;

    public ClientCommandReceiveChoice() {
    }

    public ClientCommandReceiveChoice(boolean pChoiceReceive) {
        this.fChoiceReceive = pChoiceReceive;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_RECEIVE_CHOICE;
    }

    public boolean isChoiceReceive() {
        return this.fChoiceReceive;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.CHOICE_RECEIVE.addTo(jsonObject, this.fChoiceReceive);
        return jsonObject;
    }

    @Override
    public ClientCommandReceiveChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fChoiceReceive = IJsonOption.CHOICE_RECEIVE.getFrom(jsonObject);
        return this;
    }
}

