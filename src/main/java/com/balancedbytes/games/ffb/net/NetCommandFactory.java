/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class NetCommandFactory {
    public NetCommand forJsonValue(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        NetCommand netCommand = null;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        NetCommandId netCommandId = (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject);
        if (netCommandId != null && (netCommand = netCommandId.createNetCommand()) != null) {
            netCommand.initFrom(pJsonValue);
        }
        return netCommand;
    }
}

