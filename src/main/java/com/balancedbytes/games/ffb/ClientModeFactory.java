/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class ClientModeFactory
implements INamedObjectFactory {
    @Override
    public ClientMode forName(String pName) {
        for (ClientMode mode : ClientMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }

    public ClientMode forArgument(String pArgument) {
        for (ClientMode mode : ClientMode.values()) {
            if (mode.getArgument() == null || !mode.getArgument().equalsIgnoreCase(pArgument)) continue;
            return mode;
        }
        return null;
    }
}

