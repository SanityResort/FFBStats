/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.PlayerChoiceMode;

public class PlayerChoiceModeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public PlayerChoiceMode forName(String pName) {
        for (PlayerChoiceMode type : PlayerChoiceMode.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public PlayerChoiceMode forId(int pId) {
        for (PlayerChoiceMode type : PlayerChoiceMode.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

