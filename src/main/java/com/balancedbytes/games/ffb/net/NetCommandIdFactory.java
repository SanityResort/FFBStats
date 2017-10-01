/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;

public class NetCommandIdFactory
implements INamedObjectFactory {
    @Override
    public NetCommandId forName(String pName) {
        for (NetCommandId commandId : NetCommandId.values()) {
            if (!commandId.getName().equalsIgnoreCase(pName)) continue;
            return commandId;
        }
        return null;
    }
}

