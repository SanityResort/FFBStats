/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;

public class CardEffectFactory
implements INamedObjectFactory {
    @Override
    public CardEffect forName(String pName) {
        for (CardEffect effect : CardEffect.values()) {
            if (!effect.getName().equalsIgnoreCase(pName)) continue;
            return effect;
        }
        return null;
    }
}

