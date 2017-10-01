/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.InjuryType;

public class InjuryTypeFactory
implements INamedObjectFactory {
    @Override
    public InjuryType forName(String pName) {
        for (InjuryType type : InjuryType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

