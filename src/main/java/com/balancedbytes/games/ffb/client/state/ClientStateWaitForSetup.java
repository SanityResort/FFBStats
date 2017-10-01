/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.model.Game;

public class ClientStateWaitForSetup
extends ClientState {
    private boolean fReservesBoxOpened;

    protected ClientStateWaitForSetup(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.WAIT_FOR_SETUP;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(false);
        Game game = this.getClient().getGame();
        boolean bl = this.fReservesBoxOpened = game.getTurnMode() == TurnMode.SETUP ;
        if (this.fReservesBoxOpened) {
        }
    }

    @Override
    public void leaveState() {
    }
}

