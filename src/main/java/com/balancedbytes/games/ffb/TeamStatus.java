/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum TeamStatus implements INamedObject
{
    NEW(0, "New"),
    ACTIVE(1, "Active"),
    PENDING_APPROVAL(2, "Pending Approval"),
    BLOCKED(3, "Blocked"),
    RETIRED(4, "Retired"),
    WAITING_FOR_OPPONENT(5, "Waiting for Opponent"),
    SKILL_ROLLS_PENDING(6, "Skill Rolls Pending");
    
    private int fId;
    private String fName;

    private TeamStatus(int pId, String pName) {
        this.fId = pId;
        this.fName = pName;
    }

    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

