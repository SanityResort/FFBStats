/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.SeriousInjury;

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

