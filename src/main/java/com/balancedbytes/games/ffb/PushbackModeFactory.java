/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.PushbackMode;

public class PushbackModeFactory
implements INamedObjectFactory {
    @Override
    public PushbackMode forName(String pName) {
        for (PushbackMode mode : PushbackMode.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

