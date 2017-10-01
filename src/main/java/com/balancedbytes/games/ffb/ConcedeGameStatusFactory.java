/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class ConcedeGameStatusFactory
implements INamedObjectFactory {
    @Override
    public ConcedeGameStatus forName(String pName) {
        for (ConcedeGameStatus status : ConcedeGameStatus.values()) {
            if (!status.getName().equalsIgnoreCase(pName)) continue;
            return status;
        }
        return null;
    }
}

