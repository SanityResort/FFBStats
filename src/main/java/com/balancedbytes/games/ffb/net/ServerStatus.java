/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public enum ServerStatus implements IEnumWithId,
IEnumWithName
{
    ERROR_UNKNOWN_COACH(1, "Unknown Coach", "Unknown Coach!"),
    ERROR_WRONG_PASSWORD(2, "Wrong Password", "Wrong Password!"),
    ERROR_GAME_IN_USE(3, "Game In Use", "A Game with this name is already in use!"),
    ERROR_ALREADY_LOGGED_IN(4, "Already Logged In", "You are already logged in!"),
    ERROR_NOT_YOUR_TEAM(5, "Not Your Team", "The team you wanted to join with is not yours!"),
    ERROR_UNKNOWN_GAME_ID(6, "Unknown Game Id", "There is no game with the given id!"),
    ERROR_SAME_TEAM(7, "Same Team", "You cannot play a team against itself!"),
    FUMBBL_ERROR(8, "Fumbbl Error", "Fumbbl Error");
    
    private int fId;
    private String fName;
    private String fMessage;

    private ServerStatus(int pId, String pName, String pMessage) {
        this.fId = pId;
        this.fName = pName;
        this.fMessage = pMessage;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getMessage() {
        return this.fMessage;
    }
}

