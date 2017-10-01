/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum PassingDistance implements INamedObject
{
    QUICK_PASS("Quick Pass", 1, 'Q'),
    SHORT_PASS("Short Pass", 0, 'S'),
    LONG_PASS("Long Pass", -1, 'L'),
    LONG_BOMB("Long Bomb", -2, 'B');
    
    private String fName;
    private int fModifier;
    private char fShortcut;

    private PassingDistance(String pName, int pModifier, char pShortcut) {
        this.fName = pName;
        this.fModifier = pModifier;
        this.fShortcut = pShortcut;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public int getModifier() {
        return this.fModifier;
    }

    public char getShortcut() {
        return this.fShortcut;
    }
}

