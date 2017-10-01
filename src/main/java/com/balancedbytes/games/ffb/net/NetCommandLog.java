/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.net.NetCommand;
import java.util.ArrayList;
import java.util.List;

public class NetCommandLog {
    private List<NetCommand> fCommands = new ArrayList<NetCommand>();

    public void add(NetCommand pNetCommand) {
        this.fCommands.add(pNetCommand);
    }

    public NetCommand[] getCommands() {
        return this.fCommands.toArray(new NetCommand[this.fCommands.size()]);
    }
}

