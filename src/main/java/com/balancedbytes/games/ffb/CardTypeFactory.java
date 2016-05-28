/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class CardTypeFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public CardType forName(String pName) {
        for (CardType type : CardType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public CardType forId(int pId) {
        for (CardType type : CardType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

