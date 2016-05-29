/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class PlayerActionFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public PlayerAction forName(String pName) {
        for (PlayerAction action : PlayerAction.values()) {
            if (!action.getName().equalsIgnoreCase(pName)) continue;
            return action;
        }
        return null;
    }

    @Override
    public PlayerAction forId(int pId) {
        for (PlayerAction action : PlayerAction.values()) {
            if (action.getId() != pId) continue;
            return action;
        }
        return null;
    }
}

