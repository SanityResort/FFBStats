/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;

public class CardFactory
implements INamedObjectFactory {
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

