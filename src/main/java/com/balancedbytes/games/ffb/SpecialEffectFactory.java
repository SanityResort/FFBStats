/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class SpecialEffectFactory
implements INamedObjectFactory {
    @Override
    public SpecialEffect forName(String pName) {
        for (SpecialEffect effect : SpecialEffect.values()) {
            if (!effect.getName().equalsIgnoreCase(pName)) continue;
            return effect;
        }
        return null;
    }
}

