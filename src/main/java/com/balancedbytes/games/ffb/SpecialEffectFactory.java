/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class SpecialEffectFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public SpecialEffect forName(String pName) {
        for (SpecialEffect effect : SpecialEffect.values()) {
            if (!effect.getName().equalsIgnoreCase(pName)) continue;
            return effect;
        }
        return null;
    }

    @Override
    public SpecialEffect forId(int pId) {
        for (SpecialEffect effect : SpecialEffect.values()) {
            if (effect.getId() != pId) continue;
            return effect;
        }
        return null;
    }
}

