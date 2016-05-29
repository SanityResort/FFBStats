/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.util.StringTool;

public class SendToBoxReasonFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public SendToBoxReason forName(String pName) {
        if (StringTool.isProvided(pName)) {
            for (SendToBoxReason reason : SendToBoxReason.values()) {
                if (!pName.equalsIgnoreCase(reason.getName())) continue;
                return reason;
            }
            if ("wrestle".equals(pName)) {
                return SendToBoxReason.BALL_AND_CHAIN;
            }
        }
        return null;
    }

    @Override
    public SendToBoxReason forId(int pId) {
        for (SendToBoxReason reason : SendToBoxReason.values()) {
            if (reason.getId() != pId) continue;
            return reason;
        }
        return null;
    }
}

