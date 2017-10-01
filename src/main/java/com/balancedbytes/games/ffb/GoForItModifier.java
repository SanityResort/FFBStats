/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum GoForItModifier implements IRollModifier
{
    BLIZZARD("Blizzard", 1),
    GREASED_SHOES("Greased Shoes", 3);
    
    private String fName;
    private int fModifier;

    private GoForItModifier(String pName, int pModifier) {
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

