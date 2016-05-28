/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum ConcedeGameStatus implements IEnumWithId,
IEnumWithName
{
    REQUESTED(1, "requested"),
    CONFIRMED(2, "confirmed"),
    DENIED(3, "denied");
    
    private int fId;
    private String fName;

    private ConcedeGameStatus(int pValue, String pName) {
        this.fId = pValue;
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

