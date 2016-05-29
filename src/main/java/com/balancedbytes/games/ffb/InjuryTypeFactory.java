/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class InjuryTypeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public InjuryType forName(String pName) {
        for (InjuryType type : InjuryType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public InjuryType forId(int pId) {
        for (InjuryType type : InjuryType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

