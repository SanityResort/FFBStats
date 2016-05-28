/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum InducementPhase implements IEnumWithId,
IEnumWithName
{
    END_OF_OWN_TURN(1, "endOfOwnTurn", "at end of own turn"),
    START_OF_OWN_TURN(2, "startOfOwnTurn", "at start of own turn"),
    AFTER_KICKOFF_TO_OPPONENT(3, "afterKickoffToOpponent", "after Kickoff to opponent"),
    AFTER_INDUCEMENTS_PURCHASED(4, "afterInducementsPurchased", "after Inducements are purchased"),
    BEFORE_KICKOFF_SCATTER(5, "beforeKickoffScatter", "before Kickoff Scatter"),
    END_OF_TURN_NOT_HALF(6, "endOfTurnNotHalf", "at end of turn, not half"),
    BEFORE_SETUP(7, "beforeSetup", "before setting up");
    
    private int fId;
    private String fName;
    private String fDescription;

    private InducementPhase(int pValue, String pName, String pDescription) {
        this.fId = pValue;
        this.fName = pName;
        this.fDescription = pDescription;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getDescription() {
        return this.fDescription;
    }
}

