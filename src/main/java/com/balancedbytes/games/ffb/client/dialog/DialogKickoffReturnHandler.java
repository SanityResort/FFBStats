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
import com.balancedbytes.games.ffb.model.Game;

public class DialogKickoffReturnHandler
extends DialogHandler {
    public DialogKickoffReturnHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == this.getClient().getMode() && game.isHomePlaying()) {
            this.setDialog(new DialogInformation(this.getClient(), "Use Kick-Off Return Skill", new String[]{"You may move a single player with this skill up to 3 squares within your own half."}, 1, "game.ref"));
            this.getDialog().showDialog(this);
        } else {
            this.showStatus("Skill Use", "Waiting for coach to use Kick-Off Return.", StatusType.WAITING);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
    }
}

