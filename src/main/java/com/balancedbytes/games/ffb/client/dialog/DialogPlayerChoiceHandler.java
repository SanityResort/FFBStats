/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.PlayerChoiceMode;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPlayerChoice;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogPlayerChoiceParameter;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogPlayerChoiceHandler
extends DialogHandler {
    private DialogPlayerChoiceParameter fDialogPlayerChoiceParameter;

    public DialogPlayerChoiceHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        this.fDialogPlayerChoiceParameter = (DialogPlayerChoiceParameter)game.getDialogParameter();
        if (this.fDialogPlayerChoiceParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(this.fDialogPlayerChoiceParameter.getTeamId())) {
                String dialogHeader = this.fDialogPlayerChoiceParameter.getPlayerChoiceMode().getDialogHeader(this.fDialogPlayerChoiceParameter.getMaxSelects());
                FieldCoordinate dialogCoordinate = null;
                String[] playerIds = this.fDialogPlayerChoiceParameter.getPlayerIds();
                if (this.fDialogPlayerChoiceParameter.getPlayerChoiceMode() != PlayerChoiceMode.CARD) {
                    int maxX = 0;
                    int maxY = 0;
                    for (int i = 0; i < playerIds.length; ++i) {
                        Player player = game.getPlayerById(playerIds[i]);
                        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(player);
                        if (playerCoordinate.getX() > maxX) {
                            maxX = playerCoordinate.getX();
                        }
                        if (playerCoordinate.getY() <= maxY) continue;
                        maxY = playerCoordinate.getY();
                    }
                    dialogCoordinate = new FieldCoordinate(maxX, maxY);
                }
                this.setDialog(new DialogPlayerChoice(this.getClient(), dialogHeader, playerIds, this.fDialogPlayerChoiceParameter.getDescriptions(), this.fDialogPlayerChoiceParameter.getMaxSelects(), dialogCoordinate));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus(this.fDialogPlayerChoiceParameter.getPlayerChoiceMode().getStatusTitle(), this.fDialogPlayerChoiceParameter.getPlayerChoiceMode().getStatusMessage(), StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.PLAYER_CHOICE)) {
            DialogPlayerChoice playerChoiceDialog = (DialogPlayerChoice)pDialog;
            this.getClient().getCommunication().sendPlayerChoice(this.fDialogPlayerChoiceParameter.getPlayerChoiceMode(), playerChoiceDialog.getSelectedPlayers());
        }
    }
}

