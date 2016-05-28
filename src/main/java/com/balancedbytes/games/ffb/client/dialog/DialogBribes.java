/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Player;

public class DialogBribes
extends DialogYesOrNoQuestion {
    public DialogBribes(FantasyFootballClient pClient, Player pPlayer) {
        super(pClient, "Use a bribe", DialogBribes.createMessages(pPlayer), null);
    }

    @Override
    public DialogId getId() {
        return DialogId.BRIBES;
    }

    private static String[] createMessages(Player pPlayer) {
        String[] messages;
        if (pPlayer != null) {
            messages = new String[2];
            StringBuilder message = new StringBuilder();
            message.append("On a roll of 2+ he will refrain from ejecting ").append(pPlayer.getName()).append(".");
            messages[1] = message.toString();
        } else {
            messages = new String[]{"Do you want to bribe the ref?"};
        }
        return messages;
    }
}

