/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import java.io.PrintStream;

public class ClientCommandHandlerSocketClosed
extends ClientCommandHandler {
    protected ClientCommandHandlerSocketClosed(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.INTERNAL_SERVER_SOCKET_CLOSED;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.getStatusReport().reportSocketClosed();
        System.out.println("Connection closed by server.");
        return true;
    }
}

