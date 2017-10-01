/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PlayerMarker;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilUrl;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class PlayerIconFactory {
    private static Color _MARK_COLOR = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    public static final int MAX_ICON_WIDTH = 40;
    public static final int MAX_ICON_HEIGHT = 40;

    public BufferedImage getBasicIcon(FantasyFootballClient pClient, Player pPlayer, boolean pHomePlayer, boolean pMoving, boolean pWithBall, boolean pWithBomb) {
        BufferedImage iconSet;
        if (pClient == null || pPlayer == null) {
            return null;
        }
        Game game = pClient.getGame();
        IconCache iconCache = pClient.getUserInterface().getIconCache();
        String settingIcons = pClient.getProperty("setting.icons");
        BufferedImage icon = null;
        String iconSetUrl = null;
        if (!StringTool.isProvided(settingIcons) || "iconsTeam".equals(settingIcons) || pHomePlayer && "iconsRosterOpponent".equals(settingIcons)) {
            iconSetUrl = PlayerIconFactory.getIconSetUrl(pPlayer);
        }
        if (!StringTool.isProvided(iconSetUrl) || "iconsRoster".equals(settingIcons) || !pHomePlayer && "iconsRosterOpponent".equals(settingIcons)) {
            iconSetUrl = PlayerIconFactory.getIconSetUrl(pPlayer.getPosition());
        }
        if (StringTool.isProvided(iconSetUrl) && (iconSet = iconCache.getIconByUrl(iconSetUrl)) != null) {
            int iconSize = iconSet.getWidth() / 4;
            int y = pPlayer.getIconSetIndex() * iconSize;
            int x = 0;
            x = pHomePlayer ? (pMoving ? 1 : 0) * iconSize : (pMoving ? 3 : 2) * iconSize;
            icon = new BufferedImage(iconSize, iconSize, 2);
            Graphics2D g2d = icon.createGraphics();
            g2d.drawImage(iconSet, 0, 0, iconSize, iconSize, x, y, x + iconSize, y + iconSize, null);
            g2d.dispose();
        }
        if (icon == null || "iconsAbstract".equals(settingIcons)) {
            int fontSize = 0;
            Color fontColor = Color.WHITE;
            Color shadowColor = Color.BLACK;
            BufferedImage playerIcon = null;
            if (pPlayer.getPosition() != null && PlayerType.BIG_GUY == pPlayer.getPosition().getType()) {
                fontSize = 17;
                playerIcon = pHomePlayer ? iconCache.getIconByProperty("players.large.home") : iconCache.getIconByProperty("players.large.away");
            } else if (UtilCards.hasSkill(game, pPlayer, Skill.STUNTY)) {
                fontSize = 13;
                playerIcon = pHomePlayer ? iconCache.getIconByProperty("players.small.home") : iconCache.getIconByProperty("players.small.away");
            } else {
                fontSize = 15;
                playerIcon = pHomePlayer ? iconCache.getIconByProperty("players.normal.home") : iconCache.getIconByProperty("players.normal.away");
            }
            if (pMoving) {
                fontColor = Color.YELLOW;
                shadowColor = null;
            }
            if (playerIcon != null) {
                String shorthand;
                icon = new BufferedImage(playerIcon.getWidth() + 2, playerIcon.getHeight() + 2, 2);
                String string = shorthand = pPlayer.getPosition() != null ? pPlayer.getPosition().getShorthand() : "?";
                if (StringTool.isProvided(shorthand)) {
                    Graphics2D g2d = icon.createGraphics();
                    g2d.drawImage(playerIcon, 2, 2, null);
                    g2d.setFont(new Font("Sans Serif", 1, fontSize));
                    FontMetrics metrics = g2d.getFontMetrics();
                    Rectangle2D stringBounds = metrics.getStringBounds(shorthand, g2d);
                    int baselineX = (icon.getWidth() - (int)stringBounds.getWidth()) / 2;
                    int baselineY = (icon.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
                    if (shadowColor != null) {
                        g2d.setColor(shadowColor);
                        g2d.drawString(shorthand, baselineX + 2, baselineY + 2);
                    }
                    g2d.setColor(fontColor);
                    g2d.drawString(shorthand, baselineX + 1, baselineY + 1);
                    g2d.dispose();
                }
            }
        }
        icon = PlayerIconFactory.decorateIcon(icon, null);
        if (pWithBomb) {
            icon = pMoving ? PlayerIconFactory.decorateIcon(icon, iconCache.getIconByProperty("decoration.bomb.selected")) : PlayerIconFactory.decorateIcon(icon, iconCache.getIconByProperty("decoration.bomb"));
        }
        if (pWithBall && !pWithBomb) {
            icon = pMoving ? PlayerIconFactory.decorateIcon(icon, iconCache.getIconByProperty("decoration.ball.selected")) : PlayerIconFactory.decorateIcon(icon, iconCache.getIconByProperty("decoration.ball"));
        }
        return icon;
    }

    public BufferedImage getIcon(FantasyFootballClient pClient, Player pPlayer) {
        PlayerMarker playerMarker;
        boolean withBall;
        ActingPlayer actingPlayer;
        BufferedImage icon = null;
        IconCache iconCache = pClient.getUserInterface().getIconCache();
        Game game = pClient.getGame();
        PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
        boolean withBomb = FieldCoordinateBounds.FIELD.isInBounds(playerCoordinate) && playerCoordinate.equals(game.getFieldModel().getBombCoordinate()) && !game.getFieldModel().isBombMoving();
        boolean bl = withBall = FieldCoordinateBounds.FIELD.isInBounds(playerCoordinate) && !game.getFieldModel().isBallMoving() && playerCoordinate.equals(game.getFieldModel().getBallCoordinate());
        if (playerState.getBase() != 16) {
            boolean homePlayer = game.getTeamHome().hasPlayer(pPlayer);
            icon = this.getBasicIcon(pClient, pPlayer, homePlayer, playerState.getBase() == 2, withBall, withBomb);
        }
        boolean fadeIcon = false;
        String decorationProperty1 = null;
        String decorationProperty2 = null;
        if (playerState != null && icon != null) {
            switch (playerState.getBase()) {
                case 15: {
                    fadeIcon = true;
                    break;
                }
                case 1: {
                    fadeIcon = !playerState.isActive();
                    break;
                }
                case 3: {
                    fadeIcon = !playerState.isActive();
                    decorationProperty2 = "decoration.prone";
                    break;
                }
                case 4: 
                case 14: {
                    decorationProperty2 = "decoration.stunned";
                    break;
                }
                case 11: 
                case 12: {
                    if (game.isHomePlaying()) {
                        decorationProperty2 = "decoration.block.home";
                        break;
                    }
                    decorationProperty2 = "decoration.block.away";
                }
            }
        }
        if (playerState.isHypnotized()) {
            decorationProperty1 = "decoration.hypnotized";
        }
        if (playerState.isConfused()) {
            decorationProperty1 = "decoration.confused";
        }
        if (playerState.isRooted()) {
            decorationProperty1 = "decoration.rooted";
        }
        if ((actingPlayer = game.getActingPlayer()).getPlayer() == pPlayer && actingPlayer.isSufferingBloodLust()) {
            decorationProperty1 = "decoration.bloodlust";
        }
        if (decorationProperty1 != null) {
            icon = PlayerIconFactory.decorateIcon(icon, iconCache.getIconByProperty(decorationProperty1));
        }
        if (decorationProperty2 != null) {
            icon = PlayerIconFactory.decorateIcon(icon, iconCache.getIconByProperty(decorationProperty2));
        }
        if (fadeIcon) {
            icon = PlayerIconFactory.fadeIcon(icon);
        }
        if ((playerMarker = game.getFieldModel().getPlayerMarker(pPlayer.getId())) != null && ClientMode.PLAYER == pClient.getMode()) {
            PlayerIconFactory.markIcon(icon, playerMarker.getHomeText());
        }
        return icon;
    }

    public static BufferedImage fadeIcon(BufferedImage pIcon) {
        BufferedImage resultingIcon = null;
        if (pIcon != null) {
            resultingIcon = new BufferedImage(pIcon.getWidth(), pIcon.getHeight(), 2);
            Graphics2D g2d = resultingIcon.createGraphics();
            g2d.setComposite(AlphaComposite.getInstance(3, 0.7f));
            g2d.drawImage(pIcon, 0, 0, null);
            g2d.dispose();
        }
        return resultingIcon;
    }

    public static BufferedImage decorateIcon(BufferedImage pIcon, BufferedImage pDecoration) {
        int x;
        int y;
        BufferedImage resultingIcon = new BufferedImage(40, 40, 2);
        Graphics2D g2d = resultingIcon.createGraphics();
        if (pIcon != null) {
            x = (resultingIcon.getWidth() - pIcon.getWidth()) / 2;
            y = (resultingIcon.getHeight() - pIcon.getHeight()) / 2;
            g2d.drawImage(pIcon, x, y, null);
        }
        if (pDecoration != null) {
            x = (resultingIcon.getWidth() - pDecoration.getWidth()) / 2;
            y = (resultingIcon.getHeight() - pDecoration.getHeight()) / 2;
            g2d.drawImage(pDecoration, x, y, null);
        }
        g2d.dispose();
        return resultingIcon;
    }

    public static void markIcon(BufferedImage pIcon, String pText) {
        if (pIcon != null && StringTool.isProvided(pText)) {
            Graphics2D g2d = pIcon.createGraphics();
            g2d.setColor(_MARK_COLOR);
            g2d.setFont(new Font("Sans Serif", 1, 12));
            FontMetrics metrics = g2d.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(pText, g2d);
            int x = (int)(((double)pIcon.getWidth() - textBounds.getWidth()) / 2.0);
            int y = pIcon.getHeight() - metrics.getDescent();
            g2d.drawString(pText, x, y);
            g2d.dispose();
        }
    }

    public static String getPortraitUrl(Player pPlayer) {
        if (pPlayer != null) {
            if (StringTool.isProvided(pPlayer.getUrlPortrait())) {
                return PlayerIconFactory.getIconUrl(pPlayer, pPlayer.getUrlPortrait());
            }
            return PlayerIconFactory.getPortraitUrl(pPlayer.getPosition());
        }
        return null;
    }

    public static String getPortraitUrl(RosterPosition pPosition) {
        if (pPosition != null) {
            return PlayerIconFactory.getIconUrl(pPosition, pPosition.getUrlPortrait());
        }
        return null;
    }

    public static String getIconSetUrl(Player pPlayer) {
        if (pPlayer != null) {
            if (StringTool.isProvided(pPlayer.getUrlIconSet())) {
                return PlayerIconFactory.getIconUrl(pPlayer, pPlayer.getUrlIconSet());
            }
            return PlayerIconFactory.getIconSetUrl(pPlayer.getPosition());
        }
        return null;
    }

    public static String getIconSetUrl(RosterPosition pPosition) {
        if (pPosition != null) {
            return PlayerIconFactory.getIconUrl(pPosition, pPosition.getUrlIconSet());
        }
        return null;
    }

    private static String getIconUrl(Player pPlayer, String pRelativeUrl) {
        Team team;
        if (pPlayer != null && StringTool.isProvided(pRelativeUrl) && (team = pPlayer.getTeam()) != null) {
            return UtilUrl.createUrl(team.getBaseIconPath(), pRelativeUrl);
        }
        return pRelativeUrl;
    }

    private static String getIconUrl(RosterPosition pPosition, String pRelativeUrl) {
        Roster roster;
        if (pPosition != null && StringTool.isProvided(pRelativeUrl) && (roster = pPosition.getRoster()) != null) {
            return UtilUrl.createUrl(roster.getBaseIconPath(), pRelativeUrl);
        }
        return pRelativeUrl;
    }
}

