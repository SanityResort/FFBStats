/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldMarker;
import com.balancedbytes.games.ffb.client.ClientParameters;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FieldLayerMarker
extends FieldLayer {
    public static final Color COLOR_MARKER = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    private Map<FieldCoordinate, Rectangle> fFieldMarkerBounds = new HashMap<FieldCoordinate, Rectangle>();

    public FieldLayerMarker(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void drawFieldMarker(FieldMarker pFieldMarker) {
        if (pFieldMarker != null && StringTool.isProvided(pFieldMarker.getHomeText()) && this.getClient().getParameters().getMode() == ClientMode.PLAYER) {
            this.removeFieldMarker(pFieldMarker);
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setColor(COLOR_MARKER);
            if (pFieldMarker.getHomeText().length() < 2) {
                g2d.setFont(new Font("Sans Serif", 1, 16));
            } else {
                g2d.setFont(new Font("Sans Serif", 1, 12));
            }
            FontMetrics metrics = g2d.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(pFieldMarker.getHomeText(), g2d);
            int x = 15 + pFieldMarker.getCoordinate().getX() * 30 - (int)(textBounds.getWidth() / 2.0) + 1;
            int y = 15 + pFieldMarker.getCoordinate().getY() * 30 + (int)(textBounds.getHeight() / 2.0) - 2;
            g2d.drawString(pFieldMarker.getHomeText(), x, y);
            Rectangle bounds = new Rectangle(x, y - (int)textBounds.getHeight(), (int)Math.ceil(textBounds.getWidth()), (int)Math.ceil(textBounds.getHeight()));
            this.fFieldMarkerBounds.put(pFieldMarker.getCoordinate(), bounds);
            this.addUpdatedArea(bounds);
            g2d.dispose();
        }
    }

    public void removeFieldMarker(FieldMarker pFieldMarker) {
        Rectangle bounds;
        if (pFieldMarker != null && ClientMode.PLAYER == this.getClient().getMode() && (bounds = this.fFieldMarkerBounds.get(pFieldMarker.getCoordinate())) != null) {
            this.clear(bounds.x, bounds.y, bounds.width, bounds.height, true);
            this.fFieldMarkerBounds.remove(pFieldMarker.getCoordinate());
        }
    }

    @Override
    public void init() {
        this.clear(true);
        this.fFieldMarkerBounds.clear();
        Game game = this.getClient().getGame();
        FieldModel fieldModel = game.getFieldModel();
        if (fieldModel != null) {
            for (FieldMarker fieldMarker : fieldModel.getFieldMarkers()) {
                this.drawFieldMarker(fieldMarker);
            }
        }
    }
}

