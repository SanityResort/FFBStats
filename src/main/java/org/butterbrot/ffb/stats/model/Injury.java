package org.butterbrot.ffb.stats.model;

public class Injury  implements Data {
    private String playerId;
    private InjuryState state;

    public Injury(String playerId, InjuryState state) {
        this.playerId = playerId;
        this.state = state;
    }
}
