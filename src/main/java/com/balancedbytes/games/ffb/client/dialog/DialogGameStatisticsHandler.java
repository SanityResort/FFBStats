/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogGameStatistics;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;

public class DialogGameStatisticsHandler
extends DialogHandler {
    public DialogGameStatisticsHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        if (ClientMode.PLAYER == this.getClient().getMode()) {
            this.setDialog(new DialogGameStatistics(this.getClient()));
            this.getDialog().showDialog(this);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
    }
}

