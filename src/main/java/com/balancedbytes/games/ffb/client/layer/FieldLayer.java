/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;

public abstract class FieldLayer {
    public static final int FIELD_SQUARE_SIZE = 30;
    public static final int FIELD_IMAGE_WIDTH = 782;
    public static final int FIELD_IMAGE_HEIGHT = 452;
    public static final int FIELD_IMAGE_OFFSET_CENTER_X = 15;
    public static final int FIELD_IMAGE_OFFSET_CENTER_Y = 15;
    private FantasyFootballClient fClient;
    private BufferedImage fImage;
    private Rectangle fUpdatedArea;

    public FieldLayer(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fImage = new BufferedImage(782, 452, 2);
        this.addUpdatedArea(new Rectangle(0, 0, this.fImage.getWidth(), this.fImage.getHeight()));
    }

    public BufferedImage getImage() {
        return this.fImage;
    }

    public Rectangle fetchUpdatedArea() {
        Rectangle updatedArea = this.fUpdatedArea;
        this.fUpdatedArea = null;
        return updatedArea;
    }

    protected Rectangle draw(BufferedImage pImage, int pX, int pY, float pAlpha) {
        Graphics2D g2d = this.fImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(3, pAlpha));
        g2d.drawImage(pImage, pX, pY, null);
        g2d.dispose();
        Rectangle updatedArea = new Rectangle(pX, pY, pImage.getWidth(), pImage.getHeight());
        this.addUpdatedArea(updatedArea);
        return updatedArea;
    }

    public Rectangle drawCenteredAndScaled(BufferedImage pImage, int pX, int pY, float pAlpha, double pScaleX, double pScaleY) {
        if (pImage != null) {
            int width = (int)((double)pImage.getWidth() * pScaleX);
            int height = (int)((double)pImage.getHeight() * pScaleY);
            if (width > 0 && height > 0) {
                BufferedImage scaledIcon = new BufferedImage(width, height, 2);
                Graphics2D g2d = scaledIcon.createGraphics();
                AffineTransform transformation = AffineTransform.getScaleInstance(pScaleX, pScaleY);
                g2d.drawRenderedImage(pImage, transformation);
                g2d.dispose();
                int x = pX - width / 2;
                int y = pY - height / 2;
                return this.draw(scaledIcon, x, y, pAlpha);
            }
        }
        return null;
    }

    public Rectangle draw(BufferedImage pImage, FieldCoordinate pCoordinate, float pAlpha) {
        if (pImage != null && pCoordinate != null) {
            return this.draw(pImage, this.findCenteredIconUpperLeftX(pImage, pCoordinate), this.findCenteredIconUpperLeftY(pImage, pCoordinate), pAlpha);
        }
        return null;
    }

    protected void clear(int pX, int pY, int pWidth, int pHeight, boolean pUpdateArea) {
        Graphics2D g2d = this.fImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(1, 0.0f));
        g2d.fillRect(pX, pY, pWidth, pHeight);
        g2d.dispose();
        if (pUpdateArea) {
            this.addUpdatedArea(new Rectangle(pX, pY, pWidth, pHeight));
        }
    }

    public void clear(Rectangle pRectangle, boolean pUpdateArea) {
        if (pRectangle != null) {
            this.clear(pRectangle.x, pRectangle.y, pRectangle.width, pRectangle.height, pUpdateArea);
        }
    }

    public void clear(BufferedImage pImage, FieldCoordinate pCoordinate, boolean pUpdateArea) {
        if (pImage != null && pCoordinate != null) {
            this.clear(this.findCenteredIconUpperLeftX(pImage, pCoordinate), this.findCenteredIconUpperLeftY(pImage, pCoordinate), pImage.getWidth(), pImage.getHeight(), pUpdateArea);
        }
    }

    protected int findCenteredIconUpperLeftX(BufferedImage pImage, FieldCoordinate pCoordinate) {
        return 15 + pCoordinate.getX() * 30 - pImage.getWidth() / 2;
    }

    protected int findCenteredIconUpperLeftY(BufferedImage pImage, FieldCoordinate pCoordinate) {
        return 15 + pCoordinate.getY() * 30 - pImage.getHeight() / 2;
    }

    public void clear(boolean pUpdateArea) {
        this.clear(0, 0, this.getImage().getWidth(), this.getImage().getHeight(), pUpdateArea);
    }

    public void clear(FieldCoordinate pCoordinate, boolean pUpdateArea) {
        if (pCoordinate != null && FieldCoordinateBounds.FIELD.isInBounds(pCoordinate)) {
            int fieldX = pCoordinate.getX() * 30;
            int fieldY = pCoordinate.getY() * 30;
            this.clear(fieldX, fieldY, 30, 30, pUpdateArea);
        }
    }

    protected void addUpdatedArea(Rectangle pRectangle) {
        if (this.fUpdatedArea != null) {
            this.fUpdatedArea.add(pRectangle);
        } else {
            this.fUpdatedArea = pRectangle;
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public void init() {
    }
}

