/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientStateMove;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilPlayer;

public class ClientStateGaze
extends ClientStateMove {
    protected ClientStateGaze(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.GAZE;
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pPlayer == actingPlayer.getPlayer()) {
            super.clickOnPlayer(pPlayer);
        } else if (this.canBeGazed(pPlayer)) {
            this.getClient().getCommunication().sendGaze(actingPlayer.getPlayerId(), pPlayer);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        FieldCoordinate playerPosition = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        FieldCoordinate victimPosition = UtilClientActionKeys.findMoveCoordinate(this.getClient(), playerPosition, pActionKey);
        Player victim = game.getFieldModel().getPlayer(victimPosition);
        if (victim != null) {
            actionHandled = this.canBeGazed(victim);
            if (actionHandled) {
                this.getClient().getCommunication().sendGaze(actingPlayer.getPlayerId(), victim);
            }
        } else {
            actionHandled = super.actionKeyPressed(pActionKey);
        }
        return actionHandled;
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        if (this.canBeGazed(pPlayer)) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.gaze");
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

    private boolean canBeGazed(Player pVictim) {
        boolean result = false;
        if (pVictim != null) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            FieldCoordinate actorCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
            FieldCoordinate victimCoordinate = game.getFieldModel().getPlayerCoordinate(pVictim);
            Team actorTeam = game.getTeamHome().hasPlayer(actingPlayer.getPlayer()) ? game.getTeamHome() : game.getTeamAway();
            Team victimTeam = game.getTeamHome().hasPlayer(pVictim) ? game.getTeamHome() : game.getTeamAway();
            result = UtilPlayer.canGaze(game, actingPlayer.getPlayer()) && victimCoordinate != null && victimCoordinate.isAdjacent(actorCoordinate) && actorTeam != victimTeam && game.getFieldModel().getPlayerState(pVictim).hasTacklezones();
        }
        return result;
    }
}

