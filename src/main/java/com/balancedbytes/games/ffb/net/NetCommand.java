/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.json.IJsonReadable;

public abstract class NetCommand
implements IJsonReadable {
    private int fSize;

    public abstract NetCommandId getId();

    public int size() {
        return this.fSize;
    }

    public void setSize(int pSize) {
        this.fSize = pSize;
    }

}

