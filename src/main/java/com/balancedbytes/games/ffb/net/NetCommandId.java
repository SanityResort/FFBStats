/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.net.commands.ClientCommandReplay;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;

public enum NetCommandId implements IEnumWithId,
        IEnumWithName {
    SERVER_GAME_STATE(4, "serverGameState"),
    SERVER_MODEL_SYNC(40, "serverModelSync"),
    CLIENT_REPLAY(47, "clientReplay"),
    SERVER_REPLAY(48, "serverReplay");

    private int fId;
    private String fName;

    NetCommandId(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public NetCommand createNetCommand() {
        switch (this) {

            case SERVER_GAME_STATE: {
                return new ServerCommandGameState();
            }
            case SERVER_MODEL_SYNC: {
                return new ServerCommandModelSync();
            }
            case SERVER_REPLAY: {
                return new ServerCommandReplay();
            }
            case CLIENT_REPLAY: {
                return new ClientCommandReplay();
            }

        }
        throw new IllegalStateException("Unhandled netCommandId " + this + ".");
    }

}

