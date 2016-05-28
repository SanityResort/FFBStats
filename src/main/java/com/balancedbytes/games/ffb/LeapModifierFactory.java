/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.LeapModifier;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.UtilCards;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class LeapModifierFactory
implements IRollModifierFactory {
    @Override
    public LeapModifier forId(int pId) {
        for (LeapModifier modifier : LeapModifier.values()) {
            if (modifier.getId() != pId) continue;
            return modifier;
        }
        return null;
    }

    @Override
    public LeapModifier forName(String pName) {
        for (LeapModifier modifier : LeapModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<LeapModifier> findLeapModifiers(Game pGame) {
        HashSet<LeapModifier> leapModifiers = new HashSet<LeapModifier>();
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        if (UtilCards.hasSkill(pGame, actingPlayer, Skill.VERY_LONG_LEGS)) {
            leapModifiers.add(LeapModifier.VERY_LONG_LEGS);
        }
        return leapModifiers;
    }

    public LeapModifier[] toArray(Set<LeapModifier> pLeapModifierSet) {
        if (pLeapModifierSet != null) {
            LeapModifier[] leapModifierArray = pLeapModifierSet.toArray(new LeapModifier[pLeapModifierSet.size()]);
            Arrays.sort(leapModifierArray, new Comparator<LeapModifier>(){

                @Override
                public int compare(LeapModifier pO1, LeapModifier pO2) {
                    return pO1.getId() - pO2.getId();
                }
            });
            return leapModifierArray;
        }
        return new LeapModifier[0];
    }

}

