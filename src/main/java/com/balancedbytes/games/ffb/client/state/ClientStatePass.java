/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
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
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import com.balancedbytes.games.ffb.util.UtilRangeRuler;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ClientStatePass
extends ClientStateMove {
    private boolean fShowRangeRuler;
    private RangeGridHandler fRangeGridHandler;

    protected ClientStatePass(FantasyFootballClient pClient) {
        super(pClient);
        this.fRangeGridHandler = new RangeGridHandler(pClient, false);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.PASS;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.fShowRangeRuler = true;
        this.fRangeGridHandler.refreshSettings();
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (pPlayer == actingPlayer.getPlayer()) {
            super.clickOnPlayer(pPlayer);
        } else if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction() || UtilPlayer.hasBall(game, actingPlayer.getPlayer()) && (PlayerAction.PASS == actingPlayer.getPlayerAction() || this.canPlayerGetPass(pPlayer))) {
            game.setPassCoordinate(game.getFieldModel().getPlayerCoordinate(pPlayer));
            this.getClient().getCommunication().sendPass(actingPlayer.getPlayerId(), game.getPassCoordinate());
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().refresh();
        }
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (actingPlayer.getPlayerAction() == PlayerAction.PASS_MOVE) {
            super.clickOnField(pCoordinate);
        } else if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction() || UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
            game.setPassCoordinate(pCoordinate);
            this.getClient().getCommunication().sendPass(actingPlayer.getPlayerId(), game.getPassCoordinate());
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().refresh();
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        boolean selectable = false;
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (PlayerAction.HAIL_MARY_PASS != actingPlayer.getPlayerAction() && UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
            FieldCoordinate catcherCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            if (PlayerAction.PASS == actingPlayer.getPlayerAction() || this.canPlayerGetPass(pPlayer)) {
                this.drawRangeRuler(catcherCoordinate);
            }
        } else {
            game.getFieldModel().setRangeRuler(null);
            FieldComponent fieldComponent = userInterface.getFieldComponent();
            fieldComponent.getLayerUnderPlayers().clearMovePath();
            fieldComponent.refresh();
            selectable = true;
            if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) {
                UtilClientCursor.setCustomCursor(userInterface, "cursor.pass");
            } else {
                UtilClientCursor.setDefaultCursor(userInterface);
            }
        }
        this.getClient().getClientData().setSelectedPlayer(pPlayer);
        userInterface.refreshSideBars();
        return selectable;
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        boolean selectable = false;
        if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) {
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
            userInterface.getFieldComponent().refresh();
            selectable = true;
            UtilClientCursor.setCustomCursor(userInterface, "cursor.pass");
        } else if (actingPlayer.getPlayerAction() == PlayerAction.PASS_MOVE) {
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().refresh();
            selectable = super.mouseOverField(pCoordinate);
        } else {
            this.drawRangeRuler(pCoordinate);
        }
        return selectable;
    }

    private boolean drawRangeRuler(FieldCoordinate pCoordinate) {
        RangeRuler rangeRuler = null;
        Game game = this.getClient().getGame();
        if (this.fShowRangeRuler && game.getPassCoordinate() == null) {
            ActingPlayer actingPlayer = game.getActingPlayer();
            UserInterface userInterface = this.getClient().getUserInterface();
            FieldComponent fieldComponent = userInterface.getFieldComponent();
            rangeRuler = UtilRangeRuler.createRangeRuler(game, actingPlayer.getPlayer(), pCoordinate, false);
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

    public boolean canPlayerGetPass(Player pCatcher) {
        boolean canGetPass = false;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pCatcher != null && actingPlayer.getPlayer() != null) {
            PlayerState catcherState = game.getFieldModel().getPlayerState(pCatcher);
            canGetPass = !UtilCards.hasSkill(game, pCatcher, Skill.NO_HANDS) && catcherState != null && catcherState.hasTacklezones() && game.getTeamHome() == pCatcher.getTeam() && (!actingPlayer.isSufferingAnimosity() || actingPlayer.getRace().equals(pCatcher.getRace()));
        }
        return canGetPass;
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
    }

    @Override
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
        if (UtilCards.hasSkill(game, actingPlayer, Skill.HAIL_MARY_PASS) && UtilPlayer.hasBall(game, actingPlayer.getPlayer()) && game.getFieldModel().getWeather() != Weather.BLIZZARD) {
            String text = PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction() ? "Don't use Hail Mary Pass" : "Use Hail Mary Pass";
            JMenuItem hailMaryPassAction = new JMenuItem(text, new ImageIcon(iconCache.getIconByProperty("action.toggle.hailMaryPass")));
            hailMaryPassAction.setMnemonic(72);
            hailMaryPassAction.setAccelerator(KeyStroke.getKeyStroke(72, 0));
            menuItemList.add(hailMaryPassAction);
        }
        if (UtilCards.hasUnusedSkill(game, actingPlayer, Skill.LEAP) && UtilPlayer.isNextMovePossible(game, false)) {
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
        JMenuItem toggleRangeGridAction = new JMenuItem("Range Grid on/off", new ImageIcon(iconCache.getIconByProperty("action.toggle.rangeGrid")));
        toggleRangeGridAction.setMnemonic(82);
        toggleRangeGridAction.setAccelerator(KeyStroke.getKeyStroke(82, 0));
        menuItemList.add(toggleRangeGridAction);
        if (!actingPlayer.isSufferingAnimosity()) {
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

    @Override
    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        ClientCommunication communication = this.getClient().getCommunication();
        if (pMenuKey == 82) {
            this.fRangeGridHandler.setShowRangeGrid(!this.fRangeGridHandler.isShowRangeGrid());
            this.fRangeGridHandler.refreshRangeGrid();
        } else if (pMenuKey == 72) {
            if (UtilCards.hasSkill(game, game.getActingPlayer(), Skill.HAIL_MARY_PASS)) {
                if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) {
                    communication.sendActingPlayer(pPlayer, PlayerAction.PASS, actingPlayer.isLeaping());
                    this.fShowRangeRuler = true;
                } else {
                    communication.sendActingPlayer(pPlayer, PlayerAction.HAIL_MARY_PASS, actingPlayer.isLeaping());
                    this.fShowRangeRuler = false;
                }
                if (!this.fShowRangeRuler && game.getFieldModel().getRangeRuler() != null) {
                    game.getFieldModel().setRangeRuler(null);
                }
            }
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
        if (pActionKey == ActionKey.PLAYER_ACTION_HAIL_MARY_PASS) {
            this.menuItemSelected(null, 72);
            return true;
        }
        return super.actionKeyPressed(pActionKey);
    }
}

