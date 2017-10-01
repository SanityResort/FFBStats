/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class ClientStateFoul
extends ClientStateMove {
    protected ClientStateFoul(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.FOUL;
    }

    @Override
    public void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pPlayer == actingPlayer.getPlayer()) {
            if (actingPlayer.isSufferingBloodLust()) {
                this.createAndShowPopupMenuForBloodLustPlayer();
            } else {
                super.clickOnPlayer(pPlayer);
            }
        } else if (UtilPlayer.isNextMoveGoingForIt(game) && !actingPlayer.isGoingForIt()) {
            this.createAndShowPopupMenuForActingPlayer();
        } else {
            this.foul(pPlayer);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (actingPlayer.isSufferingBloodLust()) {
            switch (pActionKey) {
                case PLAYER_SELECT: {
                    this.createAndShowPopupMenuForBloodLustPlayer();
                    break;
                }
                case PLAYER_ACTION_MOVE: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 77);
                    break;
                }
                case PLAYER_ACTION_END_MOVE: {
                    this.menuItemSelected(actingPlayer.getPlayer(), 69);
                    break;
                }
                default: {
                    actionHandled = false;
                    break;
                }
            }
        } else {
            FieldCoordinate playerPosition = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
            FieldCoordinate defenderPosition = UtilClientActionKeys.findMoveCoordinate(this.getClient(), playerPosition, pActionKey);
            Player defender = game.getFieldModel().getPlayer(defenderPosition);
            actionHandled = defender != null ? this.foul(defender) : super.actionKeyPressed(pActionKey);
        }
        return actionHandled;
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        Game game = this.getClient().getGame();
        if (UtilPlayer.isFoulable(game, pPlayer)) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.foul");
        }
        return true;
    }

    private boolean foul(Player pDefender) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        boolean doFoul = UtilPlayer.isFoulable(game, pDefender);
        if (doFoul) {
            this.getClient().getCommunication().sendFoul(actingPlayer.getPlayerId(), pDefender);
        }
        return doFoul;
    }

    @Override
    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
        if (pPlayer != null) {
            Game game = this.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            switch (pMenuKey) {
                case 69: {
                    this.getClient().getCommunication().sendActingPlayer(null, null, false);
                    break;
                }
                case 76: {
                    if (!UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) || !UtilPlayer.isNextMovePossible(game, false)) break;
                    this.getClient().getCommunication().sendActingPlayer(pPlayer, actingPlayer.getPlayerAction(), !actingPlayer.isLeaping());
                    break;
                }
                case 77: {
                    if (!actingPlayer.isSufferingBloodLust()) break;
                    this.getClient().getCommunication().sendActingPlayer(pPlayer, PlayerAction.MOVE, actingPlayer.isLeaping());
                }
            }
        }
    }

    protected void createAndShowPopupMenuForBloodLustPlayer() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (actingPlayer.isSufferingBloodLust()) {
            UserInterface userInterface = this.getClient().getUserInterface();
            IconCache iconCache = userInterface.getIconCache();
            userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
            ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
            JMenuItem moveAction = new JMenuItem("Move", new ImageIcon(iconCache.getIconByProperty("action.move")));
            moveAction.setMnemonic(77);
            moveAction.setAccelerator(KeyStroke.getKeyStroke(77, 0));
            menuItemList.add(moveAction);
            JMenuItem endMoveAction = new JMenuItem("End Move", new ImageIcon(iconCache.getIconByProperty("action.end")));
            endMoveAction.setMnemonic(69);
            endMoveAction.setAccelerator(KeyStroke.getKeyStroke(69, 0));
            menuItemList.add(endMoveAction);
            this.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
            this.showPopupMenuForPlayer(actingPlayer.getPlayer());
        }
    }

}

