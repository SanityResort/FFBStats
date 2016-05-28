/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogApothecaryChoice;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogApothecaryChoiceParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogApothecaryChoiceHandler
extends DialogHandler {
    private DialogApothecaryChoiceParameter fDialogParameter;

    public DialogApothecaryChoiceHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        this.fDialogParameter = (DialogApothecaryChoiceParameter)game.getDialogParameter();
        if (this.fDialogParameter != null) {
            Player player = game.getPlayerById(this.fDialogParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && this.getClient().getGame().getTeamHome().hasPlayer(player)) {
                this.setDialog(new DialogApothecaryChoice(this.getClient(), this.fDialogParameter));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Apothecary", "Waiting for coach to choose Apothecary Result.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.APOTHECARY_CHOICE)) {
            DialogApothecaryChoice apothecaryChoiceDialog = (DialogApothecaryChoice)pDialog;
            if (apothecaryChoiceDialog.isChoiceNewInjury()) {
                this.getClient().getCommunication().sendApothecaryChoice(this.fDialogParameter.getPlayerId(), this.fDialogParameter.getPlayerStateNew(), this.fDialogParameter.getSeriousInjuryNew());
            } else {
                this.getClient().getCommunication().sendApothecaryChoice(this.fDialogParameter.getPlayerId(), this.fDialogParameter.getPlayerStateOld(), this.fDialogParameter.getSeriousInjuryOld());
            }
        }
    }
}

