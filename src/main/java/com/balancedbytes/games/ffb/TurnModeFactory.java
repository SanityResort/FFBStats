/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class TurnModeFactory
implements INamedObjectFactory {
    @Override
    public TurnMode forName(String pName) {
        for (TurnMode mode : TurnMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

