/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonValue;

public class ClientCommandConfirm
extends ClientCommand {
    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_CONFIRM;
    }

    @Override
    public ClientCommandConfirm initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        return this;
    }
}

