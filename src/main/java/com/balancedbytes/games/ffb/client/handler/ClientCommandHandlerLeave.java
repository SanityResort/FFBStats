/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandLeave;

public class ClientCommandHandlerLeave
extends ClientCommandHandler {
    protected ClientCommandHandlerLeave(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_LEAVE;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        if (pMode == ClientCommandHandlerMode.QUEUING) {
            return true;
        }
        ServerCommandLeave leaveCommand = (ServerCommandLeave)pNetCommand;
        if (ClientMode.PLAYER == leaveCommand.getClientMode()) {
            this.getClient().getClientData().setTurnTimerStopped(true);
        }
        this.getClient().getClientData().setSpectators(leaveCommand.getSpectators());
        if (pMode != ClientCommandHandlerMode.REPLAYING) {
            UserInterface userInterface = this.getClient().getUserInterface();
            userInterface.getStatusReport().reportLeave(leaveCommand);
            this.refreshSideBars();
        }
        return true;
    }
}

