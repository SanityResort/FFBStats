/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum LeaderState implements INamedObject
{
    NONE("none"),
    AVAILABLE("available"),
    USED("used");
    
    private String fName;

    private LeaderState(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

