/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogStartGame;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import java.util.Date;

public class DialogStartGameHandler
extends DialogHandler {
    public DialogStartGameHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        if (ClientMode.PLAYER == this.getClient().getMode()) {
            if (this.getClient().getGame().getScheduled() != null) {
                this.getClient().getCommunication().sendStartGame();
            } else {
                this.setDialog(new DialogStartGame(this.getClient()));
                this.getDialog().showDialog(this);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.START_GAME)) {
            DialogStartGame gameStartDialog = (DialogStartGame)pDialog;
            if (gameStartDialog.isChoiceYes()) {
                this.getClient().getCommunication().sendStartGame();
                this.showStatus("Start game", "Waiting for coach to start the game.", StatusType.WAITING);
            } else {
                this.getClient().stopClient();
            }
        }
    }
}

