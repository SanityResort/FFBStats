/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum CatchScatterThrowInMode implements IEnumWithId,
IEnumWithName
{
    CATCH_ACCURATE_PASS(1, "catchAccuratePass", false),
    CATCH_HAND_OFF(2, "catchHandOff", false),
    CATCH_SCATTER(3, "catchScatter", false),
    SCATTER_BALL(4, "scatterBall", false),
    THROW_IN(5, "throwIn", false),
    CATCH_MISSED_PASS(6, "catchMissedPass", false),
    CATCH_KICKOFF(7, "catchKickoff", false),
    CATCH_THROW_IN(8, "catchThrowIn", false),
    FAILED_CATCH(9, "failedCatch", false),
    FAILED_PICK_UP(10, "failedPickUp", false),
    CATCH_ACCURATE_BOMB(11, "catchAccurateBomb", true),
    CATCH_BOMB(12, "catchBomb", true);
    
    private int fId;
    private String fName;
    private boolean fBomb;

    private CatchScatterThrowInMode(int pValue, String pName, boolean pBomb) {
        this.fId = pValue;
        this.fName = pName;
        this.fBomb = pBomb;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isBomb() {
        return this.fBomb;
    }
}

