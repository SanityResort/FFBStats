/*
 * Decompiled with CFR 0_122.
 */
package com.fumbbl.rng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseEntropySource
implements EntropySource {
    public static long MIN_UPDATE_PERIOD_MS = 10;
    private byte fData;
    private long fLastPositionUpdate;
    private int fBits;
    private int fLastX;
    private int fLastY;
    private Component fTargetComponent;

    public MouseEntropySource(Component pTargetComponent) {
        this.fTargetComponent = pTargetComponent;
    }

    public Component getTargetComponent() {
        return this.fTargetComponent;
    }

    private void reportMousePosition(int x, int y) {
        if (this.fLastX == x && this.fLastY == y || System.currentTimeMillis() - this.fLastPositionUpdate < MIN_UPDATE_PERIOD_MS) {
            return;
        }
        int b = x & 3 ^ y & 3;
        this.fData = (byte)(this.fData << 2 | b);
        this.fBits += 2;
        this.fLastPositionUpdate = System.currentTimeMillis();
        this.fLastX = x;
        this.fLastY = y;
    }

    public synchronized void reportMousePosition(MouseEvent pMouseEvent) {
        if (pMouseEvent != null) {
            Point convertedMousePoint = SwingUtilities.convertPoint((Component)pMouseEvent.getSource(), pMouseEvent.getPoint(), this.fTargetComponent);
            this.reportMousePosition(convertedMousePoint.x, convertedMousePoint.y);
        }
    }

    @Override
    public boolean hasEnoughEntropy() {
        return this.fBits >= 8;
    }

    @Override
    public byte getEntropy() {
        this.fBits = 0;
        return this.fData;
    }
}

