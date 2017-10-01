/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerAddPlayer;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerAdminMessage;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerGameState;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerGameTime;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerJoin;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerLeave;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerModelSync;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerRemovePlayer;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerSocketClosed;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerSound;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerTalk;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerUserSettings;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import java.util.HashMap;
import java.util.Map;

public class ClientCommandHandlerFactory {
    private FantasyFootballClient fClient;
    private Map<NetCommandId, ClientCommandHandler> fCommandHandlerById;

    public ClientCommandHandlerFactory(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fCommandHandlerById = new HashMap<NetCommandId, ClientCommandHandler>();
        this.register(new ClientCommandHandlerJoin(this.getClient()));
        this.register(new ClientCommandHandlerLeave(this.getClient()));
        this.register(new ClientCommandHandlerTalk(this.getClient()));
        this.register(new ClientCommandHandlerGameState(this.getClient()));
        this.register(new ClientCommandHandlerSound(this.getClient()));
        this.register(new ClientCommandHandlerUserSettings(this.getClient()));
        this.register(new ClientCommandHandlerAdminMessage(this.getClient()));
        this.register(new ClientCommandHandlerModelSync(this.getClient()));
        this.register(new ClientCommandHandlerSocketClosed(this.getClient()));
        this.register(new ClientCommandHandlerAddPlayer(this.getClient()));
        this.register(new ClientCommandHandlerRemovePlayer(this.getClient()));
        this.register(new ClientCommandHandlerGameTime(this.getClient()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        if (pNetCommand != null) {
            ClientCommandHandler commandHandler = this.getCommandHandler(pNetCommand.getId());
            if (commandHandler != null) {
                boolean completed = commandHandler.handleNetCommand(pNetCommand, pMode);
                if (completed) {
                    this.updateClientState(pNetCommand, pMode, false);
                } else if (pMode == ClientCommandHandlerMode.PLAYING) {
                    ClientCommandHandlerFactory clientCommandHandlerFactory = this;
                    synchronized (clientCommandHandlerFactory) {
                        try {
                            this.wait();
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                    }
                }
            } else {
                this.updateClientState(pNetCommand, pMode, false);
            }
        }
    }

    public void updateClientState(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        this.updateClientState(pNetCommand, pMode, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateClientState(NetCommand pNetCommand, ClientCommandHandlerMode pMode, boolean pNotify) {
        ClientState clientState = this.getClient().updateClientState();
        if (clientState != null) {
            clientState.handleCommand(pNetCommand);
        }
        if (pNotify) {
            ClientCommandHandlerFactory clientCommandHandlerFactory = this;
            synchronized (clientCommandHandlerFactory) {
                this.notifyAll();
            }
        }
    }

    public ClientCommandHandler getCommandHandler(NetCommandId pType) {
        return this.fCommandHandlerById.get(pType);
    }

    private void register(ClientCommandHandler pCommandHandler) {
        this.fCommandHandlerById.put(pCommandHandler.getId(), pCommandHandler);
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }
}

