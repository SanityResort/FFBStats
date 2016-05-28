/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.net.ServerStatus;

public class ServerStatusFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ServerStatus forName(String pName) {
        for (ServerStatus serverStatus : ServerStatus.values()) {
            if (!serverStatus.getName().equalsIgnoreCase(pName)) continue;
            return serverStatus;
        }
        return null;
    }

    @Override
    public ServerStatus forId(int pId) {
        if (pId > 0) {
            for (ServerStatus serverStatus : ServerStatus.values()) {
                if (pId != serverStatus.getId()) continue;
                return serverStatus;
            }
        }
        return null;
    }
}

