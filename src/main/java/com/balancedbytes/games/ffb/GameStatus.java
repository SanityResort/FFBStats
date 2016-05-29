/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum GameStatus implements IEnumWithId,
IEnumWithName
{
    SCHEDULED(1, "scheduled", "O"),
    STARTING(2, "starting", "S"),
    ACTIVE(3, "active", "A"),
    PAUSED(4, "paused", "P"),
    FINISHED(5, "finished", "F"),
    UPLOADED(6, "uploaded", "U");
    
    private int fId;
    private String fName;
    private String fTypeString;

    private GameStatus(int pValue, String pName, String pTypeString) {
        this.fId = pValue;
        this.fName = pName;
        this.fTypeString = pTypeString;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getTypeString() {
        return this.fTypeString;
    }
}

