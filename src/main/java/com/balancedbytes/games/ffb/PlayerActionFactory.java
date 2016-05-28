/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.PlayerAction;

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

