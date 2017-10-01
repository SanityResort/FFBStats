/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class CatchScatterThrowInModeFactory
implements INamedObjectFactory {
    @Override
    public CatchScatterThrowInMode forName(String pName) {
        for (CatchScatterThrowInMode mode : CatchScatterThrowInMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

