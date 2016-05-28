/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogFollowupChoice
extends DialogYesOrNoQuestion {
    public DialogFollowupChoice(FantasyFootballClient pClient) {
        super(pClient, "Followup Choice", "Follow up the block?");
    }

    @Override
    public DialogId getId() {
        return DialogId.FOLLOWUP_CHOICE;
    }
}

