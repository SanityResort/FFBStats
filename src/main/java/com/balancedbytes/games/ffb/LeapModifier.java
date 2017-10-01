/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IRollModifier;

public enum LeapModifier implements IRollModifier
{
    VERY_LONG_LEGS("Very Long Legs", -1);
    
    private String fName;
    private int fModifier;

    private LeapModifier(String pName, int pModifier) {
        this.fName = pName;
        this.fModifier = pModifier;
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

