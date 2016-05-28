/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.model.change.ModelChangeDataType;

public class ModelChangeDataTypeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ModelChangeDataType forName(String pName) {
        for (ModelChangeDataType type : ModelChangeDataType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public ModelChangeDataType forId(int pId) {
        for (ModelChangeDataType type : ModelChangeDataType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

