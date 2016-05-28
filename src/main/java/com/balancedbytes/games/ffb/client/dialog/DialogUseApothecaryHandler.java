/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogUseApothecary;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseApothecaryParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogUseApothecaryHandler
extends DialogHandler {
    public DialogUseApothecaryHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogUseApothecaryParameter dialogUseApothecaryParameter = (DialogUseApothecaryParameter)game.getDialogParameter();
        if (dialogUseApothecaryParameter != null) {
            Player player = game.getPlayerById(dialogUseApothecaryParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && this.getClient().getGame().getTeamHome().hasPlayer(player)) {
                this.setDialog(new DialogUseApothecary(this.getClient(), dialogUseApothecaryParameter));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Apothecary", "Waiting for coach to use Apothecary.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.USE_APOTHECARY)) {
            DialogUseApothecary apothecaryDialog = (DialogUseApothecary)pDialog;
            this.getClient().getCommunication().sendUseApothecary(apothecaryDialog.getPlayerId(), apothecaryDialog.isChoiceYes());
        }
    }
}

