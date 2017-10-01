/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum DodgeModifier implements IRollModifier
{
    STUNTY("Stunty", 0, false, false),
    BREAK_TACKLE("Break Tackle", 0, false, false),
    TWO_HEADS("Two Heads", -1, false, false),
    DIVING_TACKLE("Diving Tackle", 2, false, false),
    TITCHY("Titchy", -1, false, false),
    TACKLEZONES_1("1 Tacklezone", 1, true, false),
    TACKLEZONES_2("2 Tacklezones", 2, true, false),
    TACKLEZONES_3("3 Tacklezones", 3, true, false),
    TACKLEZONES_4("4 Tacklezones", 4, true, false),
    TACKLEZONES_5("5 Tacklezones", 5, true, false),
    TACKLEZONES_6("6 Tacklezones", 6, true, false),
    TACKLEZONES_7("7 Tacklezones", 7, true, false),
    TACKLEZONES_8("8 Tacklezones", 8, true, false),
    PREHENSILE_TAIL_1("1 Prehensile Tail", 1, false, true),
    PREHENSILE_TAIL_2("2 Prehensile Tails", 2, false, true),
    PREHENSILE_TAIL_3("3 Prehensile Tails", 3, false, true),
    PREHENSILE_TAIL_4("4 Prehensile Tails", 4, false, true),
    PREHENSILE_TAIL_5("5 Prehensile Tails", 5, false, true),
    PREHENSILE_TAIL_6("6 Prehensile Tails", 6, false, true),
    PREHENSILE_TAIL_7("7 Prehensile Tails", 7, false, true),
    PREHENSILE_TAIL_8("8 Prehensile Tails", 8, false, true);
    
    private String fName;
    private int fModifier;
    private boolean fTacklezoneModifier;
    private boolean fPrehensileTailModifier;

    private DodgeModifier(String pName, int pModifier, boolean pTacklezoneModifier, boolean pPrehensileTailModifier) {
        this.fName = pName;
        this.fModifier = pModifier;
        this.fTacklezoneModifier = pTacklezoneModifier;
        this.fPrehensileTailModifier = pPrehensileTailModifier;
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

    public boolean isPrehensileTailModifier() {
        return this.fPrehensileTailModifier;
    }

    @Override
    public boolean isModifierIncluded() {
        return this.isTacklezoneModifier() || this.isPrehensileTailModifier();
    }
}

