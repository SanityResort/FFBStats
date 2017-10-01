/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public abstract class ClientCommand
extends NetCommand {
    private Byte fEntropy;

    public void setEntropy(byte entropy) {
        this.fEntropy = Byte.valueOf(entropy);
    }

    public boolean hasEntropy() {
        return this.fEntropy != null;
    }

    public byte getEntropy() {
        return this.fEntropy.byteValue();
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        if (this.hasEntropy()) {
            IJsonOption.ENTROPY.addTo(jsonObject, this.getEntropy());
        }
        return jsonObject;
    }

    @Override
    public ClientCommand initFrom(JsonValue jsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        if (IJsonOption.ENTROPY.isDefinedIn(jsonObject)) {
            this.setEntropy((byte)IJsonOption.ENTROPY.getFrom(jsonObject));
        }
        return this;
    }
}

