/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IRollModifier;

public enum PickupModifier implements IRollModifier
{
    BIG_HAND(1, "Big Hand", 0, false),
    POURING_RAIN(2, "Pouring Rain", 1, false),
    EXTRA_ARMS(3, "Extra Arms", -1, false),
    TACKLEZONES_1(4, "1 Tacklezone", 1, true),
    TACKLEZONES_2(5, "2 Tacklezones", 2, true),
    TACKLEZONES_3(6, "3 Tacklezones", 3, true),
    TACKLEZONES_4(7, "4 Tacklezones", 4, true),
    TACKLEZONES_5(8, "5 Tacklezones", 5, true),
    TACKLEZONES_6(9, "6 Tacklezones", 6, true),
    TACKLEZONES_7(10, "7 Tacklezones", 7, true),
    TACKLEZONES_8(11, "8 Tacklezones", 8, true);
    
    private int fId;
    private String fName;
    private int fModifier;
    private boolean fTacklezoneModifier;

    private PickupModifier(int pId, String pName, int pModifier, boolean pTacklezoneModifier) {
        this.fId = pId;
        this.fName = pName;
        this.fModifier = pModifier;
        this.fTacklezoneModifier = pTacklezoneModifier;
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

    public boolean isTacklezoneModifier() {
        return this.fTacklezoneModifier;
    }

    @Override
    public boolean isModifierIncluded() {
        return this.isTacklezoneModifier();
    }
}

