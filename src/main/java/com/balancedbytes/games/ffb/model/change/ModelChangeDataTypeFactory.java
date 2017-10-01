/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.model.change.ModelChangeDataType;

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

