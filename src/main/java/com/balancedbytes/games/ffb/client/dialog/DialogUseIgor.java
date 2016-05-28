/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseIgorParameter;

public class DialogUseIgor
extends DialogYesOrNoQuestion {
    private DialogUseIgorParameter fDialogParameter;

    public DialogUseIgor(FantasyFootballClient pClient, DialogUseIgorParameter pDialogParameter) {
        super(pClient, "Use Igor", DialogUseIgor.createMessages(pClient, pDialogParameter), "resource.igor");
        this.fDialogParameter = pDialogParameter;
    }

    @Override
    public DialogId getId() {
        return DialogId.USE_IGOR;
    }

    public String getPlayerId() {
        return this.fDialogParameter.getPlayerId();
    }

    private static String[] createMessages(FantasyFootballClient pClient, DialogUseIgorParameter pDialogParameter) {
        String[] messages = new String[]{"Do you want to use your Igor?", "Using the Igor will re-roll the failed Regeneration."};
        return messages;
    }
}

