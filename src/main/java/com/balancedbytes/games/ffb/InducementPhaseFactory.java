/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.InducementPhase;
import com.balancedbytes.games.ffb.util.StringTool;

public class InducementPhaseFactory
implements INamedObjectFactory {
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

