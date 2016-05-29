/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum LeaderState implements IEnumWithId,
IEnumWithName
{
    NONE(1, "none"),
    AVAILABLE(2, "available"),
    USED(3, "used");
    
    private int fId;
    private String fName;

    private LeaderState(int pValue, String pName) {
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

