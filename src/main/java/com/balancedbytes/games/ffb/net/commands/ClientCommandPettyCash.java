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

public class ClientCommandPettyCash
extends NetCommand {
    private int fPettyCash;

    public ClientCommandPettyCash() {
    }

    public ClientCommandPettyCash(int pPettyCash) {
        this.fPettyCash = pPettyCash;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_PETTY_CASH;
    }

    public int getPettyCash() {
        return this.fPettyCash;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.PETTY_CASH.addTo(jsonObject, this.fPettyCash);
        return jsonObject;
    }

    @Override
    public ClientCommandPettyCash initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fPettyCash = IJsonOption.PETTY_CASH.getFrom(jsonObject);
        return this;
    }
}

