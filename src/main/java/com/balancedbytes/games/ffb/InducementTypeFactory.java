/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

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

