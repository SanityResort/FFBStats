/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.InducementType;

public class InducementTypeFactory
implements INamedObjectFactory {
    @Override
    public InducementType forName(String pName) {
        for (InducementType type : InducementType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

