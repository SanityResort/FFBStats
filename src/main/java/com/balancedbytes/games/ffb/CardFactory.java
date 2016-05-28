/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class CardFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public Card forId(int pId) {
        for (Card card : Card.values()) {
            if (card.getId() != pId) continue;
            return card;
        }
        return null;
    }

    @Override
    public Card forName(String pName) {
        for (Card card : Card.values()) {
            if (!card.getName().equalsIgnoreCase(pName)) continue;
            return card;
        }
        return null;
    }

    public Card forShortName(String pName) {
        for (Card card : Card.values()) {
            if (!card.getShortName().equalsIgnoreCase(pName)) continue;
            return card;
        }
        return null;
    }
}

