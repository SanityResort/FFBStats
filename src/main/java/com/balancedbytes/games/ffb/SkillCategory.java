/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum SkillCategory implements INamedObject
{
    GENERAL("General", "G"),
    AGILITY("Agility", "A"),
    PASSING("Passing", "P"),
    STRENGTH("Strength", "S"),
    MUTATION("Mutation", "M"),
    EXTRAORDINARY("Extraordinary", "E"),
    STAT_INCREASE("Stat Increase", "+"),
    STAT_DECREASE("Stat Decrease", "-");
    
    private String fName;
    private String fTypeString;

    private SkillCategory(String pName, String pTypeString) {
        this.fName = pName;
        this.fTypeString = pTypeString;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getTypeString() {
        return this.fTypeString;
    }
}

