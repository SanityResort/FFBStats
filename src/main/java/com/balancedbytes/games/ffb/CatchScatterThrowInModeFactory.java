/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.CatchScatterThrowInMode;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class CatchScatterThrowInModeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public CatchScatterThrowInMode forId(int pId) {
        if (pId > 0) {
            for (CatchScatterThrowInMode mode : CatchScatterThrowInMode.values()) {
                if (mode.getId() != pId) continue;
                return mode;
            }
        }
        return null;
    }

    @Override
    public CatchScatterThrowInMode forName(String pName) {
        for (CatchScatterThrowInMode mode : CatchScatterThrowInMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

