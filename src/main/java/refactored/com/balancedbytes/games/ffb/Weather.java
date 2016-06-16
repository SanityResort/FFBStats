/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb;

public enum Weather implements IEnumWithId,
IEnumWithName
{
    SWELTERING_HEAT(1, "Sweltering Heat", "heat", "Each player on the pitch may suffer from heat exhaustion on a roll of 1 before the next kick-off."),
    VERY_SUNNY(2, "Very Sunny", "sunny", "A -1 modifier applies to all passing rolls."),
    NICE(3, "Nice Weather", "nice", "Perfect Fantasy Football weather."),
    POURING_RAIN(4, "Pouring Rain", "rain", "A -1 modifier applies to all catch, intercept, or pick-up rolls."),
    BLIZZARD(5, "Blizzard", "blizzard", "Going For It fails on a roll of 1 or 2 and only quick or short passes can be attempted."),
    INTRO(6, "Intro", "intro", "No weather at all, but the intro screen shown by the client.");
    
    private int fId;
    private String fName;
    private String fShortName;
    private String fDescription;

    private Weather(int pId, String pName, String pShortName, String pDescription) {
        this.fId = pId;
        this.fName = pName;
        this.fShortName = pShortName;
        this.fDescription = pDescription;
    }

    @Override
    public int getId() {
        return this.fId;
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

