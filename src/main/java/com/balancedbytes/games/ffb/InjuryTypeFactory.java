/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

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

