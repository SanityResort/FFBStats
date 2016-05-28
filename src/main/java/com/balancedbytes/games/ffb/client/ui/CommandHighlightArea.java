/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

public class CommandHighlightArea {
    private int fCommandNr;
    private int fStartPosition;
    private int fEndPosition;

    public CommandHighlightArea(int pCommandNr) {
        this.fCommandNr = pCommandNr;
    }

    public int getCommandNr() {
        return this.fCommandNr;
    }

    public int getStartPosition() {
        return this.fStartPosition;
    }

    public void setStartPosition(int pStartPosition) {
        this.fStartPosition = pStartPosition;
    }

    public int getEndPosition() {
        return this.fEndPosition;
    }

    public void setEndPosition(int pEndPosition) {
        this.fEndPosition = pEndPosition;
    }
}

