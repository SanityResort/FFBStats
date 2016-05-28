/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum PushbackMode implements IEnumWithId,
IEnumWithName
{
    REGULAR(1, "regular"),
    SIDE_STEP(2, "sideStep"),
    GRAB(3, "grab");
    
    private int fId;
    private String fName;

    private PushbackMode(int pId, String pName) {
        this.fId = pId;
        this.fName = pName;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

