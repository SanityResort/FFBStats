/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.event.ActionListener;

public class DialogConcedeGame
extends DialogYesOrNoQuestion
implements ActionListener {
    public DialogConcedeGame(FantasyFootballClient pClient, boolean pLegalConcession) {
        super(pClient, "Concede Game", DialogConcedeGame.createMessages(pLegalConcession), "game.ref");
    }

    @Override
    public DialogId getId() {
        return DialogId.CONCEDE_GAME;
    }

    private static String[] createMessages(boolean pLegalConcession) {
        String[] messages = null;
        messages = pLegalConcession ? new String[]{"Do you want to concede this game?", "The concession will have no negative consequences at this point."} : new String[]{"Do you want to concede this game?", "Your fan factor will decrease by 1.", "You will lose your player award and all your winnings.", "Some valuable players (SPP 51+) may decide to leave your team."};
        return messages;
    }
}

