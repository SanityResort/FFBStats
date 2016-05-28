/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.state.ClientStateSetup;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import java.util.HashSet;
import java.util.Set;

public class ClientStateIllegalSubstitution
extends ClientStateSetup {
    private Set<Player> fFieldPlayers;

    protected ClientStateIllegalSubstitution(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.ILLEGAL_SUBSTITUTION;
    }

    @Override
    public void enterState() {
        super.enterState();
        Game game = this.getClient().getGame();
        this.fFieldPlayers = new HashSet<Player>();
        for (Player player : game.getTeamHome().getPlayers()) {
            if (game.getFieldModel().getPlayerCoordinate(player).isBoxCoordinate()) continue;
            this.fFieldPlayers.add(player);
        }
    }

    @Override
    public boolean isInitDragAllowed(FieldCoordinate pCoordinate) {
        Game game;
        Player draggedPlayer;
        if (pCoordinate != null && (draggedPlayer = (game = this.getClient().getGame()).getFieldModel().getPlayer(pCoordinate)) != null) {
            if (pCoordinate.isBoxCoordinate()) {
                for (FieldCoordinate coordinate : FieldCoordinateBounds.ENDZONE_HOME.fieldCoordinates()) {
                    Player player = game.getFieldModel().getPlayer(coordinate);
                    if (player == null || !game.getTeamHome().hasPlayer(player) || this.fFieldPlayers.contains(player)) continue;
                    return false;
                }
                return true;
            }
            return !this.fFieldPlayers.contains(draggedPlayer);
        }
        return false;
    }

    @Override
    public boolean isDragAllowed(FieldCoordinate pCoordinate) {
        if (pCoordinate == null) {
            return false;
        }
        Game game = this.getClient().getGame();
        ClientData clientData = this.getClient().getClientData();
        return clientData.getDragStartPosition() != null && game.getFieldModel().getPlayer(pCoordinate) == null && (pCoordinate.isBoxCoordinate() || FieldCoordinateBounds.ENDZONE_HOME.isInBounds(pCoordinate));
    }

    @Override
    public boolean isDropAllowed(FieldCoordinate pCoordinate) {
        if (pCoordinate == null) {
            return false;
        }
        return pCoordinate.isBoxCoordinate() || FieldCoordinateBounds.ENDZONE_HOME.isInBounds(pCoordinate);
    }
}

