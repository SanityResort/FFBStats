/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

public class ClientStateHighKick
extends ClientState {
    private FieldCoordinate fOldCoordinate;

    protected ClientStateHighKick(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.HIGH_KICK;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(true);
        this.fOldCoordinate = null;
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game;
        Player oldPlayer;
        if (this.isPlayerSelectable(pPlayer) && pPlayer != (oldPlayer = (game = this.getClient().getGame()).getFieldModel().getPlayer(game.getFieldModel().getBallCoordinate()))) {
            if (oldPlayer != null && this.fOldCoordinate != null) {
                this.getClient().getCommunication().sendSetupPlayer(oldPlayer, this.fOldCoordinate);
            }
            this.fOldCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            this.getClient().getCommunication().sendSetupPlayer(pPlayer, game.getFieldModel().getBallCoordinate());
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        if (this.isPlayerSelectable(pPlayer)) {
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

    private boolean isPlayerSelectable(Player pPlayer) {
        boolean selectable = false;
        if (pPlayer != null) {
            Game game = this.getClient().getGame();
            PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
            selectable = playerState != null && playerState.isActive() && game.getTeamHome().hasPlayer(pPlayer);
        }
        return selectable;
    }

    @Override
    public void endTurn() {
    }
}

