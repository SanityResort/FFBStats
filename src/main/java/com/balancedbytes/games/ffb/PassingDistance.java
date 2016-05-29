/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum PassingDistance implements IEnumWithId,
IEnumWithName
{
    QUICK_PASS(1, "Quick Pass", 1, 'Q'),
    SHORT_PASS(2, "Short Pass", 0, 'S'),
    LONG_PASS(3, "Long Pass", -1, 'L'),
    LONG_BOMB(4, "Long Bomb", -2, 'B');
    
    private int fId;
    private String fName;
    private int fModifier;
    private char fShortcut;

    private PassingDistance(int pId, String pName, int pModifier, char pShortcut) {
        this.fId = pId;
        this.fName = pName;
        this.fModifier = pModifier;
        this.fShortcut = pShortcut;
    }

    @Override
    public int getId() {
        return this.fId;
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

