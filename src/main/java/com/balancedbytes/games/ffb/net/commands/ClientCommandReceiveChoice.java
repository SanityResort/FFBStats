/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandReceiveChoice
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.CHOICE_RECEIVE.addTo(jsonObject, this.fChoiceReceive);
        return jsonObject;
    }

    @Override
    public ClientCommandReceiveChoice initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fChoiceReceive = IJsonOption.CHOICE_RECEIVE.getFrom(jsonObject);
        return this;
    }
}

