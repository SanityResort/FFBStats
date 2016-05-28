/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;

public class ModelChangeIdFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ModelChangeId forName(String pName) {
        for (ModelChangeId changeId : ModelChangeId.values()) {
            if (!changeId.getName().equalsIgnoreCase(pName)) continue;
            return changeId;
        }
        return null;
    }

    @Override
    public ModelChangeId forId(int pId) {
        for (ModelChangeId changeId : ModelChangeId.values()) {
            if (changeId.getId() != pId) continue;
            return changeId;
        }
        return null;
    }
}

