package org.butterbrot.ffb.stats.collections;

import refactored.com.balancedbytes.games.ffb.PlayerState;

public class Injury {
    private String playerId;
    private InjuryState state;

    public Injury(String playerId, InjuryState state) {
        this.playerId = playerId;
        this.state = state;
    }
}
