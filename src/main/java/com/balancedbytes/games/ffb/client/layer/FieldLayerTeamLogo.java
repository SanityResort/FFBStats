/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.StringTool;
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

public class FieldLayerTeamLogo
extends FieldLayer {
    public FieldLayerTeamLogo(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void drawDistanceMarkers() {
        for (int x = 1; x < 25; ++x) {
            this.drawDistanceMarker(x);
        }
    }

    private void drawDistanceMarker(int pX) {
        int distance = pX >= 13 ? 25 - pX : pX;
        String distanceString = Integer.toString(distance);
        Graphics2D g2d = this.getImage().createGraphics();
        g2d.setFont(new Font("Sans Serif", 1, 12));
        FontMetrics metrics = g2d.getFontMetrics();
        Rectangle2D distanceBounds = metrics.getStringBounds(distanceString, g2d);
        Color distanceColor = pX >= 13 ? new Color(120, 120, 255) : new Color(255, 120, 120);
        g2d.setComposite(AlphaComposite.getInstance(3, 0.7f));
        FieldCoordinate upperLine = new FieldCoordinate(pX, 0);
        this.clear(upperLine, true);
        int x = 15 + upperLine.getX() * 30 - (int)(distanceBounds.getWidth() / 2.0) + 1;
        int y = 15 + upperLine.getY() * 30 + (int)(distanceBounds.getHeight() / 2.0) - 8;
        g2d.setColor(Color.BLACK);
        g2d.drawString(distanceString, x + 1, y + 1);
        g2d.setColor(distanceColor);
        g2d.drawString(distanceString, x, y);
        FieldCoordinate lowerLine = new FieldCoordinate(pX, 14);
        this.clear(lowerLine, true);
        x = 15 + lowerLine.getX() * 30 - (int)(distanceBounds.getWidth() / 2.0) + 1;
        y = 15 + lowerLine.getY() * 30 + (int)(distanceBounds.getHeight() / 2.0) + 4;
        g2d.setColor(Color.BLACK);
        g2d.drawString(distanceString, x + 1, y + 1);
        g2d.setColor(distanceColor);
        g2d.drawString(distanceString, x, y);
        g2d.dispose();
    }

    private void drawTeamLogo(Team pTeam, boolean pHomeTeam) {
        BufferedImage teamLogo;
        IconCache iconCache;
        if (pTeam != null && StringTool.isProvided(pTeam.getLogoUrl()) && (teamLogo = (iconCache = this.getClient().getUserInterface().getIconCache()).getIconByUrl(IconCache.findTeamLogoUrl(pTeam))) != null) {
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setComposite(AlphaComposite.getInstance(3, 0.5f));
            int x = pHomeTeam ? 195 - teamLogo.getWidth() / 2 + 15 : 585 - teamLogo.getWidth() / 2 - 15;
            int y = 226 - teamLogo.getHeight() / 2;
            g2d.setClip(pHomeTeam ? 30 : 391, 0, 361, 452);
            g2d.drawImage(teamLogo, x, y, null);
            g2d.dispose();
        }
    }

    @Override
    public void init() {
        String teamLogosSetting;
        this.clear(true);
        Game game = this.getClient().getGame();
        String markingsSetting = this.getClient().getProperty("setting.pitch.markings");
        if ("pitchMarkingsOn".equals(markingsSetting)) {
            this.drawDistanceMarkers();
        }
        if ("teamLogosBoth".equals(teamLogosSetting = this.getClient().getProperty("setting.pitch.teamLogos"))) {
            this.drawTeamLogo(game.getTeamHome(), true);
            this.drawTeamLogo(game.getTeamAway(), false);
        }
        if ("teamLogosOwn".equals(teamLogosSetting)) {
            this.drawTeamLogo(game.getTeamHome(), true);
        }
    }
}

