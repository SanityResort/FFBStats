/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.InducementPhase;
import com.balancedbytes.games.ffb.util.StringTool;

public class InducementPhaseFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public InducementPhase forName(String pName) {
        if (StringTool.isProvided(pName)) {
            for (InducementPhase phase : InducementPhase.values()) {
                if (!phase.getName().equalsIgnoreCase(pName)) continue;
                return phase;
            }
            if ("afterKickoffToOpponentResolved".equals(pName)) {
                return InducementPhase.AFTER_KICKOFF_TO_OPPONENT;
            }
        }
        return null;
    }

    @Override
    public InducementPhase forId(int pId) {
        if (pId > 0) {
            for (InducementPhase phase : InducementPhase.values()) {
                if (phase.getId() != pId) continue;
                return phase;
            }
        }
        return null;
    }

    public String getDescription(InducementPhase[] pPhases) {
        StringBuilder description = new StringBuilder();
        description.append("Play ");
        boolean firstPhase = true;
        for (InducementPhase phase : pPhases) {
            if (firstPhase) {
                firstPhase = false;
            } else {
                description.append(" or ");
            }
            description.append(phase.getDescription());
        }
        return description.toString();
    }
}

