/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import java.nio.channels.SocketChannel;

public class SocketChangeRequest {
    public static final int REGISTER = 1;
    public static final int CHANGEOPS = 2;
    private SocketChannel fSocketChannel;
    private int fType;
    private int fOps;

    public SocketChangeRequest(SocketChannel pSocketChannel, int pType, int pOps) {
        this.fSocketChannel = pSocketChannel;
        this.fType = pType;
        this.fOps = pOps;
    }

    public SocketChannel getSocketChannel() {
        return this.fSocketChannel;
    }

    public int getType() {
        return this.fType;
    }

    public int getOps() {
        return this.fOps;
    }
}

