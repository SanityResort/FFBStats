/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonValue;

public class ClientCommandIllegalProcedure
extends ClientCommand {
    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_ILLEGAL_PROCEDURE;
    }

    @Override
    public ClientCommandIllegalProcedure initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        return this;
    }
}

