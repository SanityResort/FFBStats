/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

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

