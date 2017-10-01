/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum ClientMode implements INamedObject
{
    PLAYER("player", "-player"),
    SPECTATOR("spectator", "-spectator"),
    REPLAY("replay", "-replay");
    
    private String fName;
    private String fArgument;

    private ClientMode(String pName, String pArgument) {
        this.fName = pName;
        this.fArgument = pArgument;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getArgument() {
        return this.fArgument;
    }
}

