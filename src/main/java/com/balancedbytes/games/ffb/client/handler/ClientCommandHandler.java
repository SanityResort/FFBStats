/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.ClientReplayer;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.sound.SoundEngine;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
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
            SoundEngine soundEngine = this.getClient().getUserInterface().getSoundEngine();
            String soundSetting = this.getClient().getProperty("setting.sound.mode");
            if (pMode == ClientCommandHandlerMode.PLAYING || pMode == ClientCommandHandlerMode.REPLAYING && this.getClient().getReplayer().isReplayingSingleSpeedForward()) {
                long soundLength;
                if ("soundOn".equals(soundSetting) || "muteSpectators".equals(soundSetting) && !pSoundId.isSpectatorSound()) {
                    soundEngine.playSound(pSoundId);
                }
                if (pWait && pMode == ClientCommandHandlerMode.PLAYING && (soundLength = soundEngine.getSoundLength(pSoundId)) > 0) {
                    ClientCommandHandler clientCommandHandler = this;
                    synchronized (clientCommandHandler) {
                        try {
                            Thread.sleep(soundLength);
                        }
                        catch (InterruptedException var9_8) {
                            // empty catch block
                        }
                    }
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

