/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogCoinChoice;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;

public class DialogCoinChoiceHandler
extends DialogHandler {
    public DialogCoinChoiceHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == this.getClient().getMode() && game.isHomePlaying()) {
            this.setDialog(new DialogCoinChoice(this.getClient()));
            this.getDialog().showDialog(this);
        } else {
            this.showStatus("Coin Throw", "Waiting for coach to choose heads or tails.", StatusType.WAITING);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.COIN_CHOICE)) {
            DialogCoinChoice coinThrowDialog = (DialogCoinChoice)pDialog;
            this.getClient().getCommunication().sendCoinChoice(coinThrowDialog.isChoiceHeads());
        }
    }
}

