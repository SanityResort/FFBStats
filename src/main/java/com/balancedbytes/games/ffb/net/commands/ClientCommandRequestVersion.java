/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonValue;

public class ClientCommandRequestVersion
extends ClientCommand {
    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_REQUEST_VERSION;
    }

    @Override
    public ClientCommandRequestVersion initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        return this;
    }
}

