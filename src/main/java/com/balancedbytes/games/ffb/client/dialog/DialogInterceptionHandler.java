/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogInterception;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogInterceptionParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilPassing;

public class DialogInterceptionHandler
extends DialogHandler {
    public DialogInterceptionHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogInterceptionParameter dialogParameter = (DialogInterceptionParameter)game.getDialogParameter();
        Player thrower = game.getPlayerById(dialogParameter.getThrowerId());
        if (ClientMode.PLAYER != this.getClient().getMode() || game.getTeamHome().hasPlayer(thrower)) {
            this.showStatus("Interception", "Waiting for coach to choose an interceptor.", StatusType.WAITING);
        } else {
            this.setDialog(new DialogInterception(this.getClient()));
            this.getDialog().showDialog(this);
            if (!game.isHomePlaying()) {
                this.playSound(SoundId.QUESTION);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        Game game = this.getClient().getGame();
        if (this.testDialogHasId(pDialog, DialogId.INTERCEPTION)) {
            DialogInterception interceptionDialog = (DialogInterception)pDialog;
            if (!interceptionDialog.isChoiceYes()) {
                this.getClient().getCommunication().sendInterceptorChoice(null);
            } else {
                Player[] interceptors = UtilPassing.findInterceptors(game, game.getThrower(), game.getPassCoordinate());
                if (interceptors != null && interceptors.length == 1) {
                    this.getClient().getCommunication().sendInterceptorChoice(interceptors[0]);
                }
            }
        }
        game.setWaitingForOpponent(false);
        this.getClient().updateClientState();
    }
}

