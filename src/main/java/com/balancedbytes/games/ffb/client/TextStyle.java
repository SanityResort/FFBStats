/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

public enum TextStyle {
    NONE(""),
    BOLD("bold"),
    HOME("home"),
    HOME_BOLD("homeBold"),
    AWAY("away"),
    AWAY_BOLD("awayBold"),
    SPECTATOR("spectator"),
    ROLL("roll"),
    NEEDED_ROLL("neededRoll"),
    EXPLANATION("explanation"),
    TURN("turn"),
    TURN_HOME("turnHome"),
    TURN_AWAY("turnAway");
    
    private String fName;

    private TextStyle(String pName) {
        this.fName = pName;
    }

    public String getName() {
        return this.fName;
    }
}

