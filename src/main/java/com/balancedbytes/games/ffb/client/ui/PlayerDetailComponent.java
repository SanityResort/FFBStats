/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.InjuryAttribute;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;

public class PlayerDetailComponent
extends JPanel {
    public static final int WIDTH = 116;
    public static final int HEIGHT = 430;
    private static final int _PORTRAIT_WIDTH = 95;
    private static final int _PORTRAIT_HEIGHT = 147;
    private static final int _STAT_BOX_WIDTH = 28;
    private static final int _STAT_BOX_HEIGHT = 29;
    private static final int _STAT_BOX_INNER_HEIGHT = 14;
    private static final Font _NAME_FONT = new Font("Sans Serif", 0, 12);
    private static final Font _STAT_FONT = new Font("Sans Serif", 1, 13);
    private static final Font _POSITION_FONT = new Font("Sans Serif", 0, 11);
    private static final Font _SPP_FONT = new Font("Sans Serif", 1, 11);
    private static final Font _SKILL_FONT = new Font("Sans Serif", 1, 11);
    private static final Font _SKILL_USED_FONT = new Font("Sans Serif", 3, 11);
    private static final int _DISPLAY_NONE = 0;
    private static final int _DISPLAY_ACTING_PLAYER = 1;
    private static final int _DISPLAY_DEFENDING_PLAYER = 2;
    private static final int _DISPLAY_SELECTED_PLAYER = 3;
    private SideBarComponent fSideBar;
    private Player fPlayer;
    private BufferedImage fImage;
    private boolean fRefreshNecessary;

    public PlayerDetailComponent(SideBarComponent pSideBar) {
        this.fSideBar = pSideBar;
        this.fImage = new BufferedImage(116, 430, 2);
        this.setLayout(null);
        Dimension size = new Dimension(116, 430);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.fRefreshNecessary = true;
    }

    private void drawBackground() {
        Graphics2D g2d = this.fImage.createGraphics();
        IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
        BufferedImage background = this.getSideBar().isHomeSide() ? iconCache.getIconByProperty("sidebar.background.player.detail.red") : iconCache.getIconByProperty("sidebar.background.player.detail.blue");
        g2d.drawImage(background, 0, 0, null);
        if (this.fPlayer != null) {
            Game game = this.getSideBar().getClient().getGame();
            BufferedImage overlay = this.fPlayer.getTeam() == null || game.getTeamHome().hasPlayer(this.fPlayer) ? iconCache.getIconByProperty("sidebar.overlay.player.detail.red") : iconCache.getIconByProperty("sidebar.overlay.player.detail.blue");
            g2d.drawImage(overlay, 0, 0, null);
        }
        g2d.dispose();
    }

    private void drawPlayerName() {
        if (this.fPlayer != null) {
            int x = 3;
            int y = 1;
            Graphics2D g2d = this.fImage.createGraphics();
            g2d.setFont(_NAME_FONT);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            AttributedString attStr = new AttributedString(this.getPlayer().getName());
            attStr.addAttribute(TextAttribute.FONT, g2d.getFont());
            LineBreakMeasurer measurer = new LineBreakMeasurer(attStr.getIterator(), new FontRenderContext(null, false, true));
            TextLayout layoutLine1 = measurer.nextLayout(116 - 2 * x);
            if (layoutLine1 != null) {
                int yLine1 = y + fontMetrics.getHeight() - fontMetrics.getDescent();
                int yLine2 = yLine1 + fontMetrics.getHeight() - 1;
                TextLayout layoutLine2 = measurer.nextLayout(116 - 2 * x);
                if (layoutLine2 != null) {
                    this.drawShadowedLayout(g2d, layoutLine1, x, yLine1);
                    this.drawShadowedLayout(g2d, layoutLine2, x, yLine2);
                } else {
                    this.drawShadowedLayout(g2d, layoutLine1, x, yLine2);
                }
            }
        }
    }

    private void drawPlayerPortraitAndPosition() {
        if (this.fPlayer != null) {
            int x = 3;
            int y = 32;
            Graphics2D g2d = this.fImage.createGraphics();
            String portraitUrl = PlayerIconFactory.getPortraitUrl(this.getPlayer());
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            StringBuilder positionName = new StringBuilder();
            if (this.getPlayer() != null) {
                if (this.getPlayer().getPlayerType() == PlayerType.STAR) {
                    positionName.append("Star Player");
                } else if (StringTool.isProvided(this.getPlayer().getPosition().getDisplayName())) {
                    positionName.append(this.getPlayer().getPosition().getDisplayName());
                } else {
                    positionName.append(this.getPlayer().getPosition().getName());
                }
                positionName.append(" #").append(this.getPlayer().getNr());
            }
            String positionNameString = positionName.toString();
            g2d.setFont(_POSITION_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            BufferedImage playerPortrait = iconCache.getIconByUrl(portraitUrl);
            BufferedImage portraitBackground = iconCache.getIconByProperty("sidebar.background.player.portrait");
            if (playerPortrait != null) {
                g2d.drawImage(playerPortrait, x, y, 95, 147, null);
            } else {
                g2d.drawImage(portraitBackground, x - 2, y, null);
            }
            g2d.rotate(-1.5707963267948966);
            g2d.setColor(Color.BLACK);
            g2d.drawString(positionNameString, - y + 147 - 4, 95 + metrics.getAscent() + 3);
            g2d.setColor(Color.WHITE);
            g2d.drawString(positionNameString, - y + 147 - 5, 95 + metrics.getAscent() + 2);
            g2d.dispose();
        }
    }

    private void drawPlayerStats() {
        if (this.fPlayer != null) {
            int x = 3;
            int y = 179;
            Graphics2D g2d = this.fImage.createGraphics();
            Game game = this.getSideBar().getClient().getGame();
            PlayerResult playerResult = game.getGameResult().getPlayerResult(this.getPlayer());
            boolean moveIsRed = false;
            int moveLeft = UtilCards.getPlayerMovement(game, this.getPlayer()) - this.findNewStatDecreases(playerResult, InjuryAttribute.MA);
            int strength = UtilCards.getPlayerStrength(game, this.getPlayer()) - this.findNewStatDecreases(playerResult, InjuryAttribute.ST);
            int agility = this.getPlayer().getAgility() - this.findNewStatDecreases(playerResult, InjuryAttribute.AG);
            int armour = this.getPlayer().getArmour() - this.findNewStatDecreases(playerResult, InjuryAttribute.AV);
            ActingPlayer actingPlayer = this.getSideBar().getClient().getGame().getActingPlayer();
            if (this.fPlayer == actingPlayer.getPlayer() && actingPlayer.isGoingForIt() && (moveLeft -= actingPlayer.getCurrentMove()) <= 0) {
                moveIsRed = true;
                moveLeft = UtilCards.hasSkill(game, this.getPlayer(), Skill.SPRINT) ? 3 + moveLeft : 2 + moveLeft;
            }
            int maIncreases = this.findStatIncreases(Skill.MOVEMENT_INCREASE);
            int maDecreases = this.findStatDecreases(InjuryAttribute.MA) + this.findStatDecreases(Skill.MOVEMENT_DECREASE);
            if (maIncreases > 0 && maDecreases > 0) {
                maIncreases = 1;
                maDecreases = 1;
            }
            this.drawStatBox(g2d, x, y, moveLeft, moveIsRed, maIncreases, maDecreases);
            int stIncreases = this.findStatIncreases(Skill.STRENGTH_INCREASE);
            int stDecreases = this.findStatDecreases(InjuryAttribute.ST) + this.findStatDecreases(Skill.STRENGTH_DECREASE);
            if (stIncreases > 0 && stDecreases > 0) {
                stIncreases = 1;
                stDecreases = 1;
            }
            boolean strengthIsRed = this.getPlayer().getStrength() != UtilCards.getPlayerStrength(game, this.getPlayer());
            this.drawStatBox(g2d, x + 28, y, strength, strengthIsRed, stIncreases, stDecreases);
            int agIncreases = this.findStatIncreases(Skill.AGILITY_INCREASE);
            int agDecreases = this.findStatDecreases(InjuryAttribute.AG) + this.findStatDecreases(Skill.AGILITY_DECREASE);
            if (agIncreases > 0 && agDecreases > 0) {
                agIncreases = 1;
                agDecreases = 1;
            }
            this.drawStatBox(g2d, x + 56, y, agility, false, agIncreases, agDecreases);
            int avIncreases = this.findStatIncreases(Skill.ARMOUR_INCREASE);
            int avDecreases = this.findStatDecreases(InjuryAttribute.AV) + this.findStatDecreases(Skill.ARMOUR_DECREASE);
            if (avIncreases > 0 && avDecreases > 0) {
                avIncreases = 1;
                avDecreases = 1;
            }
            this.drawStatBox(g2d, x + 84, y, armour, false, avIncreases, avDecreases);
            g2d.dispose();
        }
    }

    private int findStatIncreases(Skill pSkill) {
        int increases = 0;
        if (this.getPlayer() != null && pSkill != null) {
            Game game = this.getSideBar().getClient().getGame();
            for (Skill skill : UtilCards.findAllSkills(game, this.getPlayer())) {
                if (skill != pSkill) continue;
                ++increases;
            }
        }
        return Math.min(2, increases);
    }

    private int findStatDecreases(Skill pSkill) {
        int decreases = 0;
        if (this.getPlayer() != null && pSkill != null) {
            Game game = this.getSideBar().getClient().getGame();
            for (Skill skill : UtilCards.findAllSkills(game, this.getPlayer())) {
                if (skill != pSkill) continue;
                ++decreases;
            }
        }
        return decreases;
    }

    private int findNewStatDecreases(PlayerResult pPlayerResult, InjuryAttribute pInjuryAttribute) {
        int decreases = 0;
        if (pPlayerResult != null) {
            if (pPlayerResult.getSeriousInjury() != null && pPlayerResult.getSeriousInjury().getInjuryAttribute() == pInjuryAttribute) {
                ++decreases;
            }
            if (pPlayerResult.getSeriousInjuryDecay() != null && pPlayerResult.getSeriousInjuryDecay().getInjuryAttribute() == pInjuryAttribute) {
                ++decreases;
            }
        }
        return decreases;
    }

    private int findStatDecreases(InjuryAttribute pInjuryAttribute) {
        int decreases = 0;
        if (this.getPlayer() != null && pInjuryAttribute != null) {
            for (SeriousInjury injury : this.getPlayer().getLastingInjuries()) {
                if (pInjuryAttribute != injury.getInjuryAttribute()) continue;
                ++decreases;
            }
            Game game = this.getSideBar().getClient().getGame();
            PlayerResult playerResult = game.getGameResult().getPlayerResult(this.getPlayer());
            if (playerResult != null && (playerResult.getSeriousInjury() != null && pInjuryAttribute == playerResult.getSeriousInjury().getInjuryAttribute() || playerResult.getSeriousInjuryDecay() != null && pInjuryAttribute == playerResult.getSeriousInjuryDecay().getInjuryAttribute())) {
                ++decreases;
            }
        }
        if (pInjuryAttribute != InjuryAttribute.NI) {
            return Math.min(2, decreases);
        }
        return decreases;
    }

    private void drawNigglingInjuries() {
        int nigglingInjuries = this.findStatDecreases(InjuryAttribute.NI);
        if (nigglingInjuries > 0) {
            Graphics2D g2d = this.fImage.createGraphics();
            int x = 9;
            int y = 36;
            for (int i = 0; i < nigglingInjuries; ++i) {
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + i * 12 + 1, y + 1, 10, 10);
                g2d.setColor(Color.RED);
                g2d.fillOval(x + i * 12, y, 10, 10);
            }
            g2d.dispose();
        }
    }

    private void drawCardOnPlayer() {
        Game game = this.getSideBar().getClient().getGame();
        if (ArrayTool.isProvided(game.getFieldModel().getCards(this.getPlayer()))) {
            Graphics2D g2d = this.fImage.createGraphics();
            IconCache iconCache = this.getSideBar().getClient().getUserInterface().getIconCache();
            BufferedImage overlayCard = iconCache.getIconByProperty("sidebar.overlay.player.card");
            g2d.drawImage(overlayCard, 76, 36, null);
            g2d.dispose();
        }
    }

    private void drawPlayerSpps() {
        if (this.fPlayer != null) {
            int x = 8;
            int y = 222;
            Graphics2D g2d = this.fImage.createGraphics();
            g2d.setFont(_SPP_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            Game game = this.getSideBar().getClient().getGame();
            PlayerResult playerResult = game.getGameResult().getPlayerResult(this.getPlayer());
            StringBuilder sppInfo = new StringBuilder();
            if (playerResult != null && this.getPlayer() != null) {
                if (this.getPlayer().getPlayerType() == PlayerType.STAR) {
                    RosterPosition position = this.getPlayer().getPosition();
                    sppInfo.append(StringTool.formatThousands(position.getCost())).append(" gold");
                } else {
                    int oldSpps = playerResult.getCurrentSpps();
                    int newSpps = playerResult.totalEarnedSpps();
                    sppInfo.append(oldSpps);
                    if (newSpps > 0) {
                        sppInfo.append("+").append(newSpps);
                    }
                    if (oldSpps > 175) {
                        sppInfo.append(" Legend");
                    } else if (oldSpps > 75) {
                        sppInfo.append(" Super Star");
                    } else if (oldSpps > 50) {
                        sppInfo.append(" Star");
                    } else if (oldSpps > 30) {
                        sppInfo.append(" Emerging");
                    } else if (oldSpps > 15) {
                        sppInfo.append(" Veteran");
                    } else if (oldSpps > 5) {
                        sppInfo.append(" Experienced");
                    } else {
                        sppInfo.append(" Rookie");
                    }
                }
            }
            g2d.setColor(Color.BLACK);
            g2d.drawString(sppInfo.toString(), x, y + metrics.getAscent());
            g2d.dispose();
        }
    }

    private void drawPlayerSkills() {
        if (this.fPlayer != null) {
            int x = 8;
            int y = 246;
            Graphics2D g2d = this.fImage.createGraphics();
            Game game = this.getSideBar().getClient().getGame();
            ActingPlayer actingPlayer = game.getActingPlayer();
            PlayerState playerState = game.getFieldModel().getPlayerState(this.getPlayer());
            Skill[] skills = UtilCards.findAllSkills(game, this.getPlayer());
            ArrayList<String> cardSkills = new ArrayList<String>();
            ArrayList<String> acquiredSkills = new ArrayList<String>();
            ArrayList<String> rosterSkills = new ArrayList<String>();
            HashSet<String> usedSkills = new HashSet<String>();
            for (Skill skill : skills) {
                if (this.getPlayer().getPosition().hasSkill(skill)) {
                    if (SkillCategory.STAT_INCREASE != skill.getCategory() && SkillCategory.STAT_DECREASE != skill.getCategory()) {
                        rosterSkills.add(skill.getName());
                    }
                } else if (this.getPlayer().hasSkill(skill)) {
                    if (SkillCategory.STAT_INCREASE != skill.getCategory() && SkillCategory.STAT_DECREASE != skill.getCategory()) {
                        acquiredSkills.add(skill.getName());
                    }
                } else {
                    cardSkills.add(skill.getName());
                }
                if ((this.getPlayer() != actingPlayer.getPlayer() || !actingPlayer.isSkillUsed(skill)) && (skill != Skill.PRO || !playerState.hasUsedPro())) continue;
                usedSkills.add(skill.getName());
            }
            for (Card card : game.getFieldModel().getCards(this.getPlayer())) {
                cardSkills.add(card.getShortName());
            }
            int height = 0;
            if (cardSkills.size() > 0) {
                g2d.setColor(new Color(220, 0, 0));
                height += this.drawPlayerSkills(g2d, x, y + height, cardSkills, usedSkills) + 2;
            }
            if (acquiredSkills.size() > 0) {
                g2d.setColor(new Color(0, 96, 0));
                height += this.drawPlayerSkills(g2d, x, y + height, acquiredSkills, usedSkills) + 2;
            }
            g2d.setColor(Color.BLACK);
            this.drawPlayerSkills(g2d, x, y + height, rosterSkills, usedSkills);
            g2d.dispose();
        }
    }

    private int drawPlayerSkills(Graphics2D pG2d, int pX, int pY, List<String> pSkills, Set<String> pUsedSkills) {
        int height = 0;
        if (pSkills != null && pSkills.size() > 0) {
            int yPos = pY;
            for (String skill : pSkills) {
                if (pUsedSkills.contains(skill)) {
                    pG2d.setFont(_SKILL_USED_FONT);
                } else {
                    pG2d.setFont(_SKILL_FONT);
                }
                FontMetrics metrics = pG2d.getFontMetrics();
                height += metrics.getHeight();
                yPos = yPos > pY ? (yPos += metrics.getHeight()) : (yPos += metrics.getAscent());
                pG2d.drawString(skill, pX, yPos);
            }
        }
        return height;
    }

    private void drawStatBox(Graphics2D pG2d, int pX, int pY, int pValue, boolean pStatIsRed, int pStatIncreases, int pStatDecreases) {
        if (this.fPlayer != null) {
            pG2d.setColor(Color.BLACK);
            pG2d.setFont(_STAT_FONT);
            if (pStatIncreases > 0) {
                pG2d.setColor(Color.GREEN);
                if (pStatIncreases > 1) {
                    pG2d.fillRect(pX + 2, pY + 29 - 14 - 2, 22, 14);
                } else {
                    pG2d.fillPolygon(new int[]{pX + 2, pX + 2, pX + 28 - 3}, new int[]{pY + 29 - 14 - 2, pY + 29 - 2, pY + 29 - 2}, 3);
                }
            }
            if (pStatDecreases > 0) {
                pG2d.setColor(Color.RED);
                if (pStatDecreases > 1) {
                    pG2d.fillRect(pX + 2, pY + 29 - 14 - 2, 22, 14);
                } else {
                    pG2d.fillPolygon(new int[]{pX + 2, pX + 28 - 3, pX + 28 - 3}, new int[]{pY + 29 - 14 - 2, pY + 29 - 14 - 2, pY + 29 - 2}, 3);
                }
            }
            Color statColor = pStatIsRed ? Color.RED : Color.BLACK;
            this.drawCenteredText(pG2d, pX + 14, pY + 29 - 4, statColor, Integer.toString(pValue));
        }
    }

    private void drawCenteredText(Graphics2D pG2d, int pX, int pY, Color pColor, String pText) {
        FontMetrics metrics = pG2d.getFontMetrics();
        Rectangle2D numberBounds = metrics.getStringBounds(pText, pG2d);
        int x = (int)((double)pX - numberBounds.getWidth() / 2.0);
        pG2d.setColor(pColor);
        pG2d.drawString(pText, x, pY);
    }

    private void drawShadowedLayout(Graphics2D pG2d, TextLayout pTextLayout, int pX, int pY) {
        pG2d.setColor(Color.BLACK);
        pTextLayout.draw(pG2d, pX + 1, pY + 1);
        pG2d.setColor(Color.WHITE);
        pTextLayout.draw(pG2d, pX, pY);
    }

    public void refresh() {
        ClientData clientData = this.getSideBar().getClient().getClientData();
        int displayMode = this.findDisplayMode();
        if (!this.fRefreshNecessary) {
            boolean bl = this.fRefreshNecessary = this.getDisplayedPlayer(displayMode) != this.getPlayer();
            if (displayMode == 1 && clientData.isActingPlayerUpdated()) {
                this.fRefreshNecessary = true;
                clientData.setActingPlayerUpdated(false);
            }
        }
        if (this.fRefreshNecessary) {
            this.fPlayer = this.getDisplayedPlayer(displayMode);
            this.drawBackground();
            if (this.fPlayer != null) {
                this.drawPlayerName();
                this.drawPlayerPortraitAndPosition();
                this.drawPlayerStats();
                this.drawNigglingInjuries();
                this.drawCardOnPlayer();
                this.drawPlayerSpps();
                this.drawPlayerSkills();
            }
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    public Player getPlayer() {
        return this.fPlayer;
    }

    public void setPlayer(Player pPlayer) {
        this.fPlayer = pPlayer;
    }

    public SideBarComponent getSideBar() {
        return this.fSideBar;
    }

    private int findDisplayMode() {
        int displayMode = 0;
        if (!this.getSideBar().isBoxOpen()) {
            SideBarComponent otherSideBar;
            Game game = this.getSideBar().getClient().getGame();
            ClientData clientData = this.getSideBar().getClient().getClientData();
            UserInterface userInterface = this.getSideBar().getClient().getUserInterface();
            SideBarComponent sideBarComponent = otherSideBar = this.getSideBar().isHomeSide() ? userInterface.getSideBarAway() : userInterface.getSideBarHome();
            if (otherSideBar.isBoxOpen()) {
                displayMode = 3;
                if (clientData.getSelectedPlayer() == null && game.getActingPlayer().getPlayer() != null) {
                    displayMode = 1;
                }
            } else if (this.getSideBar().isHomeSide() == game.isHomePlaying()) {
                displayMode = 1;
            } else {
                displayMode = 3;
                if (clientData.getSelectedPlayer() == null && game.getDefender() != null) {
                    displayMode = 2;
                }
            }
        }
        return displayMode;
    }

    private Player getDisplayedPlayer(int pDisplayMode) {
        Player displayedPlayer = null;
        Game game = this.getSideBar().getClient().getGame();
        ClientData clientData = this.getSideBar().getClient().getClientData();
        switch (pDisplayMode) {
            case 1: {
                displayedPlayer = game.getActingPlayer().getPlayer();
                break;
            }
            case 2: {
                displayedPlayer = game.getDefender();
                break;
            }
            case 3: {
                displayedPlayer = clientData.getSelectedPlayer();
            }
        }
        return displayedPlayer;
    }
}

