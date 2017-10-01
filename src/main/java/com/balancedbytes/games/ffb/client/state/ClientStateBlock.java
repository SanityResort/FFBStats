/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.client.util.UtilClientStateBlocking;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import javax.swing.*;
import java.util.ArrayList;

public class ClientStateBlock
extends ClientState {
    protected ClientStateBlock(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.BLOCK;
    }

    @Override
    public void enterState() {
        super.enterState();
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (actingPlayer.getPlayer() == pPlayer) {
            if (actingPlayer.isSufferingBloodLust()) {
                this.createAndShowPopupMenuForBlockingPlayer();
            } else if (PlayerAction.BLITZ == actingPlayer.getPlayerAction()) {
                this.getClient().getCommunication().sendActingPlayer(actingPlayer.getPlayer(), PlayerAction.BLITZ_MOVE, actingPlayer.isLeaping());
            } else {
                this.createAndShowPopupMenuForBlockingPlayer();
            }
        } else {
            UtilClientStateBlocking.showPopupOrBlockPlayer(this, pPlayer, false);
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        if (UtilPlayer.isBlockable(this.getClient().getGame(), pPlayer)) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.block");
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

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (actingPlayer.isSufferingBloodLust()) {
            boolean actionHandled = true;
            switch (pActionKey) {
                case PLAYER_SELECT: {
                    this.createAndShowPopupMenuForBlockingPlayer();
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
                }
            }
            return actionHandled;
        }
        return UtilClientStateBlocking.actionKeyPressed(this, pActionKey, false);
    }

    @Override
    public void endTurn() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        this.menuItemSelected(actingPlayer.getPlayer(), 69);
        this.getClient().getCommunication().sendEndTurn();
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
                case 77: {
                    this.getClient().getCommunication().sendActingPlayer(pPlayer, PlayerAction.MOVE, actingPlayer.isLeaping());
                    break;
                }
                default: {
                    UtilClientStateBlocking.menuItemSelected(this, pPlayer, pMenuKey);
                }
            }
        }
    }

    private void createAndShowPopupMenuForBlockingPlayer() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        UserInterface userInterface = this.getClient().getUserInterface();
        IconCache iconCache = userInterface.getIconCache();
        userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
        if (actingPlayer.isSufferingBloodLust()) {
            JMenuItem moveAction = new JMenuItem("Move", new ImageIcon(iconCache.getIconByProperty("action.move")));
            moveAction.setMnemonic(77);
            moveAction.setAccelerator(KeyStroke.getKeyStroke(77, 0));
            menuItemList.add(moveAction);
        }
        String endMoveActionLabel = actingPlayer.hasActed() ? "End Move" : "Deselect Player";
        JMenuItem endMoveAction = new JMenuItem(endMoveActionLabel, new ImageIcon(iconCache.getIconByProperty("action.end")));
        endMoveAction.setMnemonic(69);
        endMoveAction.setAccelerator(KeyStroke.getKeyStroke(69, 0));
        menuItemList.add(endMoveAction);
        this.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
        this.showPopupMenuForPlayer(actingPlayer.getPlayer());
    }

}

