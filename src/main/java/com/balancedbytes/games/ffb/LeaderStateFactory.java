/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class LeaderStateFactory
implements INamedObjectFactory {
    @Override
    public LeaderState forName(String pName) {
        for (LeaderState state : LeaderState.values()) {
            if (!state.getName().equalsIgnoreCase(pName)) continue;
            return state;
        }
        return null;
    }
}
