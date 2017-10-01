/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonValue;

public class ClientCommandEndTurn
extends ClientCommand {
    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_END_TURN;
    }

    @Override
    public ClientCommandEndTurn initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        return this;
    }
}

