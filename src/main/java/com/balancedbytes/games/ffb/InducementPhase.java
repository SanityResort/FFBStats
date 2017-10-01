/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum InducementPhase implements INamedObject
{
    END_OF_OWN_TURN("endOfOwnTurn", "at end of own turn"),
    START_OF_OWN_TURN("startOfOwnTurn", "at start of own turn"),
    AFTER_KICKOFF_TO_OPPONENT("afterKickoffToOpponent", "after Kickoff to opponent"),
    AFTER_INDUCEMENTS_PURCHASED("afterInducementsPurchased", "after Inducements are purchased"),
    BEFORE_KICKOFF_SCATTER("beforeKickoffScatter", "before Kickoff Scatter"),
    END_OF_TURN_NOT_HALF("endOfTurnNotHalf", "at end of turn, not half"),
    BEFORE_SETUP("beforeSetup", "before setting up");
    
    private String fName;
    private String fDescription;

    private InducementPhase(String pName, String pDescription) {
        this.fName = pName;
        this.fDescription = pDescription;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getDescription() {
        return this.fDescription;
    }
}

