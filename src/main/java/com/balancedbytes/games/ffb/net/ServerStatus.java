/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.INamedObject;

public enum ServerStatus implements INamedObject
{
    ERROR_UNKNOWN_COACH("Unknown Coach", "Unknown Coach!"),
    ERROR_WRONG_PASSWORD("Wrong Password", "Wrong Password!"),
    ERROR_GAME_IN_USE("Game In Use", "A Game with this name is already in use!"),
    ERROR_NOT_YOUR_TEAM("Not Your Team", "The team you wanted to join with is not yours!"),
    ERROR_UNKNOWN_GAME_ID("Unknown Game Id", "There is no game with the given id!"),
    ERROR_SAME_TEAM("Same Team", "You cannot play a team against itself!"),
    FUMBBL_ERROR("Fumbbl Error", "Fumbbl Error"),
    REPLAY_UNAVAILABLE("Replay Unavailable", "The replay for this game is currently unavailable.");
    
    private String fName;
    private String fMessage;

    private ServerStatus(String pName, String pMessage) {
        this.fName = pName;
        this.fMessage = pMessage;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getMessage() {
        return this.fMessage;
    }
}

