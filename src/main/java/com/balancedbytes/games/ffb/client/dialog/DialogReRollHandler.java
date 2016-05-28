/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogReRoll;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogReRollParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogReRollHandler
extends DialogHandler {
    public DialogReRollHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogReRollParameter dialogReRollParameter = (DialogReRollParameter)game.getDialogParameter();
        if (dialogReRollParameter != null) {
            Player player = game.getPlayerById(dialogReRollParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().hasPlayer(player)) {
                this.setDialog(new DialogReRoll(this.getClient(), dialogReRollParameter));
                this.getDialog().showDialog(this);
            } else {
                StringBuilder message = new StringBuilder();
                String reRolledActionName = dialogReRollParameter.getReRolledAction() != null ? dialogReRollParameter.getReRolledAction().getName() : null;
                message.append("Waiting to re-roll ").append(reRolledActionName);
                if (dialogReRollParameter.getMinimumRoll() > 0) {
                    message.append(" (").append(dialogReRollParameter.getMinimumRoll()).append("+ to succeed)");
                }
                message.append(".");
                this.showStatus("Re-roll", message.toString(), StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.RE_ROLL)) {
            DialogReRoll reRollDialog = (DialogReRoll)pDialog;
            this.getClient().getCommunication().sendUseReRoll(reRollDialog.getReRolledAction(), reRollDialog.getReRollSource());
        }
    }
}

