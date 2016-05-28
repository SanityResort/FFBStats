/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.Inducement;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.ResourceSlot;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TurnData;
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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class ResourceComponent
extends JPanel {
    public static final int WIDTH = 116;
    public static final int HEIGHT = 168;
    private SideBarComponent fSideBar;
    private BufferedImage fImage;
    private boolean fRefreshNecessary;
    private int fNrOfSlots;
    private ResourceSlot[] fSlots;
    private int fCurrentReRolls;
    private int fCurrentApothecaries;
    private int fCurrentIgor;
    private int fCurrentBribes;
    private int fCurrentBloodweiserBabes;
    private int fCurrentMasterChef;
    private int fCurrentWizard;
    private int fCurrentCards;
    private static final int _SLOT_HEIGHT = 40;
    private static final int _SLOT_WIDTH = 56;
    private static final Font _NUMBER_FONT = new Font("Sans Serif", 1, 16);

    public ResourceComponent(SideBarComponent pSideBar) {
        this.fSideBar = pSideBar;
        this.fImage = new BufferedImage(116, 168, 2);
        this.fSlots = this.createResourceSlots();
        this.fRefreshNecessary = true;
        this.setLayout(null);
        Dimension size = new Dimension(116, 168);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        ToolTipManager.sharedInstance().registerComponent(this);
    }

    private ResourceSlot[] createResourceSlots() {
        ResourceSlot[] resourceSlots = null;
        resourceSlots = this.getSideBar().isHomeSide() ? new ResourceSlot[]{new ResourceSlot(new Rectangle(0, 126, 56, 40)), new ResourceSlot(new Rectangle(58, 126, 56, 40)), new ResourceSlot(new Rectangle(0, 84, 56, 40)), new ResourceSlot(new Rectangle(58, 84, 56, 40)), new ResourceSlot(new Rectangle(0, 42, 56, 40)), new ResourceSlot(new Rectangle(58, 42, 56, 40)), new ResourceSlot(new Rectangle(0, 0, 56, 40)), new ResourceSlot(new Rectangle(58, 0, 56, 40))} : new ResourceSlot[]{new ResourceSlot(new Rectangle(58, 126, 56, 40)), new ResourceSlot(new Rectangle(0, 126, 56, 40)), new ResourceSlot(new Rectangle(58, 84, 56, 40)), new ResourceSlot(new Rectangle(0, 84, 56, 40)), new ResourceSlot(new Rectangle(58, 42, 56, 40)), new ResourceSlot(new Rectangle(0, 42, 56, 40)), new ResourceSlot(new Rectangle(58, 0, 56, 40)), new ResourceSlot(new Rectangle(0, 0, 56, 40))};
        return resourceSlots;
    }

    public SideBarComponent getSideBar() {
        return this.fSideBar;
    }

    private void drawBackground() {
        Graphics2D g2d = this.fImage.createGraphics();
        IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
        BufferedImage background = this.getSideBar().isHomeSide() ? iconCache.getIconByProperty("sidebar.background.resource.red") : iconCache.getIconByProperty("sidebar.background.resource.blue");
        g2d.drawImage(background, 0, 0, null);
        g2d.dispose();
    }

    private void drawSlot(ResourceSlot pSlot) {
        if (pSlot != null && pSlot.getIconProperty() != null) {
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            Graphics2D g2d = this.fImage.createGraphics();
            int x = pSlot.getLocation().x;
            int y = pSlot.getLocation().y;
            BufferedImage resourceIcon = iconCache.getIconByProperty(pSlot.getIconProperty());
            x = this.getSideBar().isHomeSide() ? (x += pSlot.getLocation().width - resourceIcon.getWidth() - 1) : ++x;
            g2d.drawImage(resourceIcon, x, y += (pSlot.getLocation().height - resourceIcon.getHeight() + 1) / 2, null);
            if (!pSlot.isEnabled()) {
                BufferedImage disabledIcon = iconCache.getIconByProperty("decoration.stunned");
                g2d.drawImage(disabledIcon, x += (resourceIcon.getWidth() - disabledIcon.getWidth()) / 2, y += (resourceIcon.getHeight() - disabledIcon.getHeight()) / 2, null);
            }
            g2d.setFont(_NUMBER_FONT);
            String resourceValue = Integer.toString(pSlot.getValue());
            FontMetrics metrics = g2d.getFontMetrics();
            Rectangle2D bounds = metrics.getStringBounds(resourceValue, g2d);
            y = pSlot.getLocation().y + (pSlot.getLocation().height + metrics.getHeight()) / 2 - metrics.getDescent();
            x = this.getSideBar().isHomeSide() ? pSlot.getLocation().x + 3 : pSlot.getLocation().x + pSlot.getLocation().width - (int)bounds.getWidth() - 3;
            g2d.setColor(Color.BLACK);
            g2d.drawString(resourceValue, x + 1, y + 1);
            g2d.setColor(Color.WHITE);
            g2d.drawString(resourceValue, x, y);
            g2d.dispose();
        }
    }

    private void updateSlots() {
        Inducement bloodweiserBabes;
        Inducement igor;
        Card[] availableCards;
        Inducement masterChef;
        Inducement bribes;
        Inducement wizard;
        int slotIndex = 0;
        Game game = this.getSideBar().getClient().getGame();
        TurnData turnData = this.getSideBar().isHomeSide() ? game.getTurnDataHome() : game.getTurnDataAway();
        Team team = this.getSideBar().isHomeSide() ? game.getTeamHome() : game.getTeamAway();
        this.fRefreshNecessary |= turnData.getReRolls() != this.fCurrentReRolls;
        this.fCurrentReRolls = turnData.getReRolls();
        if (team.getReRolls() > 0 || turnData.getReRolls() > 0) {
            ResourceSlot reRollSlot = this.fSlots[slotIndex++];
            reRollSlot.setType(1);
            this.fRefreshNecessary |= turnData.isReRollUsed() == reRollSlot.isEnabled();
            reRollSlot.setEnabled(!turnData.isReRollUsed());
            reRollSlot.setValue(this.fCurrentReRolls);
            reRollSlot.setIconProperty("resource.reRoll");
        }
        this.fRefreshNecessary |= turnData.getApothecaries() != this.fCurrentApothecaries;
        this.fCurrentApothecaries = turnData.getApothecaries();
        if (team.getApothecaries() > 0 || turnData.getApothecaries() > 0) {
            ResourceSlot apothecarySlot = this.fSlots[slotIndex++];
            apothecarySlot.setType(2);
            apothecarySlot.setValue(this.fCurrentApothecaries);
            apothecarySlot.setIconProperty("resource.apothecary");
        }
        if ((igor = turnData.getInducementSet().get(InducementType.IGOR)) != null) {
            this.fRefreshNecessary |= igor.getValue() - igor.getUses() != this.fCurrentIgor;
            this.fCurrentIgor = igor.getValue() - igor.getUses();
            if (this.fCurrentIgor > 0) {
                ResourceSlot igorSlot = this.fSlots[slotIndex++];
                igorSlot.setType(6);
                igorSlot.setValue(this.fCurrentIgor);
                igorSlot.setIconProperty("resource.igor");
            }
        }
        if ((bribes = turnData.getInducementSet().get(InducementType.BRIBES)) != null) {
            this.fRefreshNecessary |= bribes.getValue() - bribes.getUses() != this.fCurrentBribes;
            this.fCurrentBribes = bribes.getValue() - bribes.getUses();
            if (this.fCurrentBribes > 0) {
                ResourceSlot bribesSlot = this.fSlots[slotIndex++];
                bribesSlot.setType(3);
                bribesSlot.setValue(this.fCurrentBribes);
                bribesSlot.setIconProperty("resource.bribe");
            }
        }
        if ((bloodweiserBabes = turnData.getInducementSet().get(InducementType.BLOODWEISER_BABES)) != null) {
            this.fRefreshNecessary |= bloodweiserBabes.getValue() - bloodweiserBabes.getUses() != this.fCurrentBloodweiserBabes;
            this.fCurrentBloodweiserBabes = bloodweiserBabes.getValue() - bloodweiserBabes.getUses();
            if (this.fCurrentBloodweiserBabes > 0) {
                ResourceSlot bloodweiserBabesSlot = this.fSlots[slotIndex++];
                bloodweiserBabesSlot.setType(4);
                bloodweiserBabesSlot.setValue(this.fCurrentBloodweiserBabes);
                bloodweiserBabesSlot.setIconProperty("resource.bloodweiserBabe");
            }
        }
        if ((masterChef = turnData.getInducementSet().get(InducementType.MASTER_CHEF)) != null) {
            this.fRefreshNecessary |= masterChef.getValue() - masterChef.getUses() != this.fCurrentMasterChef;
            this.fCurrentMasterChef = masterChef.getValue() - masterChef.getUses();
            if (this.fCurrentMasterChef > 0) {
                ResourceSlot masterChefSlot = this.fSlots[slotIndex++];
                masterChefSlot.setType(5);
                masterChefSlot.setValue(this.fCurrentMasterChef);
                masterChefSlot.setIconProperty("resource.masterChef");
            }
        }
        if ((wizard = turnData.getInducementSet().get(InducementType.WIZARD)) != null) {
            this.fRefreshNecessary |= wizard.getValue() - wizard.getUses() != this.fCurrentWizard;
            this.fCurrentWizard = wizard.getValue() - wizard.getUses();
            if (this.fCurrentWizard > 0) {
                ResourceSlot wizardSlot = this.fSlots[slotIndex++];
                wizardSlot.setType(7);
                wizardSlot.setValue(this.fCurrentWizard);
                wizardSlot.setIconProperty("resource.wizard");
            }
        }
        this.fRefreshNecessary |= (availableCards = turnData.getInducementSet().getAvailableCards()).length != this.fCurrentCards;
        this.fCurrentCards = availableCards.length;
        if (this.fCurrentCards > 0) {
            ResourceSlot bribesSlot = this.fSlots[slotIndex++];
            bribesSlot.setType(8);
            bribesSlot.setValue(this.fCurrentCards);
            bribesSlot.setIconProperty("resource.card");
        }
        this.fNrOfSlots = slotIndex;
    }

    public void init() {
        this.fRefreshNecessary = true;
        this.refresh();
    }

    public void refresh() {
        Game game = this.getSideBar().getClient().getGame();
        if (game.getHalf() > 0) {
            this.updateSlots();
            if (this.fRefreshNecessary) {
                this.drawBackground();
                for (int i = 0; i < this.fNrOfSlots; ++i) {
                    this.drawSlot(this.fSlots[i]);
                }
                this.repaint();
                this.fRefreshNecessary = false;
            }
        } else {
            this.drawBackground();
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    @Override
    public String getToolTipText(MouseEvent pMouseEvent) {
        String toolTip = null;
        for (int i = 0; toolTip == null && i < this.fSlots.length; ++i) {
            if (!this.fSlots[i].getLocation().contains(pMouseEvent.getPoint())) continue;
            toolTip = this.fSlots[i].getToolTip();
        }
        return toolTip;
    }
}

