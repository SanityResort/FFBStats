/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum PlayerType implements INamedObject
{
    REGULAR("Regular"),
    BIG_GUY("Big Guy"),
    STAR("Star"),
    IRREGULAR("Irregular"),
    JOURNEYMAN("Journeyman"),
    RAISED_FROM_DEAD("RaisedFromDead"),
    MERCENARY("Mercenary");
    
    private String fName;

    private PlayerType(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

