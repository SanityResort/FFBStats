/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandBlockChoice
extends ClientCommand {
    private int fDiceIndex;

    public ClientCommandBlockChoice() {
    }

    public ClientCommandBlockChoice(int pDiceIndex) {
        this.fDiceIndex = pDiceIndex;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_BLOCK_CHOICE;
    }

    public int getDiceIndex() {
        return this.fDiceIndex;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.DICE_INDEX.addTo(jsonObject, this.fDiceIndex);
        return jsonObject;
    }

    @Override
    public ClientCommandBlockChoice initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fDiceIndex = IJsonOption.DICE_INDEX.getFrom(jsonObject);
        return this;
    }
}

