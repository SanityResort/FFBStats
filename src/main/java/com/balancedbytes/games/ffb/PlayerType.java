/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum PlayerType implements IEnumWithId,
IEnumWithName
{
    REGULAR(1, "Regular"),
    BIG_GUY(2, "Big Guy"),
    STAR(3, "Star"),
    IRREGULAR(4, "Irregular"),
    JOURNEYMAN(5, "Journeyman"),
    RAISED_FROM_DEAD(6, "RaisedFromDead"),
    MERCENARY(7, "Mercenary");
    
    private int fId;
    private String fName;

    private PlayerType(int pId, String pName) {
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

