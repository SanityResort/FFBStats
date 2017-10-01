/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum BlockResult implements INamedObject
{
    SKULL("SKULL"),
    BOTH_DOWN("BOTH DOWN"),
    PUSHBACK("PUSHBACK"),
    POW_PUSHBACK("POW/PUSH"),
    POW("POW");
    
    private String fName;

    private BlockResult(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

