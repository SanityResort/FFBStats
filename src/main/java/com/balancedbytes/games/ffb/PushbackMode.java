/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum PushbackMode implements INamedObject
{
    REGULAR("regular"),
    SIDE_STEP("sideStep"),
    GRAB("grab");
    
    private String fName;

    private PushbackMode(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

