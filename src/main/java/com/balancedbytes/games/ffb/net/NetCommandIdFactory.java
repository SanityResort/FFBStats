/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;

public class NetCommandIdFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public NetCommandId forName(String pName) {
        for (NetCommandId commandId : NetCommandId.values()) {
            if (!commandId.getName().equalsIgnoreCase(pName)) continue;
            return commandId;
        }
        return null;
    }

    @Override
    public NetCommandId forId(int pId) {
        for (NetCommandId commandId : NetCommandId.values()) {
            if (commandId.getId() != pId) continue;
            return commandId;
        }
        return null;
    }
}

