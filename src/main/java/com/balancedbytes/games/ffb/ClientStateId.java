/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum ClientStateId implements INamedObject
{
    LOGIN("login"),
    RE_ROLL("reRoll"),
    START_GAME("startGame"),
    SELECT_PLAYER("selectPlayer"),
    MOVE("move"),
    BLOCK("block"),
    BLITZ("blitz"),
    HAND_OVER("handOver"),
    PASS("pass"),
    SPECTATE("spectate"),
    SETUP("setup"),
    KICKOFF("kickoff"),
    PUSHBACK("pushback"),
    INTERCEPTION("interception"),
    FOUL("foul"),
    HIGH_KICK("highKick"),
    QUICK_SNAP("quickSnap"),
    TOUCHBACK("touchback"),
    WAIT_FOR_OPPONENT("waitForOpponent"),
    REPLAY("replay"),
    THROW_TEAM_MATE("throwTeamMate"),
    DUMP_OFF("dumpOff"),
    WAIT_FOR_SETUP("waitForSetup"),
    GAZE("gaze"),
    KICKOFF_RETURN("kickoffReturn"),
    WIZARD("wizard"),
    PASS_BLOCK("passBlock"),
    BOMB("bomb"),
    ILLEGAL_SUBSTITUTION("illegalSubstitution");
    
    private String fName;

    private ClientStateId(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String toString() {
        return this.getName();
    }
}

