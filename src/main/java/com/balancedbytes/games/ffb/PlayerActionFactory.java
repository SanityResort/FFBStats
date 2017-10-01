/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.PlayerAction;

public class PlayerActionFactory
implements INamedObjectFactory {
    @Override
    public PlayerAction forName(String pName) {
        for (PlayerAction action : PlayerAction.values()) {
            if (!action.getName().equalsIgnoreCase(pName)) continue;
            return action;
        }
        return null;
    }
}

