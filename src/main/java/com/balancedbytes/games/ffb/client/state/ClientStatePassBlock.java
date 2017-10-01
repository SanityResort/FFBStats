/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.MoveSquare;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientStateMove;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPassing;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ClientStatePassBlock
extends ClientStateMove {
    private DialogInformation fInfoDialog;

    protected ClientStatePassBlock(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.PASS_BLOCK;
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        if (game.getTeamHome().hasPlayer(pPlayer) && playerState.isActive()) {
            this.createAndShowPopupMenuForPlayer(pPlayer);
        }
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        MoveSquare moveSquare = game.getFieldModel().getMoveSquare(pCoordinate);
        if (moveSquare != null) {
            this.movePlayer(pCoordinate);
        }
    }

    @Override
    public void menuItemSelected(Player pPlayer, int pMenuKey) {
        if (pPlayer != null) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            ClientCommunication communication = this.getClient().getCommunication();
            switch (pMenuKey) {
                case 76: {
                    if (actingPlayer.getPlayer() == null || !UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) || !UtilPlayer.isNextMovePossible(game, false)) break;
                    communication.sendActingPlayer(pPlayer, actingPlayer.getPlayerAction(), !actingPlayer.isLeaping());
                    break;
                }
                case 77: {
                    communication.sendActingPlayer(pPlayer, PlayerAction.MOVE, false);
                    break;
                }
                case 69: {
                    communication.sendActingPlayer(null, null, false);
                }
            }
        }
    }

    private void createAndShowPopupMenuForPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        IconCache iconCache = this.getClient().getUserInterface().getIconCache();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (actingPlayer.getPlayer() == null && playerState != null && playerState.isAbleToMove()) {
            JMenuItem moveAction = new JMenuItem("Move Action", new ImageIcon(iconCache.getIconByProperty("action.move")));
            moveAction.setMnemonic(77);
            moveAction.setAccelerator(KeyStroke.getKeyStroke(77, 0));
            menuItemList.add(moveAction);
        }
        if (actingPlayer.getPlayer() != null && UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) && UtilPlayer.isNextMovePossible(game, false)) {
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
        if (game.getActingPlayer().getPlayer() == pPlayer) {
            String endMoveActionLabel = null;
            if (!actingPlayer.hasActed()) {
                endMoveActionLabel = "Deselect Player";
            } else {
                Set<FieldCoordinate> validEndCoordinates = UtilPassing.findValidPassBlockEndCoordinates(game);
                if (validEndCoordinates.contains(game.getFieldModel().getPlayerCoordinate(pPlayer))) {
                    endMoveActionLabel = "End Move";
                }
            }
            if (endMoveActionLabel != null) {
                JMenuItem endMoveAction = new JMenuItem(endMoveActionLabel, new ImageIcon(iconCache.getIconByProperty("action.end")));
                endMoveAction.setMnemonic(69);
                endMoveAction.setAccelerator(KeyStroke.getKeyStroke(69, 0));
                menuItemList.add(endMoveAction);
            }
        }
        if (menuItemList.size() > 0) {
            this.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
            this.showPopupMenuForPlayer(pPlayer);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = true;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        Player selectedPlayer = this.getClient().getClientData().getSelectedPlayer();
        switch (pActionKey) {
            case PLAYER_SELECT: {
                if (selectedPlayer == null) break;
                this.createAndShowPopupMenuForPlayer(selectedPlayer);
                break;
            }
            case PLAYER_CYCLE_RIGHT: {
                selectedPlayer = UtilClientActionKeys.cyclePlayer(game, selectedPlayer, true);
                if (selectedPlayer == null) break;
                this.hideSelectSquare();
                FieldCoordinate selectedCoordinate = game.getFieldModel().getPlayerCoordinate(selectedPlayer);
                this.showSelectSquare(selectedCoordinate);
                this.getClient().getClientData().setSelectedPlayer(selectedPlayer);
                userInterface.refreshSideBars();
                break;
            }
            case PLAYER_CYCLE_LEFT: {
                selectedPlayer = UtilClientActionKeys.cyclePlayer(game, selectedPlayer, false);
                if (selectedPlayer == null) break;
                this.hideSelectSquare();
                FieldCoordinate selectedCoordinate = game.getFieldModel().getPlayerCoordinate(selectedPlayer);
                this.showSelectSquare(selectedCoordinate);
                this.getClient().getClientData().setSelectedPlayer(selectedPlayer);
                userInterface.refreshSideBars();
                break;
            }
            case PLAYER_ACTION_MOVE: {
                this.menuItemSelected(selectedPlayer, 77);
                break;
            }
            case PLAYER_ACTION_LEAP: {
                this.menuItemSelected(actingPlayer.getPlayer(), 76);
                break;
            }
            default: {
                actionHandled = false;
            }
        }
        return actionHandled;
    }

    @Override
    public void endTurn() {
        FieldCoordinate playerCoordinate;
        Set<FieldCoordinate> validEndCoordinates;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (actingPlayer.getPlayer() != null && !(validEndCoordinates = UtilPassing.findValidPassBlockEndCoordinates(game)).contains(playerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer()))) {
            this.fInfoDialog = new DialogInformation(this.getClient(), "End Turn not possible", new String[]{"You cannot end the turn before the acting player has reached a valid destination!"}, 1, "game.ref");
            this.fInfoDialog.showDialog(new IDialogCloseListener(){

                @Override
                public void dialogClosed(IDialog pDialog) {
                    ClientStatePassBlock.this.fInfoDialog.hideDialog();
                }
            });
            return;
        }
        this.getClient().getCommunication().sendEndTurn();
        this.getClient().getClientData().setEndTurnButtonHidden(true);
        SideBarComponent sideBarHome = this.getClient().getUserInterface().getSideBarHome();
        sideBarHome.refresh();
    }

}

