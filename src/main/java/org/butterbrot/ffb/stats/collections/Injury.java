package org.butterbrot.ffb.stats.collections;

public class Injury {
    private String playerId;
    private InjuryState state;

    public Injury(String playerId, InjuryState state) {
        this.playerId = playerId;
        this.state = state;
    }
}
