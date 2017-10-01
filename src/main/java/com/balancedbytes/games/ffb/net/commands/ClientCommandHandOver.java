/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.balancedbytes.games.ffb.net.commands.ICommandWithActingPlayer;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandHandOver
extends ClientCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private String fCatcherId;

    public ClientCommandHandOver() {
    }

    public ClientCommandHandOver(String pActingPlayerId, String pCatcherId) {
        this.fActingPlayerId = pActingPlayerId;
        this.fCatcherId = pCatcherId;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_HAND_OVER;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public String getCatcherId() {
        return this.fCatcherId;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.CATCHER_ID.addTo(jsonObject, this.fCatcherId);
        return jsonObject;
    }

    @Override
    public ClientCommandHandOver initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fCatcherId = IJsonOption.CATCHER_ID.getFrom(jsonObject);
        return this;
    }
}

