/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class ClientStateIdFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ClientStateId forName(String pName) {
        for (ClientStateId state : ClientStateId.values()) {
            if (!state.getName().equalsIgnoreCase(pName)) continue;
            return state;
        }
        return null;
    }

    @Override
    public ClientStateId forId(int pId) {
        for (ClientStateId state : ClientStateId.values()) {
            if (state.getId() != pId) continue;
            return state;
        }
        return null;
    }
}

