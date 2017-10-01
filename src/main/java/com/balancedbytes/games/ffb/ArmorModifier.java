/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum ArmorModifier implements INamedObject
{
    CLAWS("Claws", 0, false),
    MIGHTY_BLOW("Mighty Blow", 1, false),
    FOUL_PLUS_1("1 Offensive Assist", 1, true),
    FOUL_PLUS_2("2 Offensive Assists", 2, true),
    FOUL_PLUS_3("3 Offensive Assists", 3, true),
    FOUL_PLUS_4("4 Offensive Assists", 4, true),
    FOUL_PLUS_5("5 Offensive Assists", 5, true),
    FOUL_PLUS_6("6 Offensive Assists", 6, true),
    FOUL_PLUS_7("7 Offensive Assists", 7, true),
    FOUL_MINUS_1("1 Defensive Assist", -1, true),
    FOUL_MINUS_2("2 Defensive Assists", -2, true),
    FOUL_MINUS_3("3 Defensive Assists", -3, true),
    FOUL_MINUS_4("4 Defensive Assists", -4, true),
    FOUL_MINUS_5("5 Defensive Assists", -5, true),
    DIRTY_PLAYER("Dirty Player", 1, false),
    STAKES("Stakes", 1, false),
    CHAINSAW("Chainsaw", 3, false),
    FOUL("Foul", 1, false);
    
    private String fName;
    private int fModifier;
    private boolean fFoulAssistModifier;

    private ArmorModifier(String pName, int pModifier, boolean pFoulAssistModifier) {
        this.fName = pName;
        this.fModifier = pModifier;
        this.fFoulAssistModifier = pFoulAssistModifier;
    }

    public int getModifier() {
        return this.fModifier;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isFoulAssistModifier() {
        return this.fFoulAssistModifier;
    }
}

