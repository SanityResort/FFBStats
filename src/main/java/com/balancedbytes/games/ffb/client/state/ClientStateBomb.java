/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayerUnderPlayers;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.state.RangeGridHandler;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPassing;
import com.balancedbytes.games.ffb.util.UtilRangeRuler;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ClientStateBomb
extends ClientState {
    private boolean fShowRangeRuler;
    private RangeGridHandler fRangeGridHandler;

    protected ClientStateBomb(FantasyFootballClient pClient) {
        super(pClient);
        this.fRangeGridHandler = new RangeGridHandler(pClient, false);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.BOMB;
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
        if (pPlayer == actingPlayer.getPlayer()) {
            this.createAndShowPopupMenuForActingPlayer();
        } else {
            FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            this.clickOnField(playerCoordinate);
        }
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        FieldCoordinate throwerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        PassingDistance passingDistance = UtilPassing.findPassingDistance(game, throwerCoordinate, pCoordinate, false);
        if (PlayerAction.HAIL_MARY_BOMB == actingPlayer.getPlayerAction() || passingDistance != null) {
            game.setPassCoordinate(pCoordinate);
            this.getClient().getCommunication().sendPass(actingPlayer.getPlayerId(), game.getPassCoordinate());
            game.getFieldModel().setRangeRuler(null);
            this.getClient().getUserInterface().getFieldComponent().refresh();
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        this.getClient().getClientData().setSelectedPlayer(pPlayer);
        userInterface.refreshSideBars();
        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        return this.mouseOverField(playerCoordinate);
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        UserInterface userInterface = this.getClient().getUserInterface();
        boolean selectable = false;
        if (PlayerAction.HAIL_MARY_BOMB == actingPlayer.getPlayerAction()) {
            game.getFieldModel().setRangeRuler(null);
            userInterface.getFieldComponent().refresh();
            selectable = true;
            UtilClientCursor.setCustomCursor(userInterface, "cursor.bomb");
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
                UtilClientCursor.setCustomCursor(userInterface, "cursor.bomb");
            } else {
                UtilClientCursor.setDefaultCursor(userInterface);
            }
            fieldComponent.getLayerUnderPlayers().clearMovePath();
            fieldComponent.refresh();
        }
        return rangeRuler != null;
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

    protected void createAndShowPopupMenuForActingPlayer() {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        IconCache iconCache = userInterface.getIconCache();
        userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
        ArrayList<JMenuItem> menuItemList = new ArrayList<JMenuItem>();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (this.isHailMaryPassActionAvailable()) {
            String text = PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction() ? "Don't use Hail Mary Pass" : "Use Hail Mary Pass";
            JMenuItem hailMaryBombAction = new JMenuItem(text, new ImageIcon(iconCache.getIconByProperty("action.toggle.hailMaryBomb")));
            hailMaryBombAction.setMnemonic(72);
            hailMaryBombAction.setAccelerator(KeyStroke.getKeyStroke(72, 0));
            menuItemList.add(hailMaryBombAction);
        }
        if (this.isRangeGridAvailable()) {
            JMenuItem toggleRangeGridAction = new JMenuItem("Range Grid on/off", new ImageIcon(iconCache.getIconByProperty("action.toggle.rangeGrid")));
            toggleRangeGridAction.setMnemonic(82);
            toggleRangeGridAction.setAccelerator(KeyStroke.getKeyStroke(82, 0));
            menuItemList.add(toggleRangeGridAction);
        }
        if (this.isEndTurnActionAvailable()) {
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
    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        ClientCommunication communication = this.getClient().getCommunication();
        switch (pMenuKey) {
            case 69: {
                if (!this.isEndTurnActionAvailable()) break;
                communication.sendActingPlayer(null, null, false);
                break;
            }
            case 82: {
                if (!this.isRangeGridAvailable()) break;
                this.fRangeGridHandler.setShowRangeGrid(!this.fRangeGridHandler.isShowRangeGrid());
                this.fRangeGridHandler.refreshRangeGrid();
                break;
            }
            case 72: {
                if (!this.isHailMaryPassActionAvailable()) break;
                if (PlayerAction.HAIL_MARY_BOMB == actingPlayer.getPlayerAction()) {
                    communication.sendActingPlayer(pPlayer, PlayerAction.THROW_BOMB, actingPlayer.isLeaping());
                    this.fShowRangeRuler = true;
                } else {
                    communication.sendActingPlayer(pPlayer, PlayerAction.HAIL_MARY_BOMB, actingPlayer.isLeaping());
                    this.fShowRangeRuler = false;
                }
                if (this.fShowRangeRuler || game.getFieldModel().getRangeRuler() == null) break;
                game.getFieldModel().setRangeRuler(null);
            }
        }
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        if (pActionKey == null) {
            return false;
        }
        switch (pActionKey) {
            case PLAYER_ACTION_RANGE_GRID: {
                this.menuItemSelected(null, 82);
                return true;
            }
            case PLAYER_ACTION_HAIL_MARY_PASS: {
                this.menuItemSelected(null, 72);
                return true;
            }
            case PLAYER_ACTION_END_MOVE: {
                this.menuItemSelected(null, 69);
                return true;
            }
        }
        return super.actionKeyPressed(pActionKey);
    }

    private boolean isHailMaryPassActionAvailable() {
        ActingPlayer actingPlayer;
        Game game = this.getClient().getGame();
        return UtilCards.hasSkill(game, actingPlayer = game.getActingPlayer(), Skill.HAIL_MARY_PASS) && game.getFieldModel().getWeather() != Weather.BLIZZARD;
    }

    private boolean isRangeGridAvailable() {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        return actingPlayer.getPlayerAction() == PlayerAction.THROW_BOMB;
    }

    private boolean isEndTurnActionAvailable() {
        Game game = this.getClient().getGame();
        return !game.getTurnMode().isBombTurn();
    }

}

