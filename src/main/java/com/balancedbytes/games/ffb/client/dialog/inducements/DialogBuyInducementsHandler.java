/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog.inducements;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.dialog.inducements.DialogBuyInducements;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogBuyInducementsParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Team;

public class DialogBuyInducementsHandler
extends DialogHandler {
    public DialogBuyInducementsHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogBuyInducementsParameter dialogBuyInducementsParameter = (DialogBuyInducementsParameter)game.getDialogParameter();
        if (dialogBuyInducementsParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(dialogBuyInducementsParameter.getTeamId())) {
                this.setDialog(new DialogBuyInducements(this.getClient(), dialogBuyInducementsParameter.getTeamId(), dialogBuyInducementsParameter.getAvailableGold()));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Buy Inducements", "Waiting for coach to buy Inducements.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.BUY_INDUCEMENTS)) {
            DialogBuyInducements buyInducementsDialog = (DialogBuyInducements)pDialog;
            this.getClient().getCommunication().sendBuyInducements(buyInducementsDialog.getTeamId(), buyInducementsDialog.getAvailableGold(), buyInducementsDialog.getSelectedInducements(), buyInducementsDialog.getSelectedStarPlayerIds(), buyInducementsDialog.getSelectedMercenaryIds(), buyInducementsDialog.getSelectedMercenarySkills());
        }
    }
}

