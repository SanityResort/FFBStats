/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandGameState
extends ServerCommand {
    private Game fGame;

    public ServerCommandGameState() {
    }

    public ServerCommandGameState(Game pGame) {
        this();
        this.fGame = pGame;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_GAME_STATE;
    }

    public Game getGame() {
        return this.fGame;
    }

    public ServerCommandGameState transform() {
        return new ServerCommandGameState(this.getGame().transform());
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
        if (this.fGame != null) {
            IJsonOption.GAME.addTo(jsonObject, this.fGame.toJsonValue());
        }
        return jsonObject;
    }

    @Override
    public ServerCommandGameState initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        JsonObject gameObject = IJsonOption.GAME.getFrom(jsonObject);
        if (gameObject != null) {
            this.fGame = new Game().initFrom(gameObject);
        }
        return this;
    }
}

