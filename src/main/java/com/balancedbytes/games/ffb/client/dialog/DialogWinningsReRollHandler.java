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
import com.balancedbytes.games.ffb.client.dialog.DialogWinningsReRoll;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogWinningsReRollParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogWinningsReRollHandler
extends DialogHandler {
    public DialogWinningsReRollHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogWinningsReRollParameter dialogParameter = (DialogWinningsReRollParameter)game.getDialogParameter();
        if (dialogParameter != null) {
            Team team;
            Team team2 = team = game.getTeamHome().getId().equals(dialogParameter.getTeamId()) ? game.getTeamHome() : game.getTeamAway();
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome() == team) {
                this.setDialog(new DialogWinningsReRoll(this.getClient(), dialogParameter.getOldRoll()));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Winnings", "Waiting for coach to re-roll winnings.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.WINNINGS_RE_ROLL)) {
            DialogWinningsReRoll winningsReRollDialog = (DialogWinningsReRoll)pDialog;
            ReRollSource reRollSource = !winningsReRollDialog.isChoiceYes() ? ReRollSource.WINNINGS : null;
            this.getClient().getCommunication().sendUseReRoll(ReRolledAction.WINNINGS, reRollSource);
        }
    }
}

