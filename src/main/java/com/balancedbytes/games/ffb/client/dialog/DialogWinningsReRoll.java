/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.event.ActionListener;

public class DialogWinningsReRoll
extends DialogYesOrNoQuestion
implements ActionListener {
    public DialogWinningsReRoll(FantasyFootballClient pClient, int pOldRoll) {
        super(pClient, "Re-roll Winnings", DialogWinningsReRoll.createMessages(pOldRoll), "game.dice.small", "Keep", 75, "Re-Roll", 82);
    }

    @Override
    public DialogId getId() {
        return DialogId.WINNINGS_RE_ROLL;
    }

    private static String[] createMessages(int pOldRoll) {
        String[] messages = new String[3];
        messages[0] = "Do you want to keep your winnings?";
        messages[1] = "The current roll is " + pOldRoll + ".";
        messages[2] = pOldRoll < 6 ? "If you re-roll you must keep the new result." : "Rolled maximum. If you re-roll this it can only get worse.";
        return messages;
    }
}

