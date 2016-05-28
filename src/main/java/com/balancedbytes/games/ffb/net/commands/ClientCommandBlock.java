/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ICommandWithActingPlayer;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandBlock
extends NetCommand
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        IJsonOption.USING_STAB.addTo(jsonObject, this.fUsingStab);
        return jsonObject;
    }

    @Override
    public ClientCommandBlock initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fUsingStab = IJsonOption.USING_STAB.getFrom(jsonObject);
        return this;
    }
}

