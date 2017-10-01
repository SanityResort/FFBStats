/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.model.AnimationType;

public class AnimationTypeFactory
implements INamedObjectFactory {
    @Override
    public AnimationType forName(String pName) {
        for (AnimationType type : AnimationType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

