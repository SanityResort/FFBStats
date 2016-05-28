/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.util.StringTool;

public class PlayerTypeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
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

    @Override
    public PlayerType forId(int pId) {
        if (pId > 0) {
            for (PlayerType type : PlayerType.values()) {
                if (pId != type.getId()) continue;
                return type;
            }
        }
        return null;
    }
}

