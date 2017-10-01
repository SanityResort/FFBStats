/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class SeriousInjuryFactory
implements INamedObjectFactory {
    @Override
    public SeriousInjury forName(String pName) {
        for (SeriousInjury seriousInjury : SeriousInjury.values()) {
            if (!seriousInjury.getName().equals(pName)) continue;
            return seriousInjury;
        }
        return null;
    }
}

