/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.model.AnimationType;

public class AnimationTypeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public AnimationType forName(String pName) {
        for (AnimationType type : AnimationType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public AnimationType forId(int pId) {
        for (AnimationType type : AnimationType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

