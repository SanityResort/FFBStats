/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class GameStatusFactory
implements IEnumWithNameFactory {
    public GameStatus forId(int pId) {
        for (GameStatus status : GameStatus.values()) {
            if (status.getId() != pId) continue;
            return status;
        }
        return null;
    }

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

