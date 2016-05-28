/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.BoxType;
import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.BoxSlot;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.client.util.UtilClientGraphics;
import com.balancedbytes.games.ffb.client.util.UtilClientMarker;
import com.balancedbytes.games.ffb.client.util.UtilClientPlayerDrag;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class BoxComponent
extends JPanel
implements MouseListener,
MouseMotionListener {
    public static final int WIDTH = 116;
    public static final int HEIGHT = 430;
    public static final int MAX_BOX_ELEMENTS = 30;
    public static final int FIELD_SQUARE_SIZE = 39;
    public static final int BOX_TITLE_OFFSET = 16;
    private static final Font _BOX_FONT = new Font("Sans Serif", 1, 12);
    private SideBarComponent fSideBar;
    private BufferedImage fImage;
    private BoxType fOpenBox;
    private List<BoxSlot> fBoxSlots;
    private int fMaxTitleOffset;

    public BoxComponent(SideBarComponent pSideBar) {
        this.fSideBar = pSideBar;
        this.fImage = new BufferedImage(116, 430, 2);
        this.fBoxSlots = new ArrayList<BoxSlot>();
        this.setLayout(null);
        Dimension size = new Dimension(116, 430);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.fOpenBox = null;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        ToolTipManager.sharedInstance().registerComponent(this);
    }

    public void closeBox() {
        this.openBox(null);
    }

    public void openBox(BoxType pBox) {
        this.fOpenBox = pBox;
        this.refresh();
    }

    private void drawBackground() {
        Graphics2D g2d = this.fImage.createGraphics();
        IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
        BufferedImage background = iconCache.getIconByProperty("sidebar.background.box");
        g2d.drawImage(background, 0, 0, null);
        g2d.dispose();
    }

    public void refresh() {
        this.drawBackground();
        this.drawPlayers();
        this.repaint();
    }

    private void drawBoxSlot(BoxSlot pBoxSlot) {
        if (pBoxSlot != null && pBoxSlot.getPlayer() != null) {
            BufferedImage icon;
            PlayerIconFactory playerIconFactory = this.getSideBar().getClient().getUserInterface().getPlayerIconFactory();
            FieldModel fieldModel = this.getSideBar().getClient().getGame().getFieldModel();
            FieldCoordinate playerCoordinate = fieldModel.getPlayerCoordinate(pBoxSlot.getPlayer());
            if (playerCoordinate != null && (icon = playerIconFactory.getIcon(this.getSideBar().getClient(), pBoxSlot.getPlayer())) != null) {
                Graphics2D g2d = this.fImage.createGraphics();
                int x = pBoxSlot.getLocation().x + (pBoxSlot.getLocation().width - icon.getWidth()) / 2;
                int y = pBoxSlot.getLocation().y + (pBoxSlot.getLocation().height - icon.getHeight()) / 2;
                g2d.drawImage(icon, x, y, null);
                g2d.dispose();
            }
        }
    }

    private void drawPlayers() {
        this.fBoxSlots.clear();
        int yPosition = 0;
        if (BoxType.RESERVES == this.fOpenBox) {
            yPosition = this.drawPlayersInBox(this.getSideBar().isHomeSide() ? -1 : 30, yPosition);
        }
        if (BoxType.OUT == this.fOpenBox) {
            yPosition = this.drawPlayersInBox(this.getSideBar().isHomeSide() ? -2 : 31, yPosition);
            yPosition = this.drawPlayersInBox(this.getSideBar().isHomeSide() ? -3 : 32, yPosition);
            yPosition = this.drawPlayersInBox(this.getSideBar().isHomeSide() ? -4 : 33, yPosition);
            yPosition = this.drawPlayersInBox(this.getSideBar().isHomeSide() ? -5 : 34, yPosition);
            yPosition = this.drawPlayersInBox(this.getSideBar().isHomeSide() ? -6 : 35, yPosition);
        }
    }

    private int drawPlayersInBox(int pXCoordinate, int pYPosition) {
        FieldModel fieldModel = this.getSideBar().getClient().getGame().getFieldModel();
        PlayerState boxState = this.findPlayerStateForXCoordinate(pXCoordinate);
        int yPos = this.drawTitle(boxState, pYPosition);
        int row = -1;
        for (int y = 0; y < 30; ++y) {
            Player player = fieldModel.getPlayer(new FieldCoordinate(pXCoordinate, y));
            if (player == null && pXCoordinate != -1) continue;
            row = y / 3;
            int locationX = y % 3 * 39;
            int locationY = yPos + row * 39;
            BoxSlot boxSlot = new BoxSlot(new Rectangle(locationX, locationY, 39, 39), boxState);
            boxSlot.setPlayer(player);
            this.fBoxSlots.add(boxSlot);
            this.drawBoxSlot(boxSlot);
        }
        if (row >= 0) {
            yPos += (row + 1) * 39;
        }
        return yPos;
    }

    @Override
    public String getToolTipText(MouseEvent pMouseEvent) {
        Game game = this.getSideBar().getClient().getGame();
        for (BoxSlot boxSlot : this.fBoxSlots) {
            if (!boxSlot.getLocation().contains(pMouseEvent.getPoint())) continue;
            return boxSlot.getToolTip(game);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    public SideBarComponent getSideBar() {
        return this.fSideBar;
    }

    public BoxType getOpenBox() {
        return this.fOpenBox;
    }

    public int getMaxTitleOffset() {
        return this.fMaxTitleOffset;
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
        if (this.getSideBar().isHomeSide() && BoxType.RESERVES == this.fOpenBox) {
            UtilClientPlayerDrag.mousePressed(this.getSideBar().getClient(), pMouseEvent, true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        if (this.getSideBar().isHomeSide() && BoxType.RESERVES == this.fOpenBox) {
            UtilClientPlayerDrag.mouseDragged(this.getSideBar().getClient(), pMouseEvent, true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
        if (pMouseEvent.isShiftDown() && ClientMode.PLAYER == this.getSideBar().getClient().getMode()) {
            BoxSlot boxSlot = this.findSlot(pMouseEvent.getPoint());
            if (boxSlot != null) {
                int x = this.getSideBar().isHomeSide() ? 5 : 647;
                int y = boxSlot.getLocation().y + boxSlot.getLocation().height;
                UtilClientMarker.showMarkerPopup(this.getSideBar().getClient(), boxSlot.getPlayer(), x, y);
            }
        } else if (this.getSideBar().isHomeSide() && BoxType.RESERVES == this.fOpenBox) {
            UtilClientPlayerDrag.mouseReleased(this.getSideBar().getClient(), pMouseEvent, true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        BoxSlot boxSlot;
        if (this.fOpenBox != null && (boxSlot = this.findSlot(pMouseEvent.getPoint())) != null && boxSlot.getPlayer() != null) {
            this.getSideBar().getClient().getClientData().setSelectedPlayer(boxSlot.getPlayer());
            UserInterface userInterface = this.getSideBar().getClient().getUserInterface();
            userInterface.refreshSideBars();
        }
    }

    public BoxSlot findSlot(Point pPoint) {
        for (BoxSlot slot : this.fBoxSlots) {
            if (!slot.getLocation().contains(pPoint)) continue;
            return slot;
        }
        return null;
    }

    private PlayerState findPlayerStateForXCoordinate(int pXCoordinate) {
        switch (pXCoordinate) {
            case -1: 
            case 30: {
                return new PlayerState(9);
            }
            case -2: 
            case 31: {
                return new PlayerState(5);
            }
            case -3: 
            case 32: {
                return new PlayerState(6);
            }
            case -4: 
            case 33: {
                return new PlayerState(7);
            }
            case -5: 
            case 34: {
                return new PlayerState(8);
            }
            case -6: 
            case 35: {
                return new PlayerState(13);
            }
            case -7: 
            case 36: {
                return new PlayerState(10);
            }
        }
        return null;
    }

    private int drawTitle(PlayerState pPlayerState, int pYPosition) {
        int height = 0;
        if (pPlayerState != null) {
            String title = null;
            switch (pPlayerState.getBase()) {
                case 9: {
                    title = "Reserve";
                    break;
                }
                case 5: {
                    title = "Knocked Out";
                    break;
                }
                case 6: {
                    title = "Badly Hurt";
                    break;
                }
                case 7: {
                    title = "Seriously Injured";
                    break;
                }
                case 8: {
                    title = "Killed";
                    break;
                }
                case 13: {
                    title = "Banned";
                }
            }
            if (title != null) {
                Graphics2D g2d = this.fImage.createGraphics();
                g2d.setFont(_BOX_FONT);
                FontMetrics metrics = g2d.getFontMetrics();
                Rectangle2D bounds = metrics.getStringBounds(title, g2d);
                int x = (116 - (int)bounds.getWidth()) / 2;
                int y = pYPosition + metrics.getAscent() + 2;
                UtilClientGraphics.drawShadowedText(g2d, title, x, y);
                y = pYPosition + (int)bounds.getHeight() / 2 + 3;
                g2d.setColor(Color.WHITE);
                g2d.drawLine(2, y, x - 4, y);
                g2d.drawLine(x + (int)bounds.getWidth() + 4, y, 113, y);
                g2d.setColor(Color.BLACK);
                g2d.drawLine(2, y + 1, x - 4, y + 1);
                g2d.drawLine(x + (int)bounds.getWidth() + 4, y + 1, 113, y + 1);
                height = (int)bounds.getHeight() + 4;
                if (height > this.fMaxTitleOffset) {
                    this.fMaxTitleOffset = height;
                }
                g2d.dispose();
            }
        }
        return pYPosition + height;
    }
}

