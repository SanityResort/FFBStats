/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.ConcedeGameStatus;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;

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

