/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.json.IJsonSerializable;

public abstract class NetCommand
implements IJsonSerializable {
    private int fSize;

    public abstract NetCommandId getId();

    public int size() {
        return this.fSize;
    }

    public void setSize(int pSize) {
        this.fSize = pSize;
    }

}

