/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.sound.SoundEngine;
import com.balancedbytes.games.ffb.dialog.DialogId;

public abstract class DialogHandler
implements IDialogCloseListener {
    private FantasyFootballClient fClient;
    private IDialog fDialog;

    public DialogHandler(FantasyFootballClient pClient) {
        this.fClient = pClient;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    protected void setDialog(IDialog pDialog) {
        this.fDialog = pDialog;
    }

    public IDialog getDialog() {
        return this.fDialog;
    }

    public abstract void showDialog();

    public void updateDialog() {
    }

    public void hideDialog() {
        if (this.getDialog() != null) {
            this.getDialog().hideDialog();
        }
        ClientData clientData = this.getClient().getClientData();
        clientData.clearStatus();
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.refreshSideBars();
    }

    protected void showStatus(String pTitle, String pMessage, StatusType pType) {
        ClientData clientData = this.getClient().getClientData();
        clientData.setStatus(pTitle, pMessage, pType);
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.refreshSideBars();
    }

    protected boolean testDialogHasId(IDialog pDialog, DialogId pDialogId) {
        return pDialog != null && pDialog.getId() != null && pDialog.getId() == pDialogId;
    }

    protected void playSound(SoundId pSound) {
        if (pSound != null) {
            SoundEngine soundEngine = this.getClient().getUserInterface().getSoundEngine();
            String soundSetting = this.getClient().getProperty("setting.sound.mode");
            if ("soundOn".equals(soundSetting) || "muteSpectators".equals(soundSetting) && !pSound.isSpectatorSound()) {
                soundEngine.playSound(pSound);
            }
        }
    }

    public boolean isEndTurnAllowedWhileDialogVisible() {
        return true;
    }
}

