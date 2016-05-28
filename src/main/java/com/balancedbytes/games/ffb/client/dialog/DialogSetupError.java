/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogSetupError
extends DialogInformation {
    public DialogSetupError(FantasyFootballClient pClient, String[] pSetupErrors) {
        super(pClient, "Setup-Error", pSetupErrors, 1, "game.ref");
    }

    @Override
    public DialogId getId() {
        return DialogId.SETUP_ERROR;
    }
}

