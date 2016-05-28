/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum Direction implements IEnumWithId,
IEnumWithName
{
    NORTH(1, "North", 1),
    NORTHEAST(2, "Northeast", 8),
    EAST(3, "East", 7),
    SOUTHEAST(4, "Southeast", 6),
    SOUTH(5, "South", 5),
    SOUTHWEST(6, "Southwest", 4),
    WEST(7, "West", 3),
    NORTHWEST(8, "Northwest", 2);
    
    private int fId;
    private String fName;
    private int fTransformedValue;

    private Direction(int pId, String pName, int pTransformedValue) {
        this.fId = pId;
        this.fName = pName;
        this.fTransformedValue = pTransformedValue;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    protected int getTransformedValue() {
        return this.fTransformedValue;
    }
}

