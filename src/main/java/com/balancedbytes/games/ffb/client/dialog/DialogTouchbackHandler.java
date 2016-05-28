/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;

public class DialogTouchbackHandler
extends DialogHandler {
    private static final String _DIALOG_TITLE = "Touchback";

    public DialogTouchbackHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == this.getClient().getMode() && !game.isHomePlaying()) {
            this.setDialog(new DialogInformation(this.getClient(), "Touchback", new String[]{"You may give the ball to any member of your team."}, 1, null));
            this.getDialog().showDialog(this);
        } else {
            this.showStatus("Touchback", "Waiting for coach to give the ball to any member of the team.", StatusType.WAITING);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.INFORMATION)) {
            this.getClient().getClientState().setClickable(true);
        }
    }
}

