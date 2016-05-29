/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class ClientModeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ClientMode forName(String pName) {
        for (ClientMode mode : ClientMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }

    @Override
    public ClientMode forId(int pId) {
        for (ClientMode mode : ClientMode.values()) {
            if (mode.getId() != pId) continue;
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

