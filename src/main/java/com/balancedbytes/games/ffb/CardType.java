/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum CardType implements INamedObject
{
    MISCELLANEOUS_MAYHEM("miscellaneousMayhem", 50000, "Miscellaneous Mayhem Deck", "Miscellaneous Mayhem Card", "Miscellaneous Mayhem Cards"),
    SPECIAL_TEAM_PLAY("specialTeamPlay", 50000, "Special Team Plays Deck", "Special Team Play Card", "Special Team Play Cards"),
    MAGIC_ITEM("magicItem", 50000, "Magic Items Deck", "Magic Item Card", "Magic Item Cards"),
    DIRTY_TRICK("dirtyTrick", 50000, "Dirty Tricks Deck", "Dirty Trick Card", "Dirty Trick Cards"),
    GOOD_KARMA("goodKarma", 100000, "Good Karma Deck", "Good Karma Card", "Good Karma Cards"),
    RANDOM_EVENT("randomEvent", 200000, "Random Events Deck", "Random Event Card", "Random Event Cards"),
    DESPERATE_MEASURE("desperateMeasure", 400000, "Desperate Measures Deck", "Desperate Measure Card", "Desperate Measure Cards");
    
    private String fName;
    private String fDeckName;
    private String fInducementNameSingle;
    private String fInducementNameMultiple;
    private int fPrice;

    private CardType(String pName, int pPrice, String pDeckName, String pInducementNameSingle, String pInducementNameMultiple) {
        this.fName = pName;
        this.fPrice = pPrice;
        this.fDeckName = pDeckName;
        this.fInducementNameSingle = pInducementNameSingle;
        this.fInducementNameMultiple = pInducementNameMultiple;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getDeckName() {
        return this.fDeckName;
    }

    public String getInducementNameSingle() {
        return this.fInducementNameSingle;
    }

    public String getInducementNameMultiple() {
        return this.fInducementNameMultiple;
    }

    public int getPrice() {
        return this.fPrice;
    }

    public static int getMinimumPrice() {
        return 50000;
    }

    public String toString() {
        return this.getName();
    }
}

