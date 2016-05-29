/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class TurnModeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public TurnMode forId(int pId) {
        if (pId > 0) {
            for (TurnMode mode : TurnMode.values()) {
                if (mode.getId() != pId) continue;
                return mode;
            }
        }
        return null;
    }

    @Override
    public TurnMode forName(String pName) {
        for (TurnMode mode : TurnMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

