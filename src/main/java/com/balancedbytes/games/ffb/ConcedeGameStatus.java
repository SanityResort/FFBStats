/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum ConcedeGameStatus implements INamedObject
{
    REQUESTED("requested"),
    CONFIRMED("confirmed"),
    DENIED("denied");
    
    private String fName;

    private ConcedeGameStatus(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

