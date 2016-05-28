/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonLongOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandPing
extends NetCommand {
    private boolean fHasEntropy;
    private byte fEntropy;
    private long fTimestamp;

    public ClientCommandPing() {
    }

    public ClientCommandPing(long pTimestamp, boolean pHasEntropy, byte pEntropy) {
        this.fTimestamp = pTimestamp;
        this.fHasEntropy = pHasEntropy;
        this.fEntropy = pEntropy;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_PING;
    }

    public long getTimestamp() {
        return this.fTimestamp;
    }

    public boolean hasEntropy() {
        return this.fHasEntropy;
    }

    public byte getEntropy() {
        return this.fEntropy;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.TIMESTAMP.addTo(jsonObject, this.fTimestamp);
        IJsonOption.HAS_ENTROPY.addTo(jsonObject, this.fHasEntropy);
        IJsonOption.ENTROPY.addTo(jsonObject, this.fEntropy);
        return jsonObject;
    }

    @Override
    public ClientCommandPing initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fTimestamp = IJsonOption.TIMESTAMP.getFrom(jsonObject);
        this.fHasEntropy = IJsonOption.HAS_ENTROPY.getFrom(jsonObject);
        this.fEntropy = (byte)IJsonOption.ENTROPY.getFrom(jsonObject);
        return this;
    }
}

