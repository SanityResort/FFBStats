/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.client.util.UtilClientMarker;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.INetCommandHandler;
import com.balancedbytes.games.ffb.net.NetCommand;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public abstract class ClientState
implements INetCommandHandler,
MouseListener,
MouseMotionListener,
ActionListener {
    public static final int FIELD_SQUARE_SIZE = 30;
    public static final int DICE_INDEX_PRO_REROLL = 3;
    public static final int DICE_INDEX_TEAM_REROLL = 4;
    private FantasyFootballClient fClient;
    private FieldCoordinate fSelectSquareCoordinate;
    private boolean fClickable;
    private boolean fSelectable;
    private boolean fPopupMenuShown;
    private JPopupMenu fPopupMenu;
    private Player fPopupMenuPlayer;

    protected ClientState(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.setSelectable(true);
        this.setClickable(true);
    }

    public abstract ClientStateId getId();

    public void enterState() {
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.getDialogManager().updateDialog();
        UtilClientCursor.setDefaultCursor(userInterface);
    }

    public void leaveState() {
        UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    protected FieldCoordinate getFieldCoordinate(MouseEvent pMouseEvent) {
        FieldCoordinate coordinate = null;
        int x = pMouseEvent.getX();
        int y = pMouseEvent.getY();
        if (x < 930 && y > 0 && y < 450) {
            coordinate = new FieldCoordinate(x / 30, y / 30);
        }
        return coordinate;
    }

    public void showSelectSquare(FieldCoordinate pCoordinate) {
        if (pCoordinate != null) {
            this.fSelectSquareCoordinate = pCoordinate;
            this.drawSelectSquare(this.fSelectSquareCoordinate, new Color(0.0f, 0.0f, 1.0f, 0.2f));
        }
    }

    protected void drawSelectSquare(FieldCoordinate pCoordinate, Color pColor) {
        if (pCoordinate != null) {
            FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
            int x = pCoordinate.getX() * 30 + 1;
            int y = pCoordinate.getY() * 30 + 1;
            Rectangle bounds = new Rectangle(x, y, 28, 28);
            Graphics2D g2d = fieldComponent.getImage().createGraphics();
            g2d.setPaint(pColor);
            g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2d.dispose();
            fieldComponent.repaint(bounds);
        }
    }

    public void hideSelectSquare() {
        if (this.fSelectSquareCoordinate != null) {
            FieldComponent fieldComponent = this.getClient().getUserInterface().getFieldComponent();
            int x = this.fSelectSquareCoordinate.getX() * 30;
            int y = this.fSelectSquareCoordinate.getY() * 30;
            Rectangle bounds = new Rectangle(x, y, 30, 30);
            fieldComponent.refresh(bounds);
            this.fSelectSquareCoordinate = null;
        }
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        if (this.isSelectable()) {
            FieldCoordinate coordinate = this.getFieldCoordinate(pMouseEvent);
            if (coordinate == null || !FieldCoordinateBounds.FIELD.isInBounds(coordinate)) {
                this.hideSelectSquare();
            } else if (!coordinate.equals(this.fSelectSquareCoordinate)) {
                this.hideSelectSquare();
                boolean selectable = true;
                Game game = this.getClient().getGame();
                Player player = game.getFieldModel().getPlayer(coordinate);
                selectable = player != null ? this.mouseOverPlayer(player) : this.mouseOverField(coordinate);
                if (selectable) {
                    this.showSelectSquare(coordinate);
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent pMouseEvent) {
        this.hideSelectSquare();
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
        this.setSelectable(true);
        FieldCoordinate coordinate = this.getFieldCoordinate(pMouseEvent);
        if (this.getClient().getGame() != null && coordinate != null) {
            Player player = this.getClient().getGame().getFieldModel().getPlayer(coordinate);
            if (pMouseEvent.isShiftDown() && ClientMode.PLAYER == this.getClient().getMode()) {
                this.hideSelectSquare();
                if (player != null) {
                    int x = (coordinate.getX() + 1) * 30;
                    int y = (coordinate.getY() + 1) * 30;
                    UtilClientMarker.showMarkerPopup(this.getClient(), player, x, y);
                } else {
                    UtilClientMarker.showMarkerPopup(this.getClient(), coordinate);
                }
            } else if (this.isClickable()) {
                this.hideSelectSquare();
                if (player != null) {
                    this.clickOnPlayer(player);
                } else {
                    this.clickOnField(coordinate);
                }
            } else {
                this.fPopupMenuShown = false;
            }
        }
    }

    public void createPopupMenu(JMenuItem[] pMenuItems) {
        this.fPopupMenu = new JPopupMenu();
        for (int i = 0; i < pMenuItems.length; ++i) {
            pMenuItems[i].addActionListener(this);
            this.fPopupMenu.add(pMenuItems[i]);
        }
    }

    public void removePopupMenu() {
        this.fPopupMenu = null;
    }

    public void showPopupMenuForPlayer(Player pPlayer) {
        if (pPlayer != null && this.fPopupMenu != null) {
            this.fPopupMenuPlayer = pPlayer;
            FieldCoordinate coordinate = this.getClient().getGame().getFieldModel().getPlayerCoordinate(this.fPopupMenuPlayer);
            if (coordinate != null) {
                this.setSelectable(false);
                int x = (coordinate.getX() + 1) * 30;
                int y = (coordinate.getY() + 1) * 30;
                this.fPopupMenu.show(this.fClient.getUserInterface().getFieldComponent(), x, y);
                this.fPopupMenuShown = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent pActionEvent) {
        JMenuItem menuItem = (JMenuItem)pActionEvent.getSource();
        this.setSelectable(true);
        this.fPopupMenuShown = false;
        this.menuItemSelected(this.fPopupMenuPlayer, menuItem.getMnemonic());
    }

    protected void clickOnField(FieldCoordinate pCoordinate) {
    }

    protected void clickOnPlayer(Player pPlayer) {
    }

    protected boolean mouseOverPlayer(Player pPlayer) {
        if (this.getClient().getClientData().getSelectedPlayer() != pPlayer) {
            this.getClient().getClientData().setSelectedPlayer(pPlayer);
            this.getClient().getUserInterface().refreshSideBars();
        }
        return true;
    }

    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        if (this.getClient().getClientData().getSelectedPlayer() != null) {
            this.getClient().getClientData().setSelectedPlayer(null);
            this.getClient().getUserInterface().refreshSideBars();
        }
        return true;
    }

    protected void menuItemSelected(Player pPlayer, int pMenuKey) {
    }

    public void setClickable(boolean pClickable) {
        this.fClickable = pClickable;
    }

    public boolean isClickable() {
        return this.fClickable && !this.getClient().getUserInterface().getDialogManager().isDialogVisible() && !this.fPopupMenuShown;
    }

    public void setSelectable(boolean pSelectable) {
        this.fSelectable = pSelectable;
        if (!this.isSelectable()) {
            this.hideSelectSquare();
        }
    }

    public boolean isSelectable() {
        return this.fSelectable;
    }

    public boolean actionKeyPressed(ActionKey pActionKey) {
        return false;
    }

    protected Player getPopupMenuPlayer() {
        return this.fPopupMenuPlayer;
    }

    public boolean isInitDragAllowed(FieldCoordinate pCoordinate) {
        return false;
    }

    public boolean isDragAllowed(FieldCoordinate pCoordinate) {
        return false;
    }

    public boolean isDropAllowed(FieldCoordinate pCoordinate) {
        return false;
    }

    public void refreshSettings() {
    }

    public void endTurn() {
    }
}

