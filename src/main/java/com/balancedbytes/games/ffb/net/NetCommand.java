/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.json.IJsonSerializable;

public abstract class NetCommand
implements IJsonSerializable {
    public abstract NetCommandId getId();

    public boolean isInternal() {
        return false;
    }
}

