/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandPong
extends ServerCommand {
    private long fTimestamp;
    private transient long fReceived;

    public ServerCommandPong() {
    }

    public ServerCommandPong(long pTimestamp) {
        this.fTimestamp = pTimestamp;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_PONG;
    }

    public long getTimestamp() {
        return this.fTimestamp;
    }

    public void setReceived(long pReceived) {
        this.fReceived = pReceived;
    }

    public long getReceived() {
        return this.fReceived;
    }

    @Override
    public boolean isReplayable() {
        return false;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.TIMESTAMP.addTo(jsonObject, this.fTimestamp);
        return jsonObject;
    }

    @Override
    public ServerCommandPong initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fTimestamp = IJsonOption.TIMESTAMP.getFrom(jsonObject);
        return this;
    }
}

