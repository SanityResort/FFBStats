/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeRuler;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.UtilPassing;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class FieldLayerRangeGrid
extends FieldLayer {
    private FieldCoordinate fCenterCoordinate;

    public FieldLayerRangeGrid(FantasyFootballClient pClient) {
        super(pClient);
    }

    public boolean drawRangeGrid(FieldCoordinate pCenterCoordinate, boolean pThrowTeamMate) {
        if (pCenterCoordinate != null && !pCenterCoordinate.equals(this.fCenterCoordinate)) {
            this.fCenterCoordinate = pCenterCoordinate;
            for (int y = 0; y < 15; ++y) {
                for (int x = 0; x < 26; ++x) {
                    FieldCoordinate coordinate = new FieldCoordinate(x, y);
                    this.clear(coordinate, false);
                    PassingDistance passingDistance = UtilPassing.findPassingDistance(this.getClient().getGame(), this.fCenterCoordinate, coordinate, pThrowTeamMate);
                    if (passingDistance == null) continue;
                    this.markSquare(coordinate, FieldLayerRangeRuler.getColorForPassingDistance(passingDistance));
                }
            }
            this.addUpdatedArea(new Rectangle(0, 0, this.getImage().getWidth(), this.getImage().getHeight()));
            return true;
        }
        return false;
    }

    public boolean clearRangeGrid() {
        if (this.fCenterCoordinate != null) {
            this.fCenterCoordinate = null;
            this.clear(true);
            return true;
        }
        return false;
    }

    private void markSquare(FieldCoordinate pCoordinate, Color pColor) {
        if (pCoordinate != null && FieldCoordinateBounds.FIELD.isInBounds(pCoordinate)) {
            int x = pCoordinate.getX() * 30;
            int y = pCoordinate.getY() * 30;
            Rectangle bounds = new Rectangle(x + 1, y + 1, 28, 28);
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setPaint(pColor);
            g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2d.dispose();
        }
    }

    public FieldCoordinate getCenterCoordinate() {
        return this.fCenterCoordinate;
    }
}

