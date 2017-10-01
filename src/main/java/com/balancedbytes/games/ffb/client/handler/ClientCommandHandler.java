/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;

public abstract class ClientCommandHandler {
    private FantasyFootballClient fClient;

    protected ClientCommandHandler(FantasyFootballClient pClient) {
        this.fClient = pClient;
    }

    public abstract NetCommandId getId();

    public abstract boolean handleNetCommand(NetCommand var1, ClientCommandHandlerMode var2);

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void playSound(SoundId pSoundId, ClientCommandHandlerMode pMode, boolean pWait) {

    }

    protected void refreshFieldComponent() {
        this.getClient().getUserInterface().invokeAndWait(new Runnable(){

            @Override
            public void run() {
                ClientCommandHandler.this.getClient().getUserInterface().getFieldComponent().refresh();
            }
        });
    }

    protected void refreshSideBars() {
        this.getClient().getUserInterface().invokeAndWait(new Runnable(){

            @Override
            public void run() {
                ClientCommandHandler.this.getClient().getUserInterface().refreshSideBars();
            }
        });
    }

    protected void refreshGameMenuBar() {
        this.getClient().getUserInterface().invokeAndWait(new Runnable(){

            @Override
            public void run() {
            }
        });
    }

    protected void updateDialog() {
        this.getClient().getUserInterface().invokeAndWait(new Runnable(){

            @Override
            public void run() {
            }
        });
    }

    protected void updateGameTitle(GameTitle pGameTitle) {
    }

}

