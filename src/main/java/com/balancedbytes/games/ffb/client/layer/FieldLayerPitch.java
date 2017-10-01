/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FieldLayerPitch
extends FieldLayer {
    public FieldLayerPitch(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void drawWeather(Weather pWeather) {
        if (pWeather != null) {
            IconCache iconCache = this.getClient().getUserInterface().getIconCache();
            BufferedImage fieldImage = iconCache.getPitch(this.getClient().getGame(), pWeather);
            this.draw(fieldImage, 0, 0, 1.0f);
            this.drawTeamNames();
        }
    }

    private void drawTeamNames() {
        Game game = this.getClient().getGame();
        String teamNameHome = game.getTeamHome().getName();
        String teamNameAway = game.getTeamAway().getName();
        if (teamNameHome != null && teamNameAway != null) {
            teamNameHome = teamNameHome.toUpperCase();
            teamNameAway = teamNameAway.toUpperCase();
            Graphics2D g2d = this.getGraphicsWithFontAndColor();
            FontMetrics metrics = g2d.getFontMetrics();
            Rectangle2D teamNameBounds = metrics.getStringBounds(teamNameHome, g2d);
            int translateX = (int)(15.0 + teamNameBounds.getHeight() / 2.0) - 4;
            int translateY = (int)((double)(this.getImage().getHeight() / 2) + teamNameBounds.getWidth() / 2.0);
            g2d.translate(translateX, translateY);
            g2d.rotate(-1.5707963267948966);
            g2d.drawString(teamNameHome, 0, 0);
            g2d.dispose();
            g2d = this.getGraphicsWithFontAndColor();
            metrics = g2d.getFontMetrics();
            teamNameBounds = metrics.getStringBounds(teamNameAway, g2d);
            translateX = (int)(750.0 + teamNameBounds.getHeight() / 2.0) - 4;
            translateY = (int)((double)(this.getImage().getHeight() / 2) - teamNameBounds.getWidth() / 2.0);
            g2d.translate(translateX, translateY);
            g2d.rotate(1.5707963267948966);
            g2d.drawString(teamNameAway, 0, 0);
            g2d.dispose();
        }
    }

    private Graphics2D getGraphicsWithFontAndColor() {
        Graphics2D g2d = this.getImage().createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.getInstance(3, 0.5f));
        g2d.setFont(new Font("Sans Serif", 1, 20));
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2d;
    }

    @Override
    public void init() {
        this.clear(true);
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        if (fieldModel != null) {
            this.drawWeather(fieldModel.getWeather());
        }
    }
}

