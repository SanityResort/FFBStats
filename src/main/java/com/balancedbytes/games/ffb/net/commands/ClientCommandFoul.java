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

public class ClientCommandFoul
extends ClientCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private String fDefenderId;

    public ClientCommandFoul() {
    }

    public ClientCommandFoul(String pActingPlayerId, String pDefenderId) {
        this.fActingPlayerId = pActingPlayerId;
        this.fDefenderId = pDefenderId;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_FOUL;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        return jsonObject;
    }

    @Override
    public ClientCommandFoul initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        return this;
    }
}

