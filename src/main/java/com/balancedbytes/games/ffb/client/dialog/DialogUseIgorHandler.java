/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogUseIgor;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseIgorParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogUseIgorHandler
extends DialogHandler {
    public DialogUseIgorHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogUseIgorParameter dialogUseIgorParameter = (DialogUseIgorParameter)game.getDialogParameter();
        if (dialogUseIgorParameter != null) {
            Player player = game.getPlayerById(dialogUseIgorParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && this.getClient().getGame().getTeamHome().hasPlayer(player)) {
                this.setDialog(new DialogUseIgor(this.getClient(), dialogUseIgorParameter));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Igor", "Waiting for coach to use Igor.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.USE_IGOR)) {
            DialogUseIgor igorDialog = (DialogUseIgor)pDialog;
            if (igorDialog.isChoiceYes()) {
                this.getClient().getCommunication().sendUseInducement(InducementType.IGOR, igorDialog.getPlayerId());
            } else {
                this.getClient().getCommunication().sendUseInducement(InducementType.IGOR, (String)null);
            }
        }
    }
}

