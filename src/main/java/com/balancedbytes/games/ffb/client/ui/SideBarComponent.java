/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.BoxType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.BoxButtonComponent;
import com.balancedbytes.games.ffb.client.ui.BoxComponent;
import com.balancedbytes.games.ffb.client.ui.PlayerDetailComponent;
import com.balancedbytes.games.ffb.client.ui.ResourceComponent;
import com.balancedbytes.games.ffb.client.ui.TurnDiceStatusComponent;
import com.balancedbytes.games.ffb.model.Player;
import com.fumbbl.rng.MouseEntropySource;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SideBarComponent
extends JPanel
implements MouseMotionListener {
    public static final int WIDTH = 116;
    public static final int HEIGHT = 712;
    private FantasyFootballClient fClient;
    private boolean fHomeSide;
    private PlayerDetailComponent fPlayerDetail;
    private BoxComponent fBoxComponent;
    private BoxButtonComponent fBoxButtons;
    private ResourceComponent fResourceComponent;
    private TurnDiceStatusComponent fTurnDiceStatusComponent;

    public SideBarComponent(FantasyFootballClient pClient, boolean pHomeSide) {
        this.fClient = pClient;
        this.fHomeSide = pHomeSide;
        this.fPlayerDetail = new PlayerDetailComponent(this);
        this.fBoxComponent = new BoxComponent(this);
        this.fBoxButtons = new BoxButtonComponent(this);
        this.fResourceComponent = new ResourceComponent(this);
        this.fTurnDiceStatusComponent = new TurnDiceStatusComponent(this);
        this.setLayout(new BoxLayout(this, 1));
        this.addComponents();
        Dimension size = new Dimension(116, 712);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.fPlayerDetail.addMouseMotionListener(this);
        this.fBoxComponent.addMouseMotionListener(this);
        this.fBoxButtons.addMouseMotionListener(this);
        this.fResourceComponent.addMouseMotionListener(this);
        this.fTurnDiceStatusComponent.addMouseMotionListener(this);
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public BoxComponent getBoxComponent() {
        return this.fBoxComponent;
    }

    public TurnDiceStatusComponent getTurnDiceStatusComponent() {
        return this.fTurnDiceStatusComponent;
    }

    public boolean isHomeSide() {
        return this.fHomeSide;
    }

    public BoxType getOpenBox() {
        return this.fBoxComponent.getOpenBox();
    }

    public void openBox(BoxType pBox) {
        this.fBoxButtons.openBox(pBox);
        if (this.isBoxOpen()) {
            this.fBoxComponent.openBox(pBox);
        } else {
            this.fBoxComponent.openBox(pBox);
            this.removeAll();
            this.addComponents();
            this.revalidate();
        }
        this.fBoxComponent.refresh();
        UserInterface userInterface = this.getClient().getUserInterface();
        SideBarComponent otherSideBar = this.isHomeSide() ? userInterface.getSideBarAway() : userInterface.getSideBarHome();
        otherSideBar.closeBox();
    }

    public void closeBox() {
        this.fBoxButtons.closeBox();
        if (this.isBoxOpen()) {
            this.fBoxComponent.closeBox();
            this.removeAll();
            this.addComponents();
            this.revalidate();
        } else {
            this.fBoxComponent.closeBox();
        }
        this.fPlayerDetail.refresh();
    }

    private void addComponents() {
        if (this.isBoxOpen()) {
            this.add(this.fBoxComponent);
        } else {
            this.add(this.fPlayerDetail);
        }
        this.add(this.fBoxButtons);
        this.add(this.fTurnDiceStatusComponent);
        this.add(this.fResourceComponent);
    }

    public boolean isBoxOpen() {
        return this.fBoxComponent.getOpenBox() != null;
    }

    public void init() {
        this.fBoxButtons.refresh();
        if (this.isBoxOpen()) {
            this.fBoxComponent.refresh();
        } else {
            this.fPlayerDetail.refresh();
        }
        this.fResourceComponent.init();
        this.fTurnDiceStatusComponent.init();
    }

    public void refresh() {
        this.fBoxButtons.refresh();
        if (this.isBoxOpen()) {
            this.fBoxComponent.refresh();
        } else {
            this.fPlayerDetail.refresh();
        }
        this.fResourceComponent.refresh();
        this.fTurnDiceStatusComponent.refresh();
    }

    public void updatePlayer(Player pPlayer) {
        this.fPlayerDetail.setPlayer(pPlayer);
        this.fPlayerDetail.refresh();
    }

    public Player getPlayer() {
        return this.fPlayerDetail.getPlayer();
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }
}

