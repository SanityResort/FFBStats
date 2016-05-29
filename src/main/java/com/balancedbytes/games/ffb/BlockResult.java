/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum BlockResult implements IEnumWithId,
IEnumWithName
{
    SKULL(1, "SKULL"),
    BOTH_DOWN(2, "BOTH DOWN"),
    PUSHBACK(3, "PUSHBACK"),
    POW_PUSHBACK(4, "POW/PUSH"),
    POW(5, "POW");
    
    private int fId;
    private String fName;

    private BlockResult(int pId, String pName) {
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

