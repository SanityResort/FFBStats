/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.Player;

public class ClientStateKickoff
extends ClientState {
    private boolean fKicked;

    protected ClientStateKickoff(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.KICKOFF;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.fKicked = false;
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        if (!this.fKicked) {
            this.placeBall(pCoordinate);
        }
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        if (!this.fKicked) {
            FieldCoordinate playerCoordinate = this.getClient().getGame().getFieldModel().getPlayerCoordinate(pPlayer);
            this.placeBall(playerCoordinate);
        }
    }

    private void placeBall(FieldCoordinate pCoordinate) {
        if (pCoordinate != null && FieldCoordinateBounds.HALF_AWAY.isInBounds(pCoordinate)) {
            this.getClient().getGame().getFieldModel().setBallMoving(true);
            this.getClient().getGame().getFieldModel().setBallCoordinate(pCoordinate);
            this.getClient().getUserInterface().getFieldComponent().refresh();
        }
    }

    @Override
    public void endTurn() {
        FieldCoordinate ballCoordinate = this.getClient().getGame().getFieldModel().getBallCoordinate();
        if (ballCoordinate != null && FieldCoordinateBounds.HALF_AWAY.isInBounds(ballCoordinate)) {
            this.fKicked = true;
            this.getClient().getCommunication().sendKickoff(ballCoordinate);
            this.getClient().getClientData().setEndTurnButtonHidden(true);
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        }
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        super.mouseOverField(pCoordinate);
        if (!this.fKicked && pCoordinate != null && FieldCoordinateBounds.HALF_AWAY.isInBounds(pCoordinate)) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.pass");
        } else {
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        }
        return true;
    }
}

