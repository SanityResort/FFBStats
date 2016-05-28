/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.net.NetCommand;

public abstract class ServerCommand
extends NetCommand {
    protected static final String XML_ATTRIBUTE_COMMAND_NR = "cmdNr";
    private int fCommandNr;

    public int getCommandNr() {
        return this.fCommandNr;
    }

    public void setCommandNr(int pCommandNr) {
        this.fCommandNr = pCommandNr;
    }

    public boolean isReplayable() {
        return true;
    }
}

