/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogFollowupChoice;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogFollowupChoiceHandler
extends DialogHandler {
    public DialogFollowupChoiceHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        if (this.getClient().getMode() == ClientMode.PLAYER && game.getTeamHome().hasPlayer(player)) {
            this.setDialog(new DialogFollowupChoice(this.getClient()));
            this.getDialog().showDialog(this);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.FOLLOWUP_CHOICE)) {
            DialogFollowupChoice followUpDialog = (DialogFollowupChoice)pDialog;
            this.getClient().getCommunication().sendFollowupChoice(followUpDialog.isChoiceYes());
        }
    }
}

