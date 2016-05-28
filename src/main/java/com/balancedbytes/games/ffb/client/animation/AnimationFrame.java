/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.animation;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class AnimationFrame {
    private String fIconProperty1;
    private float fAlpha1;
    private double fScaleX1;
    private double fScaleY1;
    private BufferedImage fIcon1;
    private String fIconProperty2;
    private float fAlpha2;
    private double fScaleX2;
    private double fScaleY2;
    private BufferedImage fIcon2;
    private int fTime;
    private SoundId fSound;
    private FieldLayer fFieldLayer;
    private Rectangle fUpdatedArea;

    public AnimationFrame(String pIconProperty, float pAlpha, int pTime) {
        this(pIconProperty, pAlpha, 1.0, 1.0, null, 1.0f, 1.0, 1.0, pTime, null);
    }

    public AnimationFrame(String pIconProperty, float pAlpha, int pTime, SoundId pSound) {
        this(pIconProperty, pAlpha, 1.0, 1.0, null, 1.0f, 1.0, 1.0, pTime, pSound);
    }

    public AnimationFrame(String pIconProperty, float pAlpha, double pScale, int pTime) {
        this(pIconProperty, pAlpha, pScale, pScale, null, 1.0f, 1.0, 1.0, pTime, null);
    }

    public AnimationFrame(String pIconProperty, float pAlpha, double pScale, int pTime, SoundId pSound) {
        this(pIconProperty, pAlpha, pScale, pScale, null, 1.0f, 1.0, 1.0, pTime, pSound);
    }

    public AnimationFrame(String pIconProperty, float pAlpha, double pScaleX, double pScaleY, int pTime) {
        this(pIconProperty, pAlpha, pScaleX, pScaleY, null, 1.0f, 1.0, 1.0, pTime, null);
    }

    public AnimationFrame(BufferedImage pIcon, float pAlpha, double pScaleX, double pScaleY, int pTime) {
        this.fIcon1 = pIcon;
        this.fIconProperty1 = null;
        this.fAlpha1 = pAlpha;
        this.fScaleX1 = pScaleX;
        this.fScaleY1 = pScaleY;
        this.fIconProperty2 = null;
        this.fAlpha2 = 1.0f;
        this.fScaleX2 = 1.0;
        this.fScaleY2 = 1.0;
        this.fTime = pTime;
        this.fSound = null;
    }

    public AnimationFrame(String pIconProperty1, float pAlpha1, String pIconProperty2, float pAlpha2, int pTime) {
        this(pIconProperty1, pAlpha1, 1.0, 1.0, pIconProperty2, pAlpha2, 1.0, 1.0, pTime, null);
    }

    public AnimationFrame(String pIconProperty1, float pAlpha1, String pIconProperty2, float pAlpha2, int pTime, SoundId pSound) {
        this(pIconProperty1, pAlpha1, 1.0, 1.0, pIconProperty2, pAlpha2, 1.0, 1.0, pTime, pSound);
    }

    public AnimationFrame(String pIconProperty1, float pAlpha1, double pScale1, String pIconProperty2, float pAlpha2, double pScale2, int pTime, SoundId pSound) {
        this(pIconProperty1, pAlpha1, pScale1, pScale1, pIconProperty2, pAlpha2, pScale2, pScale2, pTime, pSound);
    }

    public AnimationFrame(String pIconProperty1, float pAlpha1, double pScaleX1, double pScaleY1, String pIconProperty2, float pAlpha2, double pScaleX2, double pScaleY2, int pTime, SoundId pSound) {
        this.fIconProperty1 = pIconProperty1;
        this.fAlpha1 = pAlpha1;
        this.fScaleX1 = pScaleX1;
        this.fScaleY1 = pScaleY1;
        this.fIconProperty2 = pIconProperty2;
        this.fAlpha2 = pAlpha2;
        this.fScaleX2 = pScaleX2;
        this.fScaleY2 = pScaleY2;
        this.fTime = pTime;
        this.fSound = pSound;
    }

    public int getTime() {
        return this.fTime;
    }

    public SoundId getSound() {
        return this.fSound;
    }

    public void drawCenteredAndScaled(FieldLayer pFieldLayer, int pX, int pY) {
        this.fFieldLayer = pFieldLayer;
        this.getIcons();
        if (this.fIcon1 != null) {
            this.addUpdatedArea(this.fFieldLayer.drawCenteredAndScaled(this.fIcon1, pX, pY, this.fAlpha1, this.fScaleX1, this.fScaleY1));
        }
        if (this.fIcon2 != null) {
            this.addUpdatedArea(this.fFieldLayer.drawCenteredAndScaled(this.fIcon2, pX, pY, this.fAlpha2, this.fScaleX2, this.fScaleY2));
        }
    }

    public void draw(FieldLayer pFieldLayer, FieldCoordinate pCoordinate) {
        this.fFieldLayer = pFieldLayer;
        this.getIcons();
        if (this.fIcon1 != null) {
            this.addUpdatedArea(this.fFieldLayer.draw(this.fIcon1, pCoordinate, this.fAlpha1));
        }
        if (this.fIcon2 != null) {
            this.addUpdatedArea(this.fFieldLayer.draw(this.fIcon2, pCoordinate, this.fAlpha2));
        }
    }

    public void clear() {
        if (this.fFieldLayer != null && this.fUpdatedArea != null) {
            this.fFieldLayer.clear(this.fUpdatedArea, true);
            this.fUpdatedArea = null;
        }
    }

    private void getIcons() {
        IconCache iconCache = this.fFieldLayer.getClient().getUserInterface().getIconCache();
        if (StringTool.isProvided(this.fIconProperty1)) {
            this.fIcon1 = iconCache.getIconByProperty(this.fIconProperty1);
        }
        if (StringTool.isProvided(this.fIconProperty2)) {
            this.fIcon2 = iconCache.getIconByProperty(this.fIconProperty2);
        }
    }

    private void addUpdatedArea(Rectangle pRectangle) {
        if (this.fUpdatedArea != null) {
            this.fUpdatedArea.add(pRectangle);
        } else {
            this.fUpdatedArea = pRectangle;
        }
    }
}

