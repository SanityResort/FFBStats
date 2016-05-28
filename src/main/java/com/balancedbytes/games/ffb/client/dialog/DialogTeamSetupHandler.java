/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.TeamSetup;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.DialogTeamSetup;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogTeamSetupParameter;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;

public class DialogTeamSetupHandler
extends DialogHandler {
    public DialogTeamSetupHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogTeamSetupParameter dialogTeamSetupParameter = (DialogTeamSetupParameter)game.getDialogParameter();
        if (dialogTeamSetupParameter != null) {
            Object[] setupNames = dialogTeamSetupParameter.getSetupNames();
            if (ArrayTool.isProvided(setupNames) || !dialogTeamSetupParameter.isLoadDialog()) {
                this.setDialog(new DialogTeamSetup(this.getClient(), dialogTeamSetupParameter.isLoadDialog(), (String[])setupNames));
            } else {
                this.setDialog(new DialogInformation(this.getClient(), "No setups", "There are no setups available for this team.", 1));
            }
            this.getDialog().showDialog(this);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.TEAM_SETUP)) {
            Game game;
            DialogTeamSetup teamSetupDialog = (DialogTeamSetup)pDialog;
            if (teamSetupDialog.getUserChoice() == 1) {
                this.getClient().getCommunication().sendTeamSetupLoad(teamSetupDialog.getSetupName());
            }
            if (teamSetupDialog.getUserChoice() == 4) {
                this.getClient().getCommunication().sendTeamSetupDelete(teamSetupDialog.getSetupName());
            }
            if (teamSetupDialog.getUserChoice() == 2) {
                game = this.getClient().getGame();
                TeamSetup teamSetup = new TeamSetup();
                teamSetup.setName(teamSetupDialog.getSetupName());
                teamSetup.setTeamId(game.getTeamHome().getId());
                Player[] homePlayers = game.getTeamHome().getPlayers();
                for (int i = 0; i < homePlayers.length; ++i) {
                    FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(homePlayers[i]);
                    if (!FieldCoordinateBounds.HALF_HOME.isInBounds(playerCoordinate)) continue;
                    teamSetup.addCoordinate(playerCoordinate, homePlayers[i].getNr());
                }
                this.getClient().getCommunication().sendTeamSetupSave(teamSetup);
            }
            game = this.getClient().getGame();
            game.setDialogParameter(null);
        }
    }
}

