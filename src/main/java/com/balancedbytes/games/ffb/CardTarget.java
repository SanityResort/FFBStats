/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum CardTarget {
    TURN(1, "turn"),
    OWN_PLAYER(2, "ownPlayer"),
    OPPOSING_PLAYER(3, "opposingPlayer"),
    ANY_PLAYER(4, "anyPlayer");
    
    private int fId;
    private String fName;

    private CardTarget(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    public int getId() {
        return this.fId;
    }

    public String getName() {
        return this.fName;
    }

    public boolean isPlayedOnPlayer() {
        return this == OWN_PLAYER || this == OPPOSING_PLAYER || this == ANY_PLAYER;
    }

    public static CardTarget fromId(int pId) {
        for (CardTarget target : CardTarget.values()) {
            if (target.getId() != pId) continue;
            return target;
        }
        return null;
    }

    public static CardTarget fromName(String pName) {
        for (CardTarget target : CardTarget.values()) {
            if (!target.getName().equalsIgnoreCase(pName)) continue;
            return target;
        }
        return null;
    }
}

