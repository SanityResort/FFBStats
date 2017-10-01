/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPassing;

public class ClientStateInterception
extends ClientState {
    protected ClientStateInterception(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.INTERCEPTION;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(true);
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        if (this.isInterceptor(pPlayer)) {
            this.getClient().getCommunication().sendInterceptorChoice(pPlayer);
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        if (this.isInterceptor(pPlayer)) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.pass");
        } else {
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        }
        return true;
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        super.mouseOverField(pCoordinate);
        UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        return true;
    }

    private boolean isInterceptor(Player pPlayer) {
        boolean isInterceptor = false;
        Game game = this.getClient().getGame();
        Player[] interceptors = UtilPassing.findInterceptors(game, game.getThrower(), game.getPassCoordinate());
        for (int i = 0; i < interceptors.length; ++i) {
            if (interceptors[i] != pPlayer) continue;
            isInterceptor = true;
            break;
        }
        return isInterceptor;
    }
}

