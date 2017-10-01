/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.TrackNumber;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.ArrayTool;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FieldLayerUnderPlayers
extends FieldLayer {
    private FieldCoordinate[] fMovePath;

    public FieldLayerUnderPlayers(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void drawTrackNumber(TrackNumber pTrackNumber) {
        this.drawTrackNumber(pTrackNumber.getCoordinate(), pTrackNumber.getNumber(), Color.CYAN);
    }

    public void drawMovePath(FieldCoordinate[] pCoordinates, int pStartNumber) {
        this.fMovePath = pCoordinates;
        if (ArrayTool.isProvided(pCoordinates)) {
            for (int i = 0; i < pCoordinates.length; ++i) {
                this.drawTrackNumber(pCoordinates[i], pStartNumber + i + 1, Color.WHITE);
            }
        }
    }

    public FieldCoordinate[] getMovePath() {
        return this.fMovePath;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean clearMovePath() {
        FieldLayerUnderPlayers fieldLayerUnderPlayers = this;
        synchronized (fieldLayerUnderPlayers) {
            Game game = this.getClient().getGame();
            boolean pathCleared = ArrayTool.isProvided(this.fMovePath);
            if (pathCleared) {
                for (int i = 0; i < this.fMovePath.length; ++i) {
                    TrackNumber trackNumber = game.getFieldModel().getTrackNumber(this.fMovePath[i]);
                    if (trackNumber != null) {
                        this.drawTrackNumber(trackNumber);
                        continue;
                    }
                    this.clear(this.fMovePath[i], true);
                }
                this.fMovePath = null;
            }
            return pathCleared;
        }
    }

    private void drawTrackNumber(FieldCoordinate pCoordinate, int pNumber, Color pColor) {
        if (pCoordinate != null) {
            this.clear(pCoordinate, true);
            String numberString = Integer.toString(pNumber);
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setFont(new Font("Sans Serif", 1, 15));
            FontMetrics metrics = g2d.getFontMetrics();
            Rectangle2D numberBounds = metrics.getStringBounds(numberString, g2d);
            int baselineX = 15 + pCoordinate.getX() * 30 - (int)(numberBounds.getWidth() / 2.0) + 1;
            int baselineY = 15 + pCoordinate.getY() * 30 + (int)(numberBounds.getHeight() / 2.0) - 2;
            g2d.setComposite(AlphaComposite.getInstance(3, 0.7f));
            g2d.setColor(Color.BLACK);
            g2d.drawString(numberString, baselineX + 1, baselineY + 1);
            g2d.setColor(pColor);
            g2d.drawString(numberString, baselineX, baselineY);
            g2d.dispose();
        }
    }

    public void removeTrackNumber(TrackNumber pTrackNumber) {
        if (pTrackNumber != null) {
            this.clear(pTrackNumber.getCoordinate(), true);
        }
    }

    @Override
    public void init() {
        this.clear(true);
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        if (fieldModel != null) {
            TrackNumber[] trackNumbers = fieldModel.getTrackNumbers();
            for (int i = 0; i < trackNumbers.length; ++i) {
                this.drawTrackNumber(trackNumbers[i]);
            }
        }
    }
}

