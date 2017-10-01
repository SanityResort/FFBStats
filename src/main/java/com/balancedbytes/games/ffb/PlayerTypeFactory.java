/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.util.StringTool;

public class PlayerTypeFactory
implements INamedObjectFactory {
    @Override
    public PlayerType forName(String pName) {
        if (StringTool.isProvided(pName)) {
            for (PlayerType type : PlayerType.values()) {
                if (!pName.equalsIgnoreCase(type.getName())) continue;
                return type;
            }
            if (StringTool.isProvided(pName) && pName.equalsIgnoreCase("Normal")) {
                return PlayerType.REGULAR;
            }
        }
        return null;
    }
}

