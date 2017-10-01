/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.INamedObjectFactory;

public class ModelChangeDataTypeFactory
implements INamedObjectFactory {
    @Override
    public ModelChangeDataType forName(String pName) {
        for (ModelChangeDataType type : ModelChangeDataType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

