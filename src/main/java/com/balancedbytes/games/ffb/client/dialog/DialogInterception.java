/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogInterception
extends DialogYesOrNoQuestion {
    public DialogInterception(FantasyFootballClient pClient) {
        super(pClient, "Interception", new String[]{"Do you want to try to intercept the pass?"}, "game.ref");
    }

    @Override
    public DialogId getId() {
        return DialogId.INTERCEPTION;
    }
}

