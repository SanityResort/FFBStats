/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum GameStatus implements INamedObject
{
    SCHEDULED("scheduled", "O"),
    STARTING("starting", "S"),
    ACTIVE("active", "A"),
    PAUSED("paused", "P"),
    FINISHED("finished", "F"),
    UPLOADED("uploaded", "U"),
    BACKUPED("backuped", "B"),
    LOADING("loading", "L"),
    REPLAYING("replaying", "R");
    
    private String fName;
    private String fTypeString;

    private GameStatus(String pName, String pTypeString) {
        this.fName = pName;
        this.fTypeString = pTypeString;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getTypeString() {
        return this.fTypeString;
    }
}

