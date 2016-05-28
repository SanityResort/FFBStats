/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.util;

import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class UtilClientStateBlocking {
    public static boolean actionKeyPressed(ClientState pClientState, ActionKey pActionKey, boolean pDoBlitz) {
        boolean actionHandled = false;
        Game game = pClientState.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        switch (pActionKey) {
            case PLAYER_ACTION_BLOCK: {
                UtilClientStateBlocking.menuItemSelected(pClientState, actingPlayer.getPlayer(), 66);
                actionHandled = true;
                break;
            }
            case PLAYER_ACTION_STAB: {
                UtilClientStateBlocking.menuItemSelected(pClientState, actingPlayer.getPlayer(), 83);
                actionHandled = true;
                break;
            }
            default: {
                FieldCoordinate playerPosition = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
                FieldCoordinate moveCoordinate = UtilClientActionKeys.findMoveCoordinate(pClientState.getClient(), playerPosition, pActionKey);
                Player defender = game.getFieldModel().getPlayer(moveCoordinate);
                actionHandled = UtilClientStateBlocking.showPopupOrBlockPlayer(pClientState, defender, pDoBlitz);
            }
        }
        return actionHandled;
    }

    public static boolean menuItemSelected(ClientState pClientState, Player pPlayer, int pMenuKey) {
        boolean handled = false;
        if (pPlayer != null) {
            Game game = pClientState.getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            switch (pMenuKey) {
                case 66: {
                    handled = true;
                    UtilClientStateBlocking.block(pClientState, actingPlayer.getPlayerId(), pPlayer, false);
                    break;
                }
                case 83: {
                    handled = true;
                    UtilClientStateBlocking.block(pClientState, actingPlayer.getPlayerId(), pPlayer, true);
                }
            }
        }
        return handled;
    }

    public static boolean showPopupOrBlockPlayer(ClientState pClientState, Player pDefender, boolean pDoBlitz) {
        if (pDefender == null) {
            return false;
        }
        boolean handled = false;
        Game game = pClientState.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (UtilPlayer.isBlockable(game, pDefender) && (!pDoBlitz || UtilPlayer.isNextMovePossible(game, false))) {
            handled = true;
            FieldCoordinate defenderCoordinate = game.getFieldModel().getPlayerCoordinate(pDefender);
            if (UtilCards.hasSkill(game, actingPlayer, Skill.STAB)) {
                UtilClientStateBlocking.createAndShowStabPopupMenu(pClientState, pDefender);
            } else if (game.getFieldModel().getDiceDecoration(defenderCoordinate) != null) {
                UtilClientStateBlocking.block(pClientState, actingPlayer.getPlayerId(), pDefender, false);
            } else {
                handled = false;
            }
        }
        return handled;
    }

    private static void createAndShowStabPopupMenu(ClientState pClientState, Player pPlayer) {
        IconCache iconCache = pClientState.getClient().getUserInterface().getIconCache();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        JMenuItem stabAction = new JMenuItem("Stab Opponent", new ImageIcon(iconCache.getIconByProperty("action.stab")));
        stabAction.setMnemonic(83);
        stabAction.setAccelerator(KeyStroke.getKeyStroke(83, 0));
        menuItemList.add(stabAction);
        JMenuItem blockAction = new JMenuItem("Block Opponent", new ImageIcon(iconCache.getIconByProperty("action.block")));
        blockAction.setMnemonic(66);
        blockAction.setAccelerator(KeyStroke.getKeyStroke(66, 0));
        menuItemList.add(blockAction);
        pClientState.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
        pClientState.showPopupMenuForPlayer(pPlayer);
    }

    private static void block(ClientState pClientState, String pActingPlayerId, Player pDefender, boolean pUsingStab) {
        Game game = pClientState.getClient().getGame();
        game.getFieldModel().clearDiceDecorations();
        pClientState.getClient().getUserInterface().getFieldComponent().refresh();
        pClientState.getClient().getCommunication().sendBlock(pActingPlayerId, pDefender, pUsingStab);
    }

}

