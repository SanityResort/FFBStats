/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.InducementType;

public class InducementTypeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public InducementType forName(String pName) {
        for (InducementType type : InducementType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public InducementType forId(int pId) {
        for (InducementType type : InducementType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

