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

public class ClientCommandCoinChoice
extends NetCommand {
    private boolean fChoiceHeads;

    public ClientCommandCoinChoice() {
    }

    public ClientCommandCoinChoice(boolean pChoiceHeads) {
        this.fChoiceHeads = pChoiceHeads;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_COIN_CHOICE;
    }

    public boolean isChoiceHeads() {
        return this.fChoiceHeads;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.CHOICE_HEADS.addTo(jsonObject, this.fChoiceHeads);
        return jsonObject;
    }

    @Override
    public ClientCommandCoinChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fChoiceHeads = IJsonOption.CHOICE_HEADS.getFrom(jsonObject);
        return this;
    }
}

