/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogReceiveChoice;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogReceiveChoiceParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogReceiveChoiceHandler
extends DialogHandler {
    public DialogReceiveChoiceHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogReceiveChoiceParameter dialogReceiveChoiceParameter = (DialogReceiveChoiceParameter)game.getDialogParameter();
        if (dialogReceiveChoiceParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(dialogReceiveChoiceParameter.getChoosingTeamId())) {
                this.setDialog(new DialogReceiveChoice(this.getClient()));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Receive Choice", "Waiting for coach to choose to kick or receive.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.RECEIVE_CHOICE)) {
            DialogReceiveChoice receiveDialog = (DialogReceiveChoice)pDialog;
            this.getClient().getCommunication().sendReceiveChoice(receiveDialog.isChoiceYes());
        }
    }
}

