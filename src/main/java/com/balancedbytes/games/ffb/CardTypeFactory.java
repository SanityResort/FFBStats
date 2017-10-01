/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class CardTypeFactory
implements INamedObjectFactory {
    @Override
    public CardType forName(String pName) {
        for (CardType type : CardType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

