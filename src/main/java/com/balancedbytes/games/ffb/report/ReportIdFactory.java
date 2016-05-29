/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class ReportIdFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ReportId forId(int pId) {
        if (pId > 0) {
            for (ReportId mode : ReportId.values()) {
                if (mode.getId() != pId) continue;
                return mode;
            }
        }
        return null;
    }

    @Override
    public ReportId forName(String pName) {
        for (ReportId mode : ReportId.values()) {
            if (!mode.getName().equalsIgnoreCase(pName)) continue;
            return mode;
        }
        return null;
    }
}

