/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeRuler;
import com.balancedbytes.games.ffb.client.layer.FieldLayerUnderPlayers;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientStateMove;
import com.balancedbytes.games.ffb.client.state.RangeGridHandler;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import com.balancedbytes.games.ffb.util.UtilRangeRuler;
import java.awt.Color;

public class ClientStateThrowTeamMate
extends ClientStateMove {
    private boolean fShowRangeRuler;
    private RangeGridHandler fRangeGridHandler;

    protected ClientStateThrowTeamMate(FantasyFootballClient pClient) {
        super(pClient);
        this.fRangeGridHandler = new RangeGridHandler(pClient, true);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.THROW_TEAM_MATE;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.markThrowablePlayers();
        this.fRangeGridHandler.refreshSettings();
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (pPlayer == actingPlayer.getPlayer()) {
            super.clickOnPlayer(pPlayer);
        } else {
            if (game.getDefender() == null && this.canBeThrown(pPlayer)) {
                this.fShowRangeRuler = true;
                this.getClient().getCommunication().sendThrowTeamMate(actingPlayer.getPlayerId(), pPlayer.getId());
            }
            if (game.getDefender() != null) {
                this.fShowRangeRuler = false;
                this.markThrowablePlayers();
                game.getFieldModel().setRangeRuler(null);
                userInterface.getFieldComponent().refresh();
                this.getClient().getCommunication().sendThrowTeamMate(actingPlayer.getPlayerId(), game.getFieldModel().getPlayerCoordinate(pPlayer));
            }
        }
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = this.getClient().getGame().getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (actingPlayer.getPlayerAction() == PlayerAction.THROW_TEAM_MATE_MOVE) {
            super.clickOnField(pCoordinate);
        } else {
            this.fShowRangeRuler = false;
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().refresh();
            this.getClient().getCommunication().sendThrowTeamMate(actingPlayer.getPlayerId(), pCoordinate);
        }
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        if (game.getDefender() != null && game.getPassCoordinate() == null) {
            this.drawRangeRuler(pCoordinate);
        }
        return super.mouseOverField(pCoordinate);
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (game.getDefender() == null && game.getPassCoordinate() == null) {
            if (this.canBeThrown(pPlayer)) {
                UtilClientCursor.setCustomCursor(userInterface, "cursor.pass");
            } else {
                UtilClientCursor.setDefaultCursor(userInterface);
            }
        }
        if (game.getDefender() != null && game.getPassCoordinate() == null) {
            this.drawRangeRuler(game.getFieldModel().getPlayerCoordinate(pPlayer));
        }
        this.getClient().getClientData().setSelectedPlayer(pPlayer);
        userInterface.refreshSideBars();
        return true;
    }

    private boolean drawRangeRuler(FieldCoordinate pCoordinate) {
        RangeRuler rangeRuler = null;
        if (this.fShowRangeRuler) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            UserInterface userInterface = this.getClient().getUserInterface();
            FieldComponent fieldComponent = userInterface.getFieldComponent();
            if (actingPlayer.getPlayerAction() == PlayerAction.THROW_TEAM_MATE) {
                rangeRuler = UtilRangeRuler.createRangeRuler(game, actingPlayer.getPlayer(), pCoordinate, true);
            }
            game.getFieldModel().setRangeRuler(rangeRuler);
            if (rangeRuler != null) {
                UtilClientCursor.setCustomCursor(userInterface, "cursor.pass");
            } else {
                UtilClientCursor.setDefaultCursor(userInterface);
            }
            fieldComponent.getLayerUnderPlayers().clearMovePath();
            fieldComponent.refresh();
        }
        return rangeRuler != null;
    }

    private boolean canBeThrown(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        PlayerState catcherState = game.getFieldModel().getPlayerState(pPlayer);
        FieldCoordinate throwerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        FieldCoordinate catcherCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        return UtilCards.hasSkill(game, actingPlayer, Skill.THROW_TEAM_MATE) && UtilCards.hasSkill(game, pPlayer, Skill.RIGHT_STUFF) && catcherState.hasTacklezones() && catcherCoordinate.isAdjacent(throwerCoordinate) && actingPlayer.getPlayer().getTeam() == pPlayer.getTeam();
    }

    private void markThrowablePlayers() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        Object[] throwablePlayers = UtilPlayer.findThrowableTeamMates(game, actingPlayer.getPlayer());
        if (game.getDefender() == null && ArrayTool.isProvided(throwablePlayers)) {
            userInterface.getFieldComponent().getLayerRangeRuler().markPlayers((Player[])throwablePlayers, FieldLayerRangeRuler.COLOR_THROWABLE_PLAYER);
        } else {
            userInterface.getFieldComponent().getLayerRangeRuler().clearMarkedCoordinates();
        }
        userInterface.getFieldComponent().refresh();
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
        this.fRangeGridHandler.refreshRangeGrid();
        super.handleCommand(pNetCommand);
    }

    @Override
    public void leaveState() {
        this.fRangeGridHandler.setShowRangeGrid(false);
        this.fRangeGridHandler.refreshRangeGrid();
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.getFieldComponent().getLayerRangeRuler().clearMarkedCoordinates();
        userInterface.getFieldComponent().refresh();
    }

    @Override
    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
        if (pMenuKey == 82) {
            this.fRangeGridHandler.setShowRangeGrid(!this.fRangeGridHandler.isShowRangeGrid());
            this.fRangeGridHandler.refreshRangeGrid();
        } else {
            super.menuItemSelected(pPlayer, pMenuKey);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        if (pActionKey == ActionKey.PLAYER_ACTION_RANGE_GRID) {
            this.menuItemSelected(null, 82);
            return true;
        }
        return super.actionKeyPressed(pActionKey);
    }
}

