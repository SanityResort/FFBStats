/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum CardEffect implements INamedObject
{
    DISTRACTED("Distracted"),
    ILLEGALLY_SUBSTITUTED("IllegallySubstituted"),
    MAD_CAP_MUSHROOM_POTION("MadCapMushroomPotion"),
    SEDATIVE("Sedative"),
    POISONED("Poisoned");
    
    private String fName;

    private CardEffect(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

