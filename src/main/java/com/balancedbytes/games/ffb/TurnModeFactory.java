/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.TurnMode;

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

