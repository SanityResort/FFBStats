/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandBlock
extends ClientCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private String fDefenderId;
    private boolean fUsingStab;

    public ClientCommandBlock() {
    }

    public ClientCommandBlock(String pActingPlayerId, String pDefenderId, boolean pUsingStab) {
        this.fActingPlayerId = pActingPlayerId;
        this.fDefenderId = pDefenderId;
        this.fUsingStab = pUsingStab;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_BLOCK;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public boolean isUsingStab() {
        return this.fUsingStab;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        IJsonOption.USING_STAB.addTo(jsonObject, this.fUsingStab);
        return jsonObject;
    }

    @Override
    public ClientCommandBlock initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fUsingStab = IJsonOption.USING_STAB.getFrom(jsonObject);
        return this;
    }
}

