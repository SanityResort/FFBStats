/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net;

public class GameCoach {
    private String fGame;
    private String fCoach;
    private int fHashCode;

    public GameCoach(String pGame, String pCoach) {
        this.fGame = pGame;
        this.fCoach = pCoach;
    }

    public String getGame() {
        return this.fGame;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public boolean equals(Object pObject) {
        boolean result = pObject instanceof GameCoach;
        if (result) {
            GameCoach otherGameCoach = (GameCoach)pObject;
            result = this.getGame().equals(otherGameCoach.getGame()) && this.getCoach().equals(otherGameCoach.getCoach());
        }
        return result;
    }

    public int hashCode() {
        if (this.fHashCode == 0) {
            this.fHashCode = (this.getGame() + ':' + this.getCoach()).hashCode();
        }
        return this.fHashCode;
    }
}

