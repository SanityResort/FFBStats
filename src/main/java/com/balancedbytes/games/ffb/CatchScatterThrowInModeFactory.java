/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.CatchScatterThrowInMode;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;

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

