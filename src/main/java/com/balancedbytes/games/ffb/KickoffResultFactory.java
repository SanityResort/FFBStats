/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.KickoffResult;

public class KickoffResultFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public KickoffResult forName(String pName) {
        for (KickoffResult kickoff : KickoffResult.values()) {
            if (!kickoff.getName().equalsIgnoreCase(pName)) continue;
            return kickoff;
        }
        return null;
    }

    @Override
    public KickoffResult forId(int pId) {
        for (KickoffResult kickoff : KickoffResult.values()) {
            if (kickoff.getId() != pId) continue;
            return kickoff;
        }
        return null;
    }
}

