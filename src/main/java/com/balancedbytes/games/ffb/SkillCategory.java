/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum SkillCategory implements IEnumWithId,
IEnumWithName
{
    GENERAL(1, "General", "G"),
    AGILITY(2, "Agility", "A"),
    PASSING(3, "Passing", "P"),
    STRENGTH(4, "Strength", "S"),
    MUTATION(5, "Mutation", "M"),
    EXTRAORDINARY(6, "Extraordinary", "E"),
    STAT_INCREASE(7, "Stat Increase", "+"),
    STAT_DECREASE(8, "Stat Decrease", "-");
    
    private int fId;
    private String fName;
    private String fTypeString;

    private SkillCategory(int pId, String pName, String pTypeString) {
        this.fId = pId;
        this.fName = pName;
        this.fTypeString = pTypeString;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getTypeString() {
        return this.fTypeString;
    }
}

