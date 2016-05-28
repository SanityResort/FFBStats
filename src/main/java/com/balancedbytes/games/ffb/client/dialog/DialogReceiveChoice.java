/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogReceiveChoice
extends DialogYesOrNoQuestion {
    public DialogReceiveChoice(FantasyFootballClient pClient) {
        super(pClient, "Kick or receive", new String[]{"Do you want to receive the kickoff ?"}, "game.ref");
    }

    @Override
    public DialogId getId() {
        return DialogId.RECEIVE_CHOICE;
    }
}

