/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ICommandWithActingPlayer;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandGaze
extends NetCommand
implements ICommandWithActingPlayer {
    private String fActingPlayerId;
    private String fVictimId;

    public ClientCommandGaze() {
    }

    public ClientCommandGaze(String pActingPlayerId, String pCatcherId) {
        this.fActingPlayerId = pActingPlayerId;
        this.fVictimId = pCatcherId;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_GAZE;
    }

    @Override
    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public String getVictimId() {
        return this.fVictimId;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.ACTING_PLAYER_ID.addTo(jsonObject, this.fActingPlayerId);
        IJsonOption.VICTIM_ID.addTo(jsonObject, this.fVictimId);
        return jsonObject;
    }

    @Override
    public ClientCommandGaze initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fVictimId = IJsonOption.VICTIM_ID.getFrom(jsonObject);
        return this;
    }
}

