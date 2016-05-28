/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogUseInducement;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseInducementParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogUseInducementHandler
extends DialogHandler {
    public DialogUseInducementHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogUseInducementParameter dialogParameter = (DialogUseInducementParameter)game.getDialogParameter();
        if (dialogParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(dialogParameter.getTeamId())) {
                this.setDialog(new DialogUseInducement(this.getClient(), dialogParameter));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Use Inducement", "Waiting for coach to select an inducement.", StatusType.WAITING);
            }
        }
    }

    @Override
    public boolean isEndTurnAllowedWhileDialogVisible() {
        return false;
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (pDialog != null && pDialog.getId() == DialogId.USE_INDUCEMENT) {
            DialogUseInducement useInducementDialog = (DialogUseInducement)pDialog;
            if (useInducementDialog.getInducement() != null) {
                this.getClient().getCommunication().sendUseInducement(useInducementDialog.getInducement());
            } else if (useInducementDialog.getCard() != null) {
                this.getClient().getCommunication().sendUseInducement(useInducementDialog.getCard());
            } else {
                this.getClient().getCommunication().sendUseInducement((InducementType)null);
            }
        }
    }
}

