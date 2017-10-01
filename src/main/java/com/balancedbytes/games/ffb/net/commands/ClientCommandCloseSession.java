/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonValue;

public class ClientCommandCloseSession
extends ClientCommand {
    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_CLOSE_SESSION;
    }

    @Override
    public ClientCommandCloseSession initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        return this;
    }
}

