/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class LeaderStateFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public LeaderState forName(String pName) {
        for (LeaderState state : LeaderState.values()) {
            if (!state.getName().equalsIgnoreCase(pName)) continue;
            return state;
        }
        return null;
    }

    @Override
    public LeaderState forId(int pId) {
        for (LeaderState state : LeaderState.values()) {
            if (state.getId() != pId) continue;
            return state;
        }
        return null;
    }
}

