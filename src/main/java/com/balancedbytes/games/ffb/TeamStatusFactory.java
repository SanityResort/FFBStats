/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.TeamStatus;

public class TeamStatusFactory
implements INamedObjectFactory {
    public TeamStatus forId(int pId) {
        switch (pId) {
            case 0: {
                return TeamStatus.NEW;
            }
            case 1: {
                return TeamStatus.ACTIVE;
            }
            case 2: {
                return TeamStatus.PENDING_APPROVAL;
            }
            case 3: {
                return TeamStatus.BLOCKED;
            }
            case 4: {
                return TeamStatus.RETIRED;
            }
            case 5: {
                return TeamStatus.WAITING_FOR_OPPONENT;
            }
            case 6: {
                return TeamStatus.SKILL_ROLLS_PENDING;
            }
        }
        return null;
    }

    @Override
    public TeamStatus forName(String pName) {
        for (TeamStatus status : TeamStatus.values()) {
            if (!status.getName().equalsIgnoreCase(pName)) continue;
            return status;
        }
        return null;
    }
}

