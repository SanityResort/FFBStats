/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.model.change;

import refactored.com.balancedbytes.games.ffb.IEnumWithIdFactory;
import refactored.com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class ModelChangeIdFactory implements IEnumWithIdFactory, IEnumWithNameFactory {
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

