/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum InjuryModifier implements IEnumWithId,
IEnumWithName
{
    MIGHTY_BLOW(1, "Mighty Blow", 1, false),
    DIRTY_PLAYER(2, "Dirty Player", 1, false),
    STUNTY(3, "Stunty", 0, false),
    THICK_SKULL(4, "Thick Skull", 0, false),
    NIGGLING_INJURIES_1(5, "1 Niggling Injury", 1, true),
    NIGGLING_INJURIES_2(6, "2 Niggling Injuries", 2, true),
    NIGGLING_INJURIES_3(7, "3 Niggling Injuries", 3, true),
    NIGGLING_INJURIES_4(8, "4 Niggling Injuries", 4, true),
    NIGGLING_INJURIES_5(9, "5 Niggling Injuries", 5, true);
    
    private int fId;
    private String fName;
    private int fModifier;
    private boolean fNigglingInjuryModifier;

    private InjuryModifier(int pId, String pName, int pModifier, boolean pNigglingInjuryModifier) {
        this.fId = pId;
        this.fName = pName;
        this.fModifier = pModifier;
        this.fNigglingInjuryModifier = pNigglingInjuryModifier;
    }

    public int getModifier() {
        return this.fModifier;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    public boolean isNigglingInjuryModifier() {
        return this.fNigglingInjuryModifier;
    }
}

