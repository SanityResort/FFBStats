/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.PlayerChoiceMode;

public class PlayerChoiceModeFactory
implements INamedObjectFactory {
    @Override
    public PlayerChoiceMode forName(String pName) {
        for (PlayerChoiceMode type : PlayerChoiceMode.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

