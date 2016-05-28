/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.model.Game;

public class DialogPassBlockHandler
extends DialogHandler {
    public DialogPassBlockHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER != this.getClient().getMode() || !game.isHomePlaying()) {
            this.showStatus("Pass Block", "Waiting for coach to move pass blockers.", StatusType.WAITING);
        } else {
            this.setDialog(new DialogInformation(this.getClient(), "Pass Block", new String[]{"You may move your players with PASS BLOCK skill up to 3 squares.", "The move must end in a square where the player can intercept or put a TZ on thrower or catcher."}, 1, "game.ref"));
            this.getDialog().showDialog(this);
            this.playSound(SoundId.QUESTION);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
    }
}

