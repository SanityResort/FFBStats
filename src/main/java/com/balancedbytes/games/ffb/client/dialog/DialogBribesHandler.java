/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogBribes;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPlayerChoice;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogBribesParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;

public class DialogBribesHandler
extends DialogHandler {
    public DialogBribesHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogBribesParameter dialogBribesParameter = (DialogBribesParameter)game.getDialogParameter();
        if (dialogBribesParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(dialogBribesParameter.getTeamId())) {
                if (ArrayTool.isProvided(dialogBribesParameter.getPlayerIds()) && dialogBribesParameter.getPlayerIds().length == 1) {
                    Player player = game.getPlayerById(dialogBribesParameter.getPlayerIds()[0]);
                    this.setDialog(new DialogBribes(this.getClient(), player));
                } else {
                    StringBuilder header = new StringBuilder();
                    if (dialogBribesParameter.getMaxNrOfBribes() > 1) {
                        header.append("Select max. ").append(dialogBribesParameter.getMaxNrOfBribes()).append(" players to use a Bribe for");
                    } else {
                        header.append("Select a player to use a Bribe for");
                    }
                    this.setDialog(new DialogPlayerChoice(this.getClient(), header.toString(), dialogBribesParameter.getPlayerIds(), null, dialogBribesParameter.getMaxNrOfBribes(), null));
                }
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Use a bribe", "Waiting for coach to bribe the ref.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.BRIBES)) {
            DialogBribes bribesDialog = (DialogBribes)pDialog;
            Game game = this.getClient().getGame();
            DialogBribesParameter dialogBribesParameter = (DialogBribesParameter)game.getDialogParameter();
            String playerId = bribesDialog.isChoiceYes() ? dialogBribesParameter.getPlayerIds()[0] : null;
            this.getClient().getCommunication().sendUseInducement(InducementType.BRIBES, playerId);
        }
        if (this.testDialogHasId(pDialog, DialogId.PLAYER_CHOICE)) {
            DialogPlayerChoice playerChoiceDialog = (DialogPlayerChoice)pDialog;
            Player[] selectedPlayers = playerChoiceDialog.getSelectedPlayers();
            String[] selectedPlayerIds = new String[selectedPlayers.length];
            for (int i = 0; i < selectedPlayerIds.length; ++i) {
                selectedPlayerIds[i] = selectedPlayers[i].getId();
            }
            this.getClient().getCommunication().sendUseInducement(InducementType.BRIBES, selectedPlayerIds);
        }
    }
}

