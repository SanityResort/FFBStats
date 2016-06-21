package org.butterbrot.ffb.stats.collections;

import refactored.com.balancedbytes.games.ffb.PlayerState;

public enum InjuryState {
    KO(PlayerState.KNOCKED_OUT), BH(PlayerState.BADLY_HURT), SI(PlayerState.SERIOUS_INJURY), RIP(PlayerState.RIP);
    private int value;
    InjuryState(int value) {
        this.value = value;
    }

    public static InjuryState fromValue(int value) {
        for (InjuryState state: values()) {
            if (state.value == value) {
                return state;
            }
        }
        return null;
    }
}
