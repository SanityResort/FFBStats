/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeGrid;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPlayer;

public class RangeGridHandler {
    private FantasyFootballClient fClient;
    private boolean fShowRangeGrid;
    private boolean fThrowTeamMate;

    public RangeGridHandler(FantasyFootballClient pClient, boolean pThrowTeamMate) {
        this.fClient = pClient;
        this.fThrowTeamMate = pThrowTeamMate;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public void refreshRangeGrid() {
        boolean gridDrawn = false;
        UserInterface userInterface = this.getClient().getUserInterface();
        if (this.fShowRangeGrid) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            if (!this.fThrowTeamMate && UtilPlayer.hasBall(game, actingPlayer.getPlayer()) || this.fThrowTeamMate && actingPlayer.getPlayerAction() == PlayerAction.THROW_TEAM_MATE || actingPlayer.getPlayerAction() == PlayerAction.THROW_BOMB) {
                FieldCoordinate actingPlayerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
                if (userInterface.getFieldComponent().getLayerRangeGrid().drawRangeGrid(actingPlayerCoordinate, this.fThrowTeamMate)) {
                    userInterface.getFieldComponent().refresh();
                }
                gridDrawn = true;
            }
        }
        if (!gridDrawn && userInterface.getFieldComponent().getLayerRangeGrid().clearRangeGrid()) {
            userInterface.getFieldComponent().refresh();
        }
    }

    public void refreshSettings() {
        String rangeGridSettingProperty = this.getClient().getProperty("setting.rangegrid");
        if (!this.fShowRangeGrid && "rangegridAlwaysOn".equals(rangeGridSettingProperty)) {
            this.setShowRangeGrid(true);
            this.refreshRangeGrid();
        }
    }

    public boolean isShowRangeGrid() {
        return this.fShowRangeGrid;
    }

    public void setShowRangeGrid(boolean pShowRangeGrid) {
        this.fShowRangeGrid = pShowRangeGrid;
    }
}

