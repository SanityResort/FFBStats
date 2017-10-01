/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObjectFactory;

public class ReportIdFactory
implements INamedObjectFactory {
    @Override
    public ReportId forName(String pName) {
        for (ReportId mode : ReportId.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

