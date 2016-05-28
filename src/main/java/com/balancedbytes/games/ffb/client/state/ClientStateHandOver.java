/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayerUnderPlayers;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientStateMove;
import com.balancedbytes.games.ffb.client.util.UtilClientActionKeys;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ClientStateHandOver
extends ClientStateMove {
    protected ClientStateHandOver(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.HAND_OVER;
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pPlayer == actingPlayer.getPlayer()) {
            super.clickOnPlayer(pPlayer);
        } else {
            this.handOver(pPlayer);
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        FieldCoordinate playerPosition = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        FieldCoordinate catcherPosition = UtilClientActionKeys.findMoveCoordinate(this.getClient(), playerPosition, pActionKey);
        Player catcher = game.getFieldModel().getPlayer(catcherPosition);
        actionHandled = catcher != null ? this.handOver(catcher) : super.actionKeyPressed(pActionKey);
        return actionHandled;
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        if (this.canPlayerGetHandOver(pPlayer)) {
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

    public boolean canPlayerGetHandOver(Player pCatcher) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pCatcher != null && actingPlayer.getPlayer() != null) {
            FieldModel fieldModel = game.getFieldModel();
            FieldCoordinate throwerCoordinate = fieldModel.getPlayerCoordinate(actingPlayer.getPlayer());
            FieldCoordinate catcherCoordinate = fieldModel.getPlayerCoordinate(pCatcher);
            PlayerState catcherState = fieldModel.getPlayerState(pCatcher);
            return throwerCoordinate.isAdjacent(catcherCoordinate) && catcherState != null && (!actingPlayer.isSufferingAnimosity() || actingPlayer.getRace().equals(pCatcher.getRace())) && catcherState.hasTacklezones() && !UtilCards.hasSkill(game, pCatcher, Skill.NO_HANDS) && game.getTeamHome() == pCatcher.getTeam();
        }
        return false;
    }

    private boolean handOver(Player pCatcher) {
        ActingPlayer actingPlayer;
        Game game = this.getClient().getGame();
        if (UtilPlayer.hasBall(game, (actingPlayer = game.getActingPlayer()).getPlayer()) && (actingPlayer.getPlayerAction() == PlayerAction.HAND_OVER || this.canPlayerGetHandOver(pCatcher))) {
            this.getClient().getCommunication().sendHandOver(actingPlayer.getPlayerId(), pCatcher);
            return true;
        }
        return false;
    }

    @Override
    protected void createAndShowPopupMenuForActingPlayer() {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        IconCache iconCache = userInterface.getIconCache();
        userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (PlayerAction.HAND_OVER_MOVE == actingPlayer.getPlayerAction() && UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
            JMenuItem passAction = new JMenuItem("Hand Over Ball (any player)", new ImageIcon(iconCache.getIconByProperty("action.handover")));
            passAction.setMnemonic(72);
            passAction.setAccelerator(KeyStroke.getKeyStroke(72, 0));
            menuItemList.add(passAction);
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
        String endMoveActionLabel = actingPlayer.hasActed() ? "End Move" : "Deselect Player";
        JMenuItem endMoveAction = new JMenuItem(endMoveActionLabel, new ImageIcon(iconCache.getIconByProperty("action.end")));
        endMoveAction.setMnemonic(69);
        endMoveAction.setAccelerator(KeyStroke.getKeyStroke(69, 0));
        menuItemList.add(endMoveAction);
        this.createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
        this.showPopupMenuForPlayer(actingPlayer.getPlayer());
    }
}

