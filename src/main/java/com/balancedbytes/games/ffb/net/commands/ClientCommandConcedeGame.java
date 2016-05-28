/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ConcedeGameStatus;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandConcedeGame
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.CONCEDE_GAME_STATUS.addTo(jsonObject, this.fConcedeGameStatus);
        return jsonObject;
    }

    @Override
    public ClientCommandConcedeGame initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fConcedeGameStatus = (ConcedeGameStatus)IJsonOption.CONCEDE_GAME_STATUS.getFrom(jsonObject);
        return this;
    }
}

