/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.SeriousInjury;

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

