/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.GameList;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandGameList
extends ServerCommand {
    private GameList fGameList;

    public ServerCommandGameList() {
    }

    public ServerCommandGameList(GameList pGameList) {
        this();
        this.fGameList = pGameList;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_GAME_LIST;
    }

    public GameList getGameList() {
        return this.fGameList;
    }

    @Override
    public boolean isReplayable() {
        return false;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COMMAND_NR.addTo(jsonObject, this.getCommandNr());
        if (this.fGameList != null) {
            IJsonOption.GAME_LIST.addTo(jsonObject, this.fGameList.toJsonValue());
        }
        return jsonObject;
    }

    @Override
    public ServerCommandGameList initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        JsonObject gameListObject = IJsonOption.GAME_LIST.getFrom(jsonObject);
        this.fGameList = gameListObject != null ? new GameList().initFrom(gameListObject) : null;
        return this;
    }
}

