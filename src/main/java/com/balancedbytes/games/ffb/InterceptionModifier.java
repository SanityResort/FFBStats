/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IRollModifier;

public enum InterceptionModifier implements IRollModifier
{
    NERVES_OF_STEEL("Nerves of Steel", 0, false, false),
    EXTRA_ARMS("Extra Arms", -1, false, false),
    VERY_LONG_LEGS("Very Long Legs", -1, false, false),
    POURING_RAIN("Pouring Rain", 1, false, false),
    TACKLEZONES_1("1 Tacklezone", 1, true, false),
    TACKLEZONES_2("2 Tacklezones", 2, true, false),
    TACKLEZONES_3("3 Tacklezones", 3, true, false),
    TACKLEZONES_4("4 Tacklezones", 4, true, false),
    TACKLEZONES_5("5 Tacklezones", 5, true, false),
    TACKLEZONES_6("6 Tacklezones", 6, true, false),
    TACKLEZONES_7("7 Tacklezones", 7, true, false),
    TACKLEZONES_8("8 Tacklezones", 8, true, false),
    DISTURBING_PRESENCES_1("1 Disturbing Presence", 1, false, true),
    DISTURBING_PRESENCES_2("2 Disturbing Presences", 2, false, true),
    DISTURBING_PRESENCES_3("3 Disturbing Presences", 3, false, true),
    DISTURBING_PRESENCES_4("4 Disturbing Presences", 4, false, true),
    DISTURBING_PRESENCES_5("5 Disturbing Presences", 5, false, true),
    DISTURBING_PRESENCES_6("6 Disturbing Presences", 6, false, true),
    DISTURBING_PRESENCES_7("7 Disturbing Presences", 7, false, true),
    DISTURBING_PRESENCES_8("8 Disturbing Presences", 8, false, true),
    DISTURBING_PRESENCES_9("9 Disturbing Presences", 9, false, true),
    DISTURBING_PRESENCES_10("10 Disturbing Presences", 10, false, true),
    DISTURBING_PRESENCES_11("11 Disturbing Presences", 11, false, true),
    FAWNDOUGHS_HEADBAND("Fawndough's Headband", -1, false, false),
    MAGIC_GLOVES_OF_JARK_LONGARM("Magic Gloves of Jark Longarm", -1, false, false);
    
    private String fName;
    private int fModifier;
    private boolean fTacklezoneModifier;
    private boolean fDisturbingPresenceModifier;

    private InterceptionModifier(String pName, int pModifier, boolean pTacklezoneModifier, boolean pDisturbingPresenceModifier) {
        this.fName = pName;
        this.fModifier = pModifier;
        this.fTacklezoneModifier = pTacklezoneModifier;
        this.fDisturbingPresenceModifier = pDisturbingPresenceModifier;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    @Override
    public int getModifier() {
        return this.fModifier;
    }

    public boolean isTacklezoneModifier() {
        return this.fTacklezoneModifier;
    }

    public boolean isDisturbingPresenceModifier() {
        return this.fDisturbingPresenceModifier;
    }

    @Override
    public boolean isModifierIncluded() {
        return this.isTacklezoneModifier() || this.isDisturbingPresenceModifier();
    }
}

