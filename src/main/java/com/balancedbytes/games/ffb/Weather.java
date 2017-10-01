/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum Weather implements INamedObject
{
    SWELTERING_HEAT("Sweltering Heat", "heat", "Each player on the pitch may suffer from heat exhaustion on a roll of 1 before the next kick-off."),
    VERY_SUNNY("Very Sunny", "sunny", "A -1 modifier applies to all passing rolls."),
    NICE("Nice Weather", "nice", "Perfect Fantasy Football weather."),
    POURING_RAIN("Pouring Rain", "rain", "A -1 modifier applies to all catch, intercept, or pick-up rolls."),
    BLIZZARD("Blizzard", "blizzard", "Going For It fails on a roll of 1 or 2 and only quick or short passes can be attempted."),
    INTRO("Intro", "intro", "No weather at all, but the intro screen shown by the client.");
    
    private String fName;
    private String fShortName;
    private String fDescription;

    private Weather(String pName, String pShortName, String pDescription) {
        this.fName = pName;
        this.fShortName = pShortName;
        this.fDescription = pDescription;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getShortName() {
        return this.fShortName;
    }

    public String getDescription() {
        return this.fDescription;
    }
}

