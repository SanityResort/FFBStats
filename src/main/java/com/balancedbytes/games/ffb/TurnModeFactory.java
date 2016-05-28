/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.TurnMode;

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

