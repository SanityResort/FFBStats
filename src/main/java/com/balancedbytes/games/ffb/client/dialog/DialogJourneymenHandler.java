/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogJourneymen;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogJourneymenParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogJourneymenHandler
extends DialogHandler {
    public DialogJourneymenHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogJourneymenParameter dialogJourneymenParameter = (DialogJourneymenParameter)game.getDialogParameter();
        if (dialogJourneymenParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(dialogJourneymenParameter.getTeamId())) {
                this.setDialog(new DialogJourneymen(this.getClient(), dialogJourneymenParameter.getNrOfSlots(), dialogJourneymenParameter.getPositionIds()));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Journeymen", "Waiting for coach to hire up to " + dialogJourneymenParameter.getNrOfSlots() + " Journeymen.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.JOURNEYMEN)) {
            DialogJourneymen journeymenDialog = (DialogJourneymen)pDialog;
            this.getClient().getCommunication().sendJourneymen(journeymenDialog.getPositionIds(), journeymenDialog.getSlotsSelected());
        }
    }
}

