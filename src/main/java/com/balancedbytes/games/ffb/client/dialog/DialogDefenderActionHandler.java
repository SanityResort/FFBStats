/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import java.util.HashMap;
import java.util.Map;

public class DialogDefenderActionHandler
extends DialogHandler {
    private static final Map<PlayerAction, String> sTitleByAction = new HashMap<PlayerAction, String>();
    private static final Map<PlayerAction, String> sDescriptionByAction = new HashMap<PlayerAction, String>();

    public DialogDefenderActionHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Team team;
        Game game = this.getClient().getGame();
        Team team2 = team = game.getTeamHome().hasPlayer(game.getDefender()) ? game.getTeamHome() : game.getTeamAway();
        if (ClientMode.PLAYER != this.getClient().getMode() || team != game.getTeamHome()) {
            StringBuilder message = new StringBuilder();
            message.append("Waiting for coach to ");
            message.append(sDescriptionByAction.get(game.getDefenderAction())).append(".");
            this.showStatus(sTitleByAction.get(game.getDefenderAction()), message.toString(), StatusType.WAITING);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
    }

    static {
        sTitleByAction.put(PlayerAction.DUMP_OFF, "Dump Off");
        sDescriptionByAction.put(PlayerAction.DUMP_OFF, "dump off the ball");
    }
}

