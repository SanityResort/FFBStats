/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandLeave
extends ServerCommand {
    private String fCoach;
    private ClientMode fClientMode;
    private int fSpectators;

    public ServerCommandLeave() {
    }

    public ServerCommandLeave(String pCoach, ClientMode pClientMode, int pSpectators) {
        this.fCoach = pCoach;
        this.fClientMode = pClientMode;
        this.fSpectators = pSpectators;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_LEAVE;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public ClientMode getClientMode() {
        return this.fClientMode;
    }

    public int getSpectators() {
        return this.fSpectators;
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
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        IJsonOption.CLIENT_MODE.addTo(jsonObject, this.fClientMode);
        IJsonOption.SPECTATORS.addTo(jsonObject, this.fSpectators);
        return jsonObject;
    }

    @Override
    public ServerCommandLeave initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.fClientMode = (ClientMode)IJsonOption.CLIENT_MODE.getFrom(jsonObject);
        this.fSpectators = IJsonOption.SPECTATORS.getFrom(jsonObject);
        return this;
    }
}

