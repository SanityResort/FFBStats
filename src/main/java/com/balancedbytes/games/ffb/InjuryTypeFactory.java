/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.InjuryType;

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

