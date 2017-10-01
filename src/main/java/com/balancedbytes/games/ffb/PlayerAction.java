/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum PlayerAction implements INamedObject
{
    MOVE("move", 1, "starts a Move Action"),
    BLOCK("block", 2, "starts a Block Action"),
    BLITZ("blitz", 3, null),
    BLITZ_MOVE("blitzMove", 3, "starts a Blitz Action"),
    HAND_OVER("handOver", 5, null),
    HAND_OVER_MOVE("handOverMove", 5, "starts a Hand Over Action"),
    PASS("pass", 7, null),
    PASS_MOVE("passMove", 7, "starts a Pass Action"),
    FOUL("foul", 9, null),
    FOUL_MOVE("foulMove", 9, "starts a Foul Action"),
    STAND_UP("standUp", 11, "stands up"),
    THROW_TEAM_MATE("throwTeamMate", 12, null),
    THROW_TEAM_MATE_MOVE("throwTeamMateMove", 12, null),
    REMOVE_CONFUSION("removeConfusion", 14, null),
    GAZE("gaze", 15, null),
    MULTIPLE_BLOCK("multipleBlock", 16, "starts a Block Action"),
    HAIL_MARY_PASS("hailMaryPass", 7, null),
    DUMP_OFF("dumpOff", 7, null),
    STAND_UP_BLITZ("standUpBlitz", 3, "stands up with Blitz"),
    THROW_BOMB("throwBomb", 20, "starts a Bomb Action"),
    HAIL_MARY_BOMB("hailMaryBomb", 21, null);
    
    private String fName;
    private int fType;
    private String fDescription;

    private PlayerAction(String pName, int pType, String pDescription) {
        this.fName = pName;
        this.fType = pType;
        this.fDescription = pDescription;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public int getType() {
        return this.fType;
    }

    public String getDescription() {
        return this.fDescription;
    }

    public boolean isMoving() {
        return this == MOVE || this == BLITZ_MOVE || this == HAND_OVER_MOVE || this == PASS_MOVE || this == FOUL_MOVE || this == THROW_TEAM_MATE_MOVE;
    }

    public boolean isPassing() {
        return this == PASS || this == DUMP_OFF || this == HAND_OVER || this == HAIL_MARY_PASS || this == THROW_BOMB || this == HAIL_MARY_BOMB;
    }
}

