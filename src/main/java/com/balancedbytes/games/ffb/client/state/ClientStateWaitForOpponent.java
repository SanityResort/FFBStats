/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;

public class ClientStateWaitForOpponent
extends ClientState {
    protected ClientStateWaitForOpponent(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.WAIT_FOR_OPPONENT;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(false);
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = true;
        switch (pActionKey) {
            case TOOLBAR_ILLEGAL_PROCEDURE: {
                this.getClient().getCommunication().sendIllegalProcedure();
                break;
            }
            default: {
                actionHandled = false;
            }
        }
        return actionHandled;
    }

}

