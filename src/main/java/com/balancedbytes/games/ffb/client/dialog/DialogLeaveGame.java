/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogLeaveGame
extends DialogYesOrNoQuestion {
    public DialogLeaveGame(FantasyFootballClient pClient) {
        super(pClient, "Leave Game", new String[]{"Do you really want to leave the game?"}, null);
    }

    @Override
    public DialogId getId() {
        return DialogId.LEAVE_GAME;
    }
}

