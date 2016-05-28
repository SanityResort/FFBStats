/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPettyCash;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogPettyCashParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogPettyCashHandler
extends DialogHandler {
    public DialogPettyCashHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogPettyCashParameter dialogPettyCashParameter = (DialogPettyCashParameter)game.getDialogParameter();
        if (dialogPettyCashParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && dialogPettyCashParameter.getTeamId().equals(game.getTeamHome().getId())) {
                this.setDialog(new DialogPettyCash(this.getClient(), dialogPettyCashParameter.getTeamValue(), dialogPettyCashParameter.getTreasury(), dialogPettyCashParameter.getOpponentTeamValue()));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Petty Cash", "Waiting for coach to transfer gold to petty cash.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.PETTY_CASH)) {
            DialogPettyCash pettyCashDialog = (DialogPettyCash)pDialog;
            this.getClient().getCommunication().sendPettyCash(pettyCashDialog.getPettyCash());
        }
    }
}

