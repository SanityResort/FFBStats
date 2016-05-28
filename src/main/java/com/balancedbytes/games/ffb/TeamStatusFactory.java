/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.TeamStatus;

public class TeamStatusFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public TeamStatus forId(int pId) {
        for (TeamStatus status : TeamStatus.values()) {
            if (status.getId() != pId) continue;
            return status;
        }
        return null;
    }

    @Override
    public TeamStatus forName(String pName) {
        for (TeamStatus status : TeamStatus.values()) {
            if (!status.getName().equalsIgnoreCase(pName)) continue;
            return status;
        }
        return null;
    }
}

