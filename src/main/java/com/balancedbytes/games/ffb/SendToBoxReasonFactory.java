/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.util.StringTool;

public class SendToBoxReasonFactory
implements INamedObjectFactory {
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
}

