/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IRollModifier;

public enum LeapModifier implements IRollModifier
{
    VERY_LONG_LEGS(1, "Very Long Legs", -1);
    
    private int fId;
    private String fName;
    private int fModifier;

    private LeapModifier(int pId, String pName, int pModifier) {
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

