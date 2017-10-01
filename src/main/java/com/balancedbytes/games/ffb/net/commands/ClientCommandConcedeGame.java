/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ConcedeGameStatus;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandConcedeGame
extends ClientCommand {
    private ConcedeGameStatus fConcedeGameStatus;

    public ClientCommandConcedeGame() {
    }

    public ClientCommandConcedeGame(ConcedeGameStatus pConcedeGameStatus) {
        this.fConcedeGameStatus = pConcedeGameStatus;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_CONCEDE_GAME;
    }

    public ConcedeGameStatus getConcedeGameStatus() {
        return this.fConcedeGameStatus;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.CONCEDE_GAME_STATUS.addTo(jsonObject, this.fConcedeGameStatus);
        return jsonObject;
    }

    @Override
    public ClientCommandConcedeGame initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fConcedeGameStatus = (ConcedeGameStatus)IJsonOption.CONCEDE_GAME_STATUS.getFrom(jsonObject);
        return this;
    }
}

