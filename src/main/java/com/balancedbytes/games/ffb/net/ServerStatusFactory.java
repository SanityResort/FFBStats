/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.net.ServerStatus;

public class ServerStatusFactory
implements INamedObjectFactory {
    @Override
    public ServerStatus forName(String pName) {
        for (ServerStatus serverStatus : ServerStatus.values()) {
            if (!serverStatus.getName().equalsIgnoreCase(pName)) continue;
            return serverStatus;
        }
        return null;
    }
}

