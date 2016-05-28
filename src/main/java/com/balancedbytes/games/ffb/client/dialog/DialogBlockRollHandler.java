/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogBlockRoll;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogBlockRollParameter;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogBlockRollHandler
extends DialogHandler {
    private DialogBlockRollParameter fDialogParameter;

    public DialogBlockRollHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        ClientData clientData = this.getClient().getClientData();
        UserInterface userInterface = this.getClient().getUserInterface();
        this.fDialogParameter = (DialogBlockRollParameter)game.getDialogParameter();
        if (this.fDialogParameter != null) {
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().getId().equals(this.fDialogParameter.getChoosingTeamId())) {
                clientData.clearBlockDiceResult();
                this.setDialog(new DialogBlockRoll(this.getClient(), this.fDialogParameter));
                this.getDialog().showDialog(this);
                if (!game.isHomePlaying()) {
                    this.playSound(SoundId.QUESTION);
                }
            } else {
                clientData.setBlockDiceResult(this.fDialogParameter.getNrOfDice(), this.fDialogParameter.getBlockRoll(), -1);
                if (this.fDialogParameter.getNrOfDice() < 0 && game.isHomePlaying()) {
                    this.showStatus("Block Roll", "Waiting for coach to choose Block Dice.", StatusType.WAITING);
                }
            }
            userInterface.refreshSideBars();
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        Game game = this.getClient().getGame();
        ClientData clientData = this.getClient().getClientData();
        if (this.testDialogHasId(pDialog, DialogId.BLOCK_ROLL)) {
            UserInterface userInterface = this.getClient().getUserInterface();
            DialogBlockRoll blockRollDialog = (DialogBlockRoll)pDialog;
            ClientCommunication communication = this.getClient().getCommunication();
            if (game.getTeamHome().getId().equals(this.fDialogParameter.getChoosingTeamId())) {
                if (blockRollDialog.getDiceIndex() >= 0) {
                    clientData.setBlockDiceResult(this.fDialogParameter.getNrOfDice(), this.fDialogParameter.getBlockRoll(), blockRollDialog.getDiceIndex());
                    communication.sendBlockChoice(blockRollDialog.getDiceIndex());
                } else {
                    clientData.setBlockDiceResult(this.fDialogParameter.getNrOfDice(), this.fDialogParameter.getBlockRoll(), -1);
                    communication.sendUseReRoll(ReRolledAction.BLOCK, blockRollDialog.getReRollSource());
                }
                userInterface.refreshSideBars();
            }
        }
    }
}

