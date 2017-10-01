/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum Direction implements INamedObject
{
    NORTH("North"),
    NORTHEAST("Northeast"),
    EAST("East"),
    SOUTHEAST("Southeast"),
    SOUTH("South"),
    SOUTHWEST("Southwest"),
    WEST("West"),
    NORTHWEST("Northwest");
    
    private String fName;

    private Direction(String pName) {
        this.fName = pName;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

