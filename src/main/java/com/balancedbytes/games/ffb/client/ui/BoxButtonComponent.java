/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.BoxType;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class BoxButtonComponent
extends JPanel
implements MouseListener,
MouseMotionListener {
    public static final int WIDTH = 116;
    public static final int HEIGHT = 22;
    private static final Font _BUTTON_FONT = new Font("Sans Serif", 1, 11);
    private static final Dimension _BUTTON_DIMENSION = new Dimension(56, 22);
    private Map<BoxType, Rectangle> fButtonLocations;
    private SideBarComponent fSideBar;
    private BufferedImage fImage;
    private BoxType fOpenBox;
    private BoxType fSelectedBox;

    public BoxButtonComponent(SideBarComponent pSideBar) {
        this.fSideBar = pSideBar;
        this.fImage = new BufferedImage(116, 22, 2);
        this.fButtonLocations = new HashMap<BoxType, Rectangle>();
        if (this.getSideBar().isHomeSide()) {
            this.fButtonLocations.put(BoxType.RESERVES, new Rectangle(1, 0, BoxButtonComponent._BUTTON_DIMENSION.width, BoxButtonComponent._BUTTON_DIMENSION.height));
            this.fButtonLocations.put(BoxType.OUT, new Rectangle(59, 0, BoxButtonComponent._BUTTON_DIMENSION.width, BoxButtonComponent._BUTTON_DIMENSION.height));
        } else {
            this.fButtonLocations.put(BoxType.OUT, new Rectangle(1, 0, BoxButtonComponent._BUTTON_DIMENSION.width, BoxButtonComponent._BUTTON_DIMENSION.height));
            this.fButtonLocations.put(BoxType.RESERVES, new Rectangle(59, 0, BoxButtonComponent._BUTTON_DIMENSION.width, BoxButtonComponent._BUTTON_DIMENSION.height));
        }
        this.setLayout(null);
        Dimension size = new Dimension(116, 22);
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

    private BoxType findBoxTypeForLocation(Point pLocation) {
        BoxType boxType = null;
        if (pLocation != null) {
            Iterator<BoxType> boxTabIterator = this.fButtonLocations.keySet().iterator();
            while (boxType == null && boxTabIterator.hasNext()) {
                BoxType testedTab = boxTabIterator.next();
                Rectangle boxLocation = this.fButtonLocations.get((Object)testedTab);
                if (!boxLocation.contains(pLocation)) continue;
                boxType = testedTab;
            }
        }
        return boxType;
    }

    private void drawBackground() {
        Graphics2D g2d = this.fImage.createGraphics();
        IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
        BufferedImage background = this.getSideBar().isHomeSide() ? iconCache.getIconByProperty("sidebar.background.box.buttons.red") : iconCache.getIconByProperty("sidebar.background.box.buttons.blue");
        g2d.drawImage(background, 0, 0, null);
        g2d.dispose();
    }

    private void drawButton(BoxType pBox) {
        if (pBox != null) {
            Graphics2D g2d = this.fImage.createGraphics();
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            Rectangle buttonLocation = this.fButtonLocations.get((Object)pBox);
            BufferedImage buttonImage = pBox == this.fOpenBox || pBox == this.fSelectedBox ? iconCache.getIconByProperty("sidebar.box.button.selected") : iconCache.getIconByProperty("sidebar.box.button");
            g2d.drawImage(buttonImage, buttonLocation.x, buttonLocation.y, null);
            g2d.setFont(_BUTTON_FONT);
            g2d.setColor(Color.BLACK);
            FontMetrics metrics = g2d.getFontMetrics();
            String buttonText = "" + this.countBoxElements(pBox, this.getSideBar().isHomeSide()) + " " + pBox.getShortcut();
            int x = buttonLocation.x + this.findCenteredX(g2d, buttonText, buttonLocation.width);
            int y = buttonLocation.y + (buttonLocation.height + metrics.getHeight()) / 2 - metrics.getDescent();
            g2d.drawString(buttonText, x, y);
            g2d.dispose();
        }
    }

    private int findCenteredX(Graphics2D pG2d, String pText, int pWidth) {
        FontMetrics metrics = pG2d.getFontMetrics();
        Rectangle2D bounds = metrics.getStringBounds(pText, pG2d);
        return (pWidth - (int)bounds.getWidth()) / 2;
    }

    public void refresh() {
        this.drawBackground();
        this.drawButton(BoxType.RESERVES);
        this.drawButton(BoxType.OUT);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    public SideBarComponent getSideBar() {
        return this.fSideBar;
    }

    @Override
    public String getToolTipText(MouseEvent pEvent) {
        BoxType box = this.findBoxTypeForLocation(pEvent.getPoint());
        if (box != null) {
            int players = this.countBoxElements(box, this.getSideBar().isHomeSide());
            StringBuilder toolTip = new StringBuilder();
            if (players > 0) {
                toolTip.append(players).append(" ");
            } else {
                toolTip.append("No ");
            }
            if (players == 1) {
                toolTip.append(box.getToolTipSingle());
            } else {
                toolTip.append(box.getToolTipMultiple());
            }
            return toolTip.toString();
        }
        return null;
    }

    public BoxType getOpenBox() {
        return this.fOpenBox;
    }

    @Override
    public void mouseClicked(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent pMouseEvent) {
        this.mouseMoved(pMouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
        BoxType newBox = this.findBoxTypeForLocation(pMouseEvent.getPoint());
        if (newBox != null) {
            if (newBox != this.fOpenBox) {
                this.getSideBar().openBox(newBox);
            } else {
                this.getSideBar().closeBox();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        BoxType newBox = this.findBoxTypeForLocation(pMouseEvent.getPoint());
        BoxType oldSelectedBox = this.fSelectedBox;
        if (newBox != null) {
            if (newBox != this.fSelectedBox) {
                this.fSelectedBox = newBox;
                this.drawButton(this.fSelectedBox);
                if (oldSelectedBox != null) {
                    this.drawButton(oldSelectedBox);
                }
                this.repaint();
            }
        } else if (oldSelectedBox != null) {
            this.fSelectedBox = null;
            this.drawButton(oldSelectedBox);
            this.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
    }

    public int countBoxElements(BoxType pBox, boolean pHomeSide) {
        int elements = 0;
        if (BoxType.RESERVES == pBox) {
            elements += this.countBoxElements(pHomeSide ? -1 : 30);
        }
        if (BoxType.OUT == pBox) {
            elements += this.countBoxElements(pHomeSide ? -2 : 31);
            elements += this.countBoxElements(pHomeSide ? -3 : 32);
            elements += this.countBoxElements(pHomeSide ? -4 : 33);
            elements += this.countBoxElements(pHomeSide ? -5 : 34);
            elements += this.countBoxElements(pHomeSide ? -6 : 35);
        }
        return elements;
    }

    private int countBoxElements(int pXCoordinate) {
        int elements = 0;
        FieldModel fieldModel = this.getSideBar().getClient().getGame().getFieldModel();
        for (int y = 0; y < 30; ++y) {
            if (fieldModel.getPlayer(new FieldCoordinate(pXCoordinate, y)) == null) continue;
            ++elements;
        }
        return elements;
    }
}

