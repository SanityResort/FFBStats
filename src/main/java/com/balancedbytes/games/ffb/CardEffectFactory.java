/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class CardEffectFactory
implements IEnumWithNameFactory {
    @Override
    public CardEffect forName(String pName) {
        for (CardEffect effect : CardEffect.values()) {
            if (!effect.getName().equalsIgnoreCase(pName)) continue;
            return effect;
        }
        return null;
    }
}

