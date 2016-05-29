/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class SeriousInjuryFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public SeriousInjury forId(int pId) {
        for (SeriousInjury seriousInjury : SeriousInjury.values()) {
            if (seriousInjury.getId() != pId) continue;
            return seriousInjury;
        }
        return null;
    }

    @Override
    public SeriousInjury forName(String pName) {
        for (SeriousInjury seriousInjury : SeriousInjury.values()) {
            if (!seriousInjury.getName().equals(pName)) continue;
            return seriousInjury;
        }
        return null;
    }
}

