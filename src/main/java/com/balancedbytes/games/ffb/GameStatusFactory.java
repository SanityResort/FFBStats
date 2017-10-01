/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class GameStatusFactory
implements INamedObjectFactory {
    @Override
    public GameStatus forName(String pName) {
        for (GameStatus status : GameStatus.values()) {
            if (!status.getName().equalsIgnoreCase(pName)) continue;
            return status;
        }
        return null;
    }

    public GameStatus forTypeString(String pTypeString) {
        for (GameStatus status : GameStatus.values()) {
            if (!status.getTypeString().equals(pTypeString)) continue;
            return status;
        }
        return null;
    }
}

