/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ReplayControl
extends JPanel
implements MouseInputListener {
    public static final int HEIGHT = 26;
    public static final int WIDTH = 389;
    private static final int _ICON_WIDTH = 36;
    private static final int _ICON_GAP = 10;
    private FantasyFootballClient fClient;
    private BufferedImage fImage;
    private boolean fActive;
    private ReplayButton fButtonSkipBackward;
    private ReplayButton fButtonFastBackward;
    private ReplayButton fButtonPlayBackward;
    private ReplayButton fButtonPause;
    private ReplayButton fButtonPlayForward;
    private ReplayButton fButtonFastForward;
    private ReplayButton fButtonSkipForward;

    public ReplayControl(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fImage = new BufferedImage(389, 26, 2);
        this.setLayout(null);
        Dimension size = new Dimension(389, 26);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.fButtonPause = new ReplayButton(new Point(176, 1), "replay.pause", "replay.pause.active", "replay.pause.selected");
        this.fButtonPlayBackward = new ReplayButton(new Point(this.fButtonPause.getPosition().x - 36 - 10, 1), "replay.play.backward", "replay.play.backward.active", "replay.play.backward.selected");
        this.fButtonFastBackward = new ReplayButton(new Point(this.fButtonPlayBackward.getPosition().x - 36 - 10, 1), "replay.fast.backward", "replay.fast.backward.active", "replay.fast.backward.selected");
        this.fButtonSkipBackward = new ReplayButton(new Point(this.fButtonFastBackward.getPosition().x - 36 - 10, 1), "replay.skip.backward", "replay.skip.backward.active", "replay.skip.backward.selected");
        this.fButtonPlayForward = new ReplayButton(new Point(this.fButtonPause.getPosition().x + 36 + 10, 1), "replay.play.forward", "replay.play.forward.active", "replay.play.forward.selected");
        this.fButtonFastForward = new ReplayButton(new Point(this.fButtonPlayForward.getPosition().x + 36 + 10, 1), "replay.fast.forward", "replay.fast.forward.active", "replay.fast.forward.selected");
        this.fButtonSkipForward = new ReplayButton(new Point(this.fButtonFastForward.getPosition().x + 36 + 10, 1), "replay.skip.forward", "replay.skip.forward.active", "replay.skip.forward.selected");
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private void refresh() {
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    private void deActivateAll() {
        this.fButtonSkipBackward.setActive(false);
        this.fButtonFastBackward.setActive(false);
        this.fButtonPlayBackward.setActive(false);
        this.fButtonPause.setActive(false);
        this.fButtonPlayForward.setActive(false);
        this.fButtonFastForward.setActive(false);
        this.fButtonSkipForward.setActive(false);
    }

    private void deSelectAll() {
        this.fButtonSkipBackward.setSelected(false);
        this.fButtonFastBackward.setSelected(false);
        this.fButtonPlayBackward.setSelected(false);
        this.fButtonPause.setSelected(false);
        this.fButtonPlayForward.setSelected(false);
        this.fButtonFastForward.setSelected(false);
        this.fButtonSkipForward.setSelected(false);
    }

    public void showPause() {
        this.deActivateAll();
        this.fButtonPause.setActive(true);
        this.refresh();
    }

    public boolean isActive() {
        return this.fActive;
    }

    public void setActive(boolean pActive) {
        this.fActive = pActive;
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        if (this.isActive()) {
            boolean changed = this.fButtonSkipBackward.selectOnMouseOver(pMouseEvent);
            changed |= this.fButtonFastBackward.selectOnMouseOver(pMouseEvent);
            changed |= this.fButtonPlayBackward.selectOnMouseOver(pMouseEvent);
            changed |= this.fButtonPause.selectOnMouseOver(pMouseEvent);
            changed |= this.fButtonPlayForward.selectOnMouseOver(pMouseEvent);
            changed |= this.fButtonFastForward.selectOnMouseOver(pMouseEvent);
            if (changed |= this.fButtonSkipForward.selectOnMouseOver(pMouseEvent)) {
                this.refresh();
                this.repaint();
            }
        }
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
    public void mouseExited(MouseEvent pMouseEvent) {
        this.deSelectAll();
        this.refresh();
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
        if (this.isActive()) {
            boolean changed = this.fButtonSkipBackward.activateOnMouseOver(pMouseEvent);
            changed |= this.fButtonFastBackward.activateOnMouseOver(pMouseEvent);
            changed |= this.fButtonPlayBackward.activateOnMouseOver(pMouseEvent);
            changed |= this.fButtonPause.activateOnMouseOver(pMouseEvent);
            changed |= this.fButtonPlayForward.activateOnMouseOver(pMouseEvent);
            changed |= this.fButtonFastForward.activateOnMouseOver(pMouseEvent);
            if (changed |= this.fButtonSkipForward.activateOnMouseOver(pMouseEvent)) {
                this.refresh();
                this.repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {

    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    private class ReplayButton {
        private boolean fActive;
        private boolean fSelected;
        private String fIconProperty;
        private String fIconPropertyActive;
        private String fIconPropertySelected;
        private Point fPosition;

        public ReplayButton(Point pPosition, String pIconProperty, String pIconPropertyActive, String pIconPropertySelected) {
            this.fPosition = pPosition;
            this.fIconProperty = pIconProperty;
            this.fIconPropertyActive = pIconPropertyActive;
            this.fIconPropertySelected = pIconPropertySelected;
        }

        public boolean isActive() {
            return this.fActive;
        }

        public void setActive(boolean pActive) {
            this.fActive = pActive;
        }

        public boolean isSelected() {
            return this.fSelected;
        }

        public void setSelected(boolean pSelected) {
            this.fSelected = pSelected;
        }

        public Point getPosition() {
            return this.fPosition;
        }

        public void draw(Graphics2D pGraphics2D) {
            IconCache iconCache = ReplayControl.this.getClient().getUserInterface().getIconCache();
            BufferedImage icon = this.isActive() ? iconCache.getIconByProperty(this.fIconPropertyActive) : (this.isSelected() ? iconCache.getIconByProperty(this.fIconPropertySelected) : iconCache.getIconByProperty(this.fIconProperty));
            pGraphics2D.drawImage(icon, this.fPosition.x, this.fPosition.y, null);
        }

        public boolean selectOnMouseOver(MouseEvent pMouseEvent) {
            boolean changed = false;
            if (this.isMouseOver(pMouseEvent)) {
                if (!this.isSelected()) {
                    this.setSelected(true);
                    changed = true;
                }
            } else if (this.isSelected()) {
                this.setSelected(false);
                changed = true;
            }
            return changed;
        }

        public boolean isMouseOver(MouseEvent pMouseEvent) {
            return pMouseEvent.getX() >= this.fPosition.x - 5 && pMouseEvent.getX() < this.fPosition.x + 36 + 5;
        }

        public boolean activateOnMouseOver(MouseEvent pMouseEvent) {
            boolean changed = false;
            if (this.isMouseOver(pMouseEvent) && !this.isActive()) {
                this.setActive(true);
                changed = true;
            }
            return changed;
        }
    }

}

