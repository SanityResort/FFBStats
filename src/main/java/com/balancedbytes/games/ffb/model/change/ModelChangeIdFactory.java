/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;

public class ModelChangeIdFactory
implements INamedObjectFactory {
    @Override
    public ModelChangeId forName(String pName) {
        for (ModelChangeId changeId : ModelChangeId.values()) {
            if (!changeId.getName().equalsIgnoreCase(pName)) continue;
            return changeId;
        }
        return null;
    }
}

