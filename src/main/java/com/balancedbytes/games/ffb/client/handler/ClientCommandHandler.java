/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.ui.GameTitleUpdateTask;
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

    protected void playSound(SoundId pSoundId, ClientCommandHandlerMode pMode, boolean pWait) {
        if (pSoundId != null) {
            String soundSetting = this.getClient().getProperty("setting.sound.mode");
            if (pMode == ClientCommandHandlerMode.PLAYING || pMode == ClientCommandHandlerMode.REPLAYING && this.getClient().getReplayer().isReplayingSingleSpeedForward()) {
                long soundLength;
                if ("soundOn".equals(soundSetting) || "muteSpectators".equals(soundSetting) && !pSoundId.isSpectatorSound()) {
                }
                if (pWait && pMode == ClientCommandHandlerMode.PLAYING ) {
                    ClientCommandHandler clientCommandHandler = this;
                }
            }
        }
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
                ClientCommandHandler.this.getClient().getUserInterface().getGameMenuBar().refresh();
            }
        });
    }

    protected void updateDialog() {
        this.getClient().getUserInterface().invokeAndWait(new Runnable(){

            @Override
            public void run() {
                ClientCommandHandler.this.getClient().getUserInterface().getDialogManager().updateDialog();
            }
        });
    }

    protected void updateGameTitle(GameTitle pGameTitle) {
        this.getClient().getUserInterface().invokeAndWait(new GameTitleUpdateTask(this.getClient(), pGameTitle));
    }

}

