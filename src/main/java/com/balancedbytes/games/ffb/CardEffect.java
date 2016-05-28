/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithName;

public enum CardEffect implements IEnumWithName
{
    DISTRACTED("Distracted"),
    ILLEGALLY_SUBSTITUTED("IllegallySubstituted"),
    MAD_CAP_MUSHROOM_POTION("MadCapMushroomPotion"),
    SEDATIVE("Sedative");
    
    private String fName;

    private CardEffect(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

