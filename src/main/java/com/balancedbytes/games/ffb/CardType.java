/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum CardType implements IEnumWithId,
IEnumWithName
{
    MISCELLANEOUS_MAYHEM(1, "miscellaneousMayhem", 50000, "Miscellaneous Mayhem Deck", "Miscellaneous Mayhem Card", "Miscellaneous Mayhem Cards"),
    SPECIAL_TEAM_PLAY(2, "specialTeamPlay", 50000, "Special Team Plays Deck", "Special Team Play Card", "Special Team Play Cards"),
    MAGIC_ITEM(3, "magicItem", 50000, "Magic Items Deck", "Magic Item Card", "Magic Item Cards"),
    DIRTY_TRICK(4, "dirtyTrick", 50000, "Dirty Tricks Deck", "Dirty Trick Card", "Dirty Trick Cards"),
    GOOD_KARMA(5, "goodKarma", 100000, "Good Karma Deck", "Good Karma Card", "Good Karma Cards"),
    RANDOM_EVENT(6, "randomEvent", 200000, "Random Events Deck", "Random Event Card", "Random Event Cards"),
    DESPERATE_MEASURE(7, "desperateMeasure", 400000, "Desperate Measures Deck", "Desperate Measure Card", "Desperate Measure Cards");
    
    private int fId;
    private String fName;
    private String fDeckName;
    private String fInducementNameSingle;
    private String fInducementNameMultiple;
    private int fPrice;

    private CardType(int pId, String pName, int pPrice, String pDeckName, String pInducementNameSingle, String pInducementNameMultiple) {
        this.fId = pId;
        this.fName = pName;
        this.fPrice = pPrice;
        this.fDeckName = pDeckName;
        this.fInducementNameSingle = pInducementNameSingle;
        this.fInducementNameMultiple = pInducementNameMultiple;
    }

    @Override
    public int getId() {
        return this.fId;
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

