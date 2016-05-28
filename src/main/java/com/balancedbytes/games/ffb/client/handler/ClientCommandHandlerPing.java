/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandPing;

public class ClientCommandHandlerPing
extends ClientCommandHandler {
    protected ClientCommandHandlerPing(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_PING;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        ServerCommandPing pingCommand = (ServerCommandPing)pNetCommand;
        UserInterface userInterface = this.getClient().getUserInterface();
        GameTitle gameTitle = new GameTitle(userInterface.getGameTitle());
        gameTitle.setPingTime(pingCommand.getReceived() - pingCommand.getTimestamp());
        this.updateGameTitle(gameTitle);
        return true;
    }
}

