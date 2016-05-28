/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandBlockChoice
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.DICE_INDEX.addTo(jsonObject, this.fDiceIndex);
        return jsonObject;
    }

    @Override
    public ClientCommandBlockChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fDiceIndex = IJsonOption.DICE_INDEX.getFrom(jsonObject);
        return this;
    }
}

