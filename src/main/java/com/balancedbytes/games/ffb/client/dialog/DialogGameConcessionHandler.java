/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ConcedeGameStatus;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogConcedeGame;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilPlayer;

public class DialogGameConcessionHandler
extends DialogHandler {
    public DialogGameConcessionHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == this.getClient().getMode() && game.isHomePlaying()) {
            boolean legalConcession = UtilPlayer.findPlayersInReserveOrField(game, game.getTeamHome()).length <= 2;
            this.setDialog(new DialogConcedeGame(this.getClient(), legalConcession));
            this.getDialog().showDialog(this);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.CONCEDE_GAME)) {
            DialogConcedeGame concessionDialog = (DialogConcedeGame)pDialog;
            if (concessionDialog.isChoiceYes()) {
                this.getClient().getCommunication().sendConcedeGame(ConcedeGameStatus.CONFIRMED);
            } else {
                this.getClient().getCommunication().sendConcedeGame(ConcedeGameStatus.DENIED);
            }
            Game game = this.getClient().getGame();
            game.setDialogParameter(null);
        }
    }
}

