/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum ClientMode implements IEnumWithId,
IEnumWithName
{
    PLAYER(1, "player", "-player"),
    SPECTATOR(2, "spectator", "-spectator"),
    REPLAY(3, "replay", "-replay");
    
    private int fId;
    private String fName;
    private String fArgument;

    private ClientMode(int pValue, String pName, String pArgument) {
        this.fId = pValue;
        this.fName = pName;
        this.fArgument = pArgument;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getArgument() {
        return this.fArgument;
    }
}

