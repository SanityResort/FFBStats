/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.ServerStatus;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandStatus
extends ServerCommand {
    private ServerStatus fServerStatus;
    private String fMessage;

    public ServerCommandStatus() {
    }

    public ServerCommandStatus(ServerStatus pServerStatus, String pMessage) {
        this.fServerStatus = pServerStatus;
        this.fMessage = pMessage;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_STATUS;
    }

    public ServerStatus getServerStatus() {
        return this.fServerStatus;
    }

    public String getMessage() {
        return this.fMessage;
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
        IJsonOption.SERVER_STATUS.addTo(jsonObject, this.fServerStatus);
        IJsonOption.MESSAGE.addTo(jsonObject, this.fMessage);
        return jsonObject;
    }

    @Override
    public ServerCommandStatus initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fServerStatus = (ServerStatus)IJsonOption.SERVER_STATUS.getFrom(jsonObject);
        this.fMessage = IJsonOption.MESSAGE.getFrom(jsonObject);
        return this;
    }
}

