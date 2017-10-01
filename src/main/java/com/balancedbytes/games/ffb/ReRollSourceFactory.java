/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.ReRollSource;

public class ReRollSourceFactory
implements INamedObjectFactory {
    @Override
    public ReRollSource forName(String pName) {
        for (ReRollSource source : ReRollSource.values()) {
            if (!source.getName().equalsIgnoreCase(pName)) continue;
            return source;
        }
        return null;
    }
}

