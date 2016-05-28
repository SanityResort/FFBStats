/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.BoxType;
import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogSetupError;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.dialog.DialogSetupErrorParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;

public class DialogSetupErrorHandler
extends DialogHandler {
    public DialogSetupErrorHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogSetupErrorParameter dialogSetupErrorParameter = (DialogSetupErrorParameter)game.getDialogParameter();
        if (dialogSetupErrorParameter != null && game.getTeamHome().getId().equals(dialogSetupErrorParameter.getTeamId()) && ClientMode.PLAYER == this.getClient().getMode()) {
            SideBarComponent sideBarHome = this.getClient().getUserInterface().getSideBarHome();
            sideBarHome.openBox(BoxType.RESERVES);
            this.setDialog(new DialogSetupError(this.getClient(), dialogSetupErrorParameter.getSetupErrors()));
            this.getDialog().showDialog(this);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
    }
}

