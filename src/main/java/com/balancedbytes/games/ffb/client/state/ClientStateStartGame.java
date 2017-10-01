/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.BoxType;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.ui.BoxComponent;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;

public class ClientStateStartGame
extends ClientState {
    protected ClientStateStartGame(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.START_GAME;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(false);
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.getSideBarAway().openBox(BoxType.RESERVES);
    }

    @Override
    public void leaveState() {
        this.closeAwayBox();
    }

    private void closeAwayBox() {
        UserInterface userInterface = this.getClient().getUserInterface();
        if (BoxType.RESERVES == userInterface.getSideBarAway().getBoxComponent().getOpenBox()) {
            userInterface.getSideBarAway().closeBox();
        }
    }
}

