/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class CommandHighlighter
extends DefaultHighlighter
implements Highlighter.HighlightPainter {
    private Object fHighlight;
    private JTextComponent fTextComponent;
    private Rectangle fLastUpdatedArea;

    public void changeHighlight(int pP0, int pP1) throws BadLocationException {
        if (this.fHighlight == null) {
            try {
                this.fHighlight = this.addHighlight(0, 0, this);
            }
            catch (BadLocationException ble) {
                ble.printStackTrace();
            }
        }
        super.changeHighlight(this.fHighlight, pP0, pP1);
        this.repaintLastUpdatedArea();
    }

    @Override
    public void paint(Graphics pGraphics, int pP0, int pP1, Shape pBounds, JTextComponent pTextComponent) {
        try {
            this.fTextComponent = pTextComponent;
            pGraphics.setColor(Color.LIGHT_GRAY);
            Rectangle leftUpperCorner = pTextComponent.modelToView(pP0);
            Rectangle rightLowerCorner = pTextComponent.modelToView(pP1);
            Insets insets = pTextComponent.getInsets();
            Rectangle updatedArea = new Rectangle(insets.left, leftUpperCorner.y, pTextComponent.getWidth() - insets.left - insets.right, rightLowerCorner.y - leftUpperCorner.y);
            pGraphics.fillRect(updatedArea.x, updatedArea.y, updatedArea.width, updatedArea.height);
            if (this.fLastUpdatedArea == null) {
                this.fLastUpdatedArea = updatedArea;
            } else {
                this.fLastUpdatedArea.add(updatedArea);
            }
        }
        catch (BadLocationException ble) {
            ble.printStackTrace();
        }
    }

    public void repaintLastUpdatedArea() {
        if (this.fTextComponent != null && this.fLastUpdatedArea != null) {
            this.fTextComponent.repaint(this.fLastUpdatedArea);
            this.fLastUpdatedArea = null;
        }
    }
}

