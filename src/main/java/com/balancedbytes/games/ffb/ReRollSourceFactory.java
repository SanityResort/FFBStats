/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.ReRollSource;

public class ReRollSourceFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ReRollSource forName(String pName) {
        for (ReRollSource source : ReRollSource.values()) {
            if (!source.getName().equalsIgnoreCase(pName)) continue;
            return source;
        }
        return null;
    }

    @Override
    public ReRollSource forId(int pId) {
        for (ReRollSource source : ReRollSource.values()) {
            if (pId != source.getId()) continue;
            return source;
        }
        return null;
    }
}

