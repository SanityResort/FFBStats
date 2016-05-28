/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.PlayerGender;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogYesOrNoQuestion;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogPilingOnParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

public class DialogPilingOn
extends DialogYesOrNoQuestion {
    public DialogPilingOn(FantasyFootballClient pClient, DialogPilingOnParameter pDialogParameter) {
        super(pClient, "Use Piling On", DialogPilingOn.createMessages(pClient, pDialogParameter), null);
    }

    @Override
    public DialogId getId() {
        return DialogId.PILING_ON;
    }

    private static String[] createMessages(FantasyFootballClient pClient, DialogPilingOnParameter pDialogParameter) {
        Game game;
        Player player;
        String[] messages = new String[]{};
        if (pClient != null && pDialogParameter != null && (player = (game = pClient.getGame()).getPlayerById(pDialogParameter.getPlayerId())) != null) {
            messages = new String[3];
            StringBuilder line = new StringBuilder();
            line.append("Do you want ").append(player.getName()).append(" to use Piling On?");
            messages[0] = line.toString();
            line = new StringBuilder();
            line.append("Using Piling On will re-roll ").append(pDialogParameter.isReRollInjury() ? "Injury" : "Armor");
            line.append(" for ").append(player.getPlayerGender().getGenitive()).append(" opponent.");
            messages[1] = line.toString();
            line = new StringBuilder();
            line.append(player.getName()).append(" will be prone after using this skill.");
            messages[2] = line.toString();
        }
        return messages;
    }
}

