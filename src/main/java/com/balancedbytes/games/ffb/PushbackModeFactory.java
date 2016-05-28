/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.PushbackMode;

public class PushbackModeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public PushbackMode forName(String pName) {
        for (PushbackMode mode : PushbackMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }

    @Override
    public PushbackMode forId(int pId) {
        for (PushbackMode mode : PushbackMode.values()) {
            if (mode.getId() != pId) continue;
            return mode;
        }
        return null;
    }
}

