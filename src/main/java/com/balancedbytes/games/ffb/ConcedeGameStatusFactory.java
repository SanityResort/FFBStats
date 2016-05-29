/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class ConcedeGameStatusFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ConcedeGameStatus forName(String pName) {
        for (ConcedeGameStatus status : ConcedeGameStatus.values()) {
            if (!status.getName().equalsIgnoreCase(pName)) continue;
            return status;
        }
        return null;
    }

    @Override
    public ConcedeGameStatus forId(int pId) {
        for (ConcedeGameStatus status : ConcedeGameStatus.values()) {
            if (status.getId() != pId) continue;
            return status;
        }
        return null;
    }
}

