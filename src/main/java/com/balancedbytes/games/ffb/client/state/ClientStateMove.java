/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.MoveSquare;
import com.balancedbytes.games.ffb.PathFinderWithPassBlockSupport;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class ClientStateMove
extends ClientState {
    protected ClientStateMove(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.MOVE;
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        super.mouseOverField(pCoordinate);
        Game game = this.getClient().getGame();
        FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
        fieldComponent.getLayerUnderPlayers().clearMovePath();
        ActingPlayer actingPlayer = game.getActingPlayer();
        MoveSquare moveSquare = game.getFieldModel().getMoveSquare(pCoordinate);
        if (moveSquare != null) {
            this.setCustomCursor(moveSquare);
        } else {
            Object[] shortestPath;
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
            String automoveProperty = this.getClient().getProperty("setting.automove");
            if (actingPlayer != null && actingPlayer.getPlayerAction() != null && actingPlayer.getPlayerAction().isMoving() && ArrayTool.isProvided(game.getFieldModel().getMoveSquares()) && !"automoveOff".equals(automoveProperty) && game.getTurnMode() != TurnMode.PASS_BLOCK && game.getTurnMode() != TurnMode.KICKOFF_RETURN && !UtilCards.hasSkill(game, actingPlayer, Skill.BALL_AND_CHAIN) && ArrayTool.isProvided(shortestPath = PathFinderWithPassBlockSupport.getShortestPath(game, pCoordinate))) {
                fieldComponent.getLayerUnderPlayers().drawMovePath((FieldCoordinate[])shortestPath, actingPlayer.getCurrentMove());
                fieldComponent.refresh();
            }
        }
        return super.mouseOverField(pCoordinate);
    }

    private void setCustomCursor(MoveSquare pMoveSquare) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pMoveSquare.isGoingForIt() && pMoveSquare.isDodging() && !actingPlayer.isLeaping()) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.gfidodge");
        } else if (pMoveSquare.isGoingForIt()) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.gfi");
        } else if (pMoveSquare.isDodging() && !actingPlayer.isLeaping()) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.dodge");
        } else {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.move");
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        MoveSquare moveSquare = game.getFieldModel().getMoveSquare(playerCoordinate);
        if (moveSquare != null) {
            this.setCustomCursor(moveSquare);
        } else {
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
            FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
            if (fieldComponent.getLayerUnderPlayers().clearMovePath()) {
                fieldComponent.refresh();
            }
        }
        return super.mouseOverPlayer(pPlayer);
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
        MoveSquare moveSquare = game.getFieldModel().getMoveSquare(pCoordinate);
        Object[] movePath = fieldComponent.getLayerUnderPlayers().getMovePath();
        if (ArrayTool.isProvided(movePath) || moveSquare != null) {
            if (ArrayTool.isProvided(movePath)) {
                if (fieldComponent.getLayerUnderPlayers().clearMovePath()) {
                    fieldComponent.refresh();
                }
                this.movePlayer((FieldCoordinate[])movePath);
            } else {
                this.movePlayer(pCoordinate);
            }
        }
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pPlayer == actingPlayer.getPlayer()) {
            if (actingPlayer.hasActed() || UtilCards.hasSkill(game, pPlayer, Skill.LEAP) || UtilCards.hasSkill(game, pPlayer, Skill.HYPNOTIC_GAZE) || actingPlayer.getPlayerAction() == PlayerAction.PASS_MOVE && UtilPlayer.hasBall(game, pPlayer) || actingPlayer.getPlayerAction() == PlayerAction.HAND_OVER_MOVE && UtilPlayer.hasBall(game, pPlayer) || actingPlayer.getPlayerAction() == PlayerAction.THROW_TEAM_MATE_MOVE || actingPlayer.getPlayerAction() == PlayerAction.THROW_TEAM_MATE) {
                this.createAndShowPopupMenuForActingPlayer();
            } else {
                this.getClient().getCommunication().sendActingPlayer(null, null, false);
            }
        } else {
            FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            MoveSquare moveSquare = game.getFieldModel().getMoveSquare(playerCoordinate);
            if (moveSquare != null) {
                this.movePlayer(playerCoordinate);
            }
        }
    }

    @Override
    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
        if (pPlayer != null) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            ClientCommunication communication = this.getClient().getCommunication();
            switch (pMenuKey) {
                case 69: {
                    if (!this.isEndPlayerActionAvailable()) break;
                    communication.sendActingPlayer(null, null, false);
                    break;
                }
                case 76: {
                    if (!UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) || !UtilPlayer.isNextMovePossible(game, false)) break;
                    communication.sendActingPlayer(pPlayer, actingPlayer.getPlayerAction(), !actingPlayer.isLeaping());
                    break;
                }
                case 72: {
                    if (PlayerAction.HAND_OVER_MOVE != actingPlayer.getPlayerAction() || !UtilPlayer.hasBall(game, actingPlayer.getPlayer())) break;
                    communication.sendActingPlayer(pPlayer, PlayerAction.HAND_OVER, actingPlayer.isLeaping());
                    break;
                }
                case 80: {
                    if (PlayerAction.PASS_MOVE != actingPlayer.getPlayerAction() || !UtilPlayer.hasBall(game, actingPlayer.getPlayer())) break;
                    communication.sendActingPlayer(pPlayer, PlayerAction.PASS, actingPlayer.isLeaping());
                    break;
                }
                case 84: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.THROW_TEAM_MATE, actingPlayer.isLeaping());
                    break;
                }
                case 77: {
                    if (PlayerAction.GAZE == actingPlayer.getPlayerAction()) {
                        communication.sendActingPlayer(pPlayer, PlayerAction.MOVE, actingPlayer.isLeaping());
                    }
                    if (PlayerAction.PASS == actingPlayer.getPlayerAction()) {
                        communication.sendActingPlayer(pPlayer, PlayerAction.PASS_MOVE, actingPlayer.isLeaping());
                    }
                    if (PlayerAction.THROW_TEAM_MATE != actingPlayer.getPlayerAction()) break;
                    communication.sendActingPlayer(pPlayer, PlayerAction.THROW_TEAM_MATE_MOVE, actingPlayer.isLeaping());
                    break;
                }
                case 71: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.GAZE, actingPlayer.isLeaping());
                }
            }
        }
    }

    protected void createAndShowPopupMenuForActingPlayer() {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        IconCache iconCache = userInterface.getIconCache();
        userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (PlayerAction.PASS_MOVE == actingPlayer.getPlayerAction() && UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
            JMenuItem passAction = new JMenuItem("Pass Ball (any square)", new ImageIcon(iconCache.getIconByProperty("action.pass")));
            passAction.setMnemonic(80);
            passAction.setAccelerator(KeyStroke.getKeyStroke(80, 0));
            menuItemList.add(passAction);
        }
        if (PlayerAction.PASS_MOVE == actingPlayer.getPlayerAction() && UtilPlayer.hasBall(game, actingPlayer.getPlayer()) || PlayerAction.THROW_TEAM_MATE_MOVE == actingPlayer.getPlayerAction() && UtilPlayer.canThrowTeamMate(game, actingPlayer.getPlayer(), true)) {
            JMenuItem toggleRangeGridAction = new JMenuItem("Range Grid on/off", new ImageIcon(iconCache.getIconByProperty("action.toggle.rangeGrid")));
            toggleRangeGridAction.setMnemonic(82);
            toggleRangeGridAction.setAccelerator(KeyStroke.getKeyStroke(82, 0));
            menuItemList.add(toggleRangeGridAction);
        }
        if (PlayerAction.GAZE == actingPlayer.getPlayerAction()) {
            JMenuItem moveAction = new JMenuItem("Move", new ImageIcon(iconCache.getIconByProperty("action.move")));
            moveAction.setMnemonic(77);
            moveAction.setAccelerator(KeyStroke.getKeyStroke(77, 0));
            menuItemList.add(moveAction);
        }
        if (UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) && UtilPlayer.isNextMovePossible(game, true)) {
            JMenuItem leapAction;
            if (actingPlayer.isLeaping()) {
                leapAction = new JMenuItem("Don't Leap", new ImageIcon(iconCache.getIconByProperty("action.move")));
                leapAction.setMnemonic(76);
                leapAction.setAccelerator(KeyStroke.getKeyStroke(76, 0));
                menuItemList.add(leapAction);
            } else {
                leapAction = new JMenuItem("Leap", new ImageIcon(iconCache.getIconByProperty("action.leap")));
                leapAction.setMnemonic(76);
                leapAction.setAccelerator(KeyStroke.getKeyStroke(76, 0));
                menuItemList.add(leapAction);
            }
        }
        if (this.isHypnoticGazeActionAvailable()) {
            JMenuItem hypnoticGazeAction = new JMenuItem("Hypnotic Gaze", new ImageIcon(iconCache.getIconByProperty("action.gaze")));
            hypnoticGazeAction.setMnemonic(71);
            hypnoticGazeAction.setAccelerator(KeyStroke.getKeyStroke(71, 0));
            menuItemList.add(hypnoticGazeAction);
        }
        if (this.isEndPlayerActionAvailable()) {
            String endMoveActionLabel = actingPlayer.hasActed() ? "End Move" : "Deselect Player";
            JMenuItem endMoveAction = new JMenuItem(endMoveActionLabel, new ImageIcon(iconCache.getIconByProperty("action.end")));
            endMoveAction.setMnemonic(69);
            endMoveAction.setAccelerator(KeyStroke.getKeyStroke(69, 0));
            menuItemList.add(endMoveAction);
        }
        this.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
        this.showPopupMenuForPlayer(actingPlayer.getPlayer());
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled;
        actionHandled = true;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        FieldCoordinate playerPosition = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        FieldCoordinate moveCoordinate = UtilClientActionKeys.findMoveCoordinate(this.getClient(), playerPosition, pActionKey);
        if (moveCoordinate != null) {
            MoveSquare[] moveSquares;
            for (MoveSquare moveSquare : moveSquares = game.getFieldModel().getMoveSquares()) {
                if (!moveSquare.getCoordinate().equals(moveCoordinate)) continue;
                this.movePlayer(moveCoordinate);
                break;
            }
        } else {
            switch (pActionKey) {
                case PLAYER_SELECT: {
                    this.createAndShowPopupMenuForActingPlayer();
                    break;
                }
                case PLAYER_ACTION_HAND_OVER: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 72);
                    break;
                }
                case PLAYER_ACTION_PASS: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 80);
                    break;
                }
                case PLAYER_ACTION_LEAP: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 76);
                    break;
                }
                case PLAYER_ACTION_END_MOVE: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 69);
                    break;
                }
                case PLAYER_ACTION_GAZE: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 71);
                    break;
                }
                default: {
                    actionHandled = false;
                }
            }
        }
        return actionHandled;
    }

    @Override
    public void endTurn() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        this.menuItemSelected(actingPlayer.getPlayer(), 69);
        this.getClient().getCommunication().sendEndTurn();
        this.getClient().getClientData().setEndTurnButtonHidden(true);
    }

    protected void movePlayer(FieldCoordinate pCoordinate) {
        if (pCoordinate == null) {
            return;
        }
        this.movePlayer(new FieldCoordinate[]{pCoordinate});
    }

    protected void movePlayer(FieldCoordinate[] pCoordinates) {
        if (!ArrayTool.isProvided(pCoordinates)) {
            return;
        }
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        FieldCoordinate coordinateFrom = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        if (coordinateFrom == null) {
            return;
        }
        this.getClient().getGame().getFieldModel().clearMoveSquares();
        this.getClient().getUserInterface().getFieldComponent().refresh();
        this.getClient().getCommunication().sendPlayerMove(actingPlayer.getPlayerId(), coordinateFrom, pCoordinates);
    }

    private boolean isEndPlayerActionAvailable() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        return !actingPlayer.hasActed() || !UtilCards.hasSkill(game, actingPlayer, Skill.BALL_AND_CHAIN) || actingPlayer.getCurrentMove() >= UtilCards.getPlayerMovement(game, actingPlayer.getPlayer());
    }

    private boolean isHypnoticGazeActionAvailable() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        return actingPlayer.getPlayerAction() == PlayerAction.MOVE && UtilPlayer.canGaze(game, actingPlayer.getPlayer());
    }

}

