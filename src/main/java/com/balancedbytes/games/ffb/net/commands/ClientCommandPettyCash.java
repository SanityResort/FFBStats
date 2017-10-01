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

public class ClientCommandPettyCash
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.PETTY_CASH.addTo(jsonObject, this.fPettyCash);
        return jsonObject;
    }

    @Override
    public ClientCommandPettyCash initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fPettyCash = IJsonOption.PETTY_CASH.getFrom(jsonObject);
        return this;
    }
}

