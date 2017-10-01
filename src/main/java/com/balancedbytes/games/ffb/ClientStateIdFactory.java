/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class ClientStateIdFactory
implements INamedObjectFactory {
    @Override
    public ClientStateId forName(String pName) {
        for (ClientStateId state : ClientStateId.values()) {
            if (!state.getName().equalsIgnoreCase(pName)) continue;
            return state;
        }
        return null;
    }
}

