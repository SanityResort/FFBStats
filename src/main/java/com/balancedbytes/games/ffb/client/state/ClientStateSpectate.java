/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.model.Game;

public class ClientStateSpectate
extends ClientState {
    protected ClientStateSpectate(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.SPECTATE;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(false);
        Game game = this.getClient().getGame();
        if (game.getFinished() != null && ClientMode.PLAYER == this.getClient().getMode()) {
            this.getClient().setMode(ClientMode.SPECTATOR);
            UserInterface userInterface = this.getClient().getUserInterface();
            GameTitle gameTitle = userInterface.getGameTitle();
            gameTitle.setClientMode(ClientMode.SPECTATOR);
            userInterface.setGameTitle(gameTitle);
            userInterface.getGameMenuBar().refresh();
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        if (pActionKey == ActionKey.MENU_REPLAY) {
            actionHandled = true;
            this.getClient().getReplayer().start();
            this.getClient().getUserInterface().getGameMenuBar().refresh();
            this.getClient().updateClientState();
        }
        return actionHandled;
    }
}

