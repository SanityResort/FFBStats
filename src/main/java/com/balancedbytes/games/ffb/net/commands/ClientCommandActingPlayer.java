/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandActingPlayer
extends NetCommand {
    private String fPlayerId;
    private PlayerAction fPlayerAction;
    private boolean fLeaping;

    public ClientCommandActingPlayer() {
    }

    public ClientCommandActingPlayer(String pPlayerId, PlayerAction pPlayerAction, boolean pLeaping) {
        this.fPlayerId = pPlayerId;
        this.fPlayerAction = pPlayerAction;
        this.fLeaping = pLeaping;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_ACTING_PLAYER;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public PlayerAction getPlayerAction() {
        return this.fPlayerAction;
    }

    public boolean isLeaping() {
        return this.fLeaping;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.PLAYER_ACTION.addTo(jsonObject, this.fPlayerAction);
        IJsonOption.LEAPING.addTo(jsonObject, this.fLeaping);
        return jsonObject;
    }

    @Override
    public ClientCommandActingPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fPlayerAction = (PlayerAction)IJsonOption.PLAYER_ACTION.getFrom(jsonObject);
        this.fLeaping = IJsonOption.LEAPING.getFrom(jsonObject);
        return this;
    }
}

