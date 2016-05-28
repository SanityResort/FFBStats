/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseApothecaryParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

public class DialogUseApothecary
extends DialogYesOrNoQuestion {
    private DialogUseApothecaryParameter fDialogParameter;

    public DialogUseApothecary(FantasyFootballClient pClient, DialogUseApothecaryParameter pDialogParameter) {
        super(pClient, "Use Apothecary", DialogUseApothecary.createMessages(pClient, pDialogParameter), "resource.apothecary");
        this.fDialogParameter = pDialogParameter;
    }

    @Override
    public DialogId getId() {
        return DialogId.USE_APOTHECARY;
    }

    public String getPlayerId() {
        return this.fDialogParameter.getPlayerId();
    }

    private static String[] createMessages(FantasyFootballClient pClient, DialogUseApothecaryParameter pDialogParameter) {
        String[] messages = new String[]{};
        if (pClient != null && pDialogParameter != null) {
            Game game = pClient.getGame();
            Player player = game.getPlayerById(pDialogParameter.getPlayerId());
            messages = new String[2];
            StringBuilder injuryMessage = new StringBuilder();
            injuryMessage.append(player.getName()).append(" ");
            if (pDialogParameter.getSeriousInjury() != null) {
                injuryMessage.append(pDialogParameter.getSeriousInjury().getDescription());
            } else {
                injuryMessage.append(pDialogParameter.getPlayerState().getDescription());
            }
            messages[0] = injuryMessage.toString();
            messages[1] = "Do you want to use your Apothecary?";
        }
        return messages;
    }
}

