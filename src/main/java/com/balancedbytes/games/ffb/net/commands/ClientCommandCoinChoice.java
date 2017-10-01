/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandCoinChoice
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.CHOICE_HEADS.addTo(jsonObject, this.fChoiceHeads);
        return jsonObject;
    }

    @Override
    public ClientCommandCoinChoice initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fChoiceHeads = IJsonOption.CHOICE_HEADS.getFrom(jsonObject);
        return this;
    }
}

