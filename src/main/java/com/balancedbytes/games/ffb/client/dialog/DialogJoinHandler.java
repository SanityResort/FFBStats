/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;

public class DialogJoinHandler
extends DialogHandler {
    private static final String _STATUS_TITLE = "Game start";

    public DialogJoinHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        if (ClientMode.PLAYER == this.getClient().getMode()) {
            this.showStatus("Game start", "Waiting for coach to join the game.", StatusType.WAITING);
        } else {
            this.showStatus("Game start", "Waiting for game to start.", StatusType.WAITING);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
    }
}

