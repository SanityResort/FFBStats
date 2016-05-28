/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IRollModifier;

public enum GoForItModifier implements IRollModifier
{
    BLIZZARD(1, "Blizzard", 1),
    GREASED_SHOES(2, "Greased Shoes", 3);
    
    private int fId;
    private String fName;
    private int fModifier;

    private GoForItModifier(int pId, String pName, int pModifier) {
        this.fId = pId;
        this.fName = pName;
        this.fModifier = pModifier;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public int getModifier() {
        return this.fModifier;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    @Override
    public boolean isModifierIncluded() {
        return false;
    }
}

