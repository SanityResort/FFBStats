/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.client.ui.ChatLogTextPane;
import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ChatLogScrollPane
extends JScrollPane
implements AdjustmentListener,
ComponentListener {
    private int fOldVisibleMaximum;

    public ChatLogScrollPane(ChatLogTextPane pTextPane) {
        super(pTextPane);
        this.setHorizontalScrollBarPolicy(31);
        this.setVerticalScrollBarPolicy(20);
        this.getVerticalScrollBar().addAdjustmentListener(this);
        this.addComponentListener(this);
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent pE) {
        int visibleMaximum;
        JScrollBar scrollBar = (JScrollBar)pE.getSource();
        if (!pE.getValueIsAdjusting() && (visibleMaximum = this.findVisibleMaximum(scrollBar)) > this.fOldVisibleMaximum) {
            if (scrollBar.getValue() - this.fOldVisibleMaximum < 2) {
                scrollBar.setValue(visibleMaximum);
            }
            this.fOldVisibleMaximum = visibleMaximum;
        }
    }

    private int findVisibleMaximum(JScrollBar pScrollBar) {
        return pScrollBar.getMaximum() - pScrollBar.getVisibleAmount();
    }

    @Override
    public void componentResized(ComponentEvent pE) {
        this.fOldVisibleMaximum = this.findVisibleMaximum(this.getVerticalScrollBar());
    }

    @Override
    public void componentHidden(ComponentEvent pE) {
    }

    @Override
    public void componentMoved(ComponentEvent pE) {
    }

    @Override
    public void componentShown(ComponentEvent pE) {
        this.fOldVisibleMaximum = this.findVisibleMaximum(this.getVerticalScrollBar());
    }

    public void setScrollBarToMaximum() {
        this.getVerticalScrollBar().setValue(this.findVisibleMaximum(this.getVerticalScrollBar()));
    }

    public void setScrollBarToMinimum() {
        this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getMinimum());
    }
}

