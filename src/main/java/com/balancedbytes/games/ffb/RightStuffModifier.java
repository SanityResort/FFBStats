/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IRollModifier;

public enum RightStuffModifier implements IRollModifier
{
    TACKLEZONES_1(1, "1 Tacklezone", 1, true),
    TACKLEZONES_2(2, "2 Tacklezones", 2, true),
    TACKLEZONES_3(3, "3 Tacklezones", 3, true),
    TACKLEZONES_4(4, "4 Tacklezones", 4, true),
    TACKLEZONES_5(5, "5 Tacklezones", 5, true),
    TACKLEZONES_6(6, "6 Tacklezones", 6, true),
    TACKLEZONES_7(7, "7 Tacklezones", 7, true),
    TACKLEZONES_8(8, "8 Tacklezones", 8, true);
    
    private int fId;
    private String fName;
    private int fModifier;
    private boolean fTacklezoneModifier;

    private RightStuffModifier(int pId, String pName, int pModifier, boolean pTacklezoneModifier) {
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

