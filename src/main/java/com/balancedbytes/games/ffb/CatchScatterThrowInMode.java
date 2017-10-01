/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum CatchScatterThrowInMode implements INamedObject
{
    CATCH_ACCURATE_PASS("catchAccuratePass", false),
    CATCH_HAND_OFF("catchHandOff", false),
    CATCH_SCATTER("catchScatter", false),
    SCATTER_BALL("scatterBall", false),
    THROW_IN("throwIn", false),
    CATCH_MISSED_PASS("catchMissedPass", false),
    CATCH_KICKOFF("catchKickoff", false),
    CATCH_THROW_IN("catchThrowIn", false),
    FAILED_CATCH("failedCatch", false),
    FAILED_PICK_UP("failedPickUp", false),
    CATCH_ACCURATE_BOMB("catchAccurateBomb", true),
    CATCH_BOMB("catchBomb", true);
    
    private String fName;
    private boolean fBomb;

    private CatchScatterThrowInMode(String pName, boolean pBomb) {
        this.fName = pName;
        this.fBomb = pBomb;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isBomb() {
        return this.fBomb;
    }
}

