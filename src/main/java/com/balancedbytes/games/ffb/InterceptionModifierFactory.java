/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilDisturbingPresence;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class InterceptionModifierFactory
implements IRollModifierFactory {
    @Override
    public InterceptionModifier forName(String pName) {
        for (InterceptionModifier modifier : InterceptionModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<InterceptionModifier> findInterceptionModifiers(Game pGame, Player pPlayer) {
        HashSet<InterceptionModifier> interceptionModifiers = new HashSet<InterceptionModifier>();
        if (Weather.POURING_RAIN == pGame.getFieldModel().getWeather()) {
            interceptionModifiers.add(InterceptionModifier.POURING_RAIN);
        }
        if (UtilCards.hasSkill(pGame, pPlayer, Skill.EXTRA_ARMS)) {
            interceptionModifiers.add(InterceptionModifier.EXTRA_ARMS);
        }
        if (UtilCards.hasSkill(pGame, pPlayer, Skill.VERY_LONG_LEGS)) {
            interceptionModifiers.add(InterceptionModifier.VERY_LONG_LEGS);
        }
        if (UtilCards.hasSkill(pGame, pPlayer, Skill.NERVES_OF_STEEL)) {
            interceptionModifiers.add(InterceptionModifier.NERVES_OF_STEEL);
        } else {
            InterceptionModifier tacklezoneModifier = this.getTacklezoneModifier(pGame, pPlayer);
            if (tacklezoneModifier != null) {
                interceptionModifiers.add(tacklezoneModifier);
            }
        }
        InterceptionModifier disturbingPresenceModifier = this.getDisturbingPresenceModifier(pGame, pPlayer);
        if (disturbingPresenceModifier != null) {
            interceptionModifiers.add(disturbingPresenceModifier);
        }
        if (UtilCards.hasCard(pGame, pGame.getThrower(), Card.FAWNDOUGHS_HEADBAND)) {
            interceptionModifiers.add(InterceptionModifier.FAWNDOUGHS_HEADBAND);
        }
        if (UtilCards.hasCard(pGame, pPlayer, Card.MAGIC_GLOVES_OF_JARK_LONGARM)) {
            interceptionModifiers.add(InterceptionModifier.MAGIC_GLOVES_OF_JARK_LONGARM);
        }
        return interceptionModifiers;
    }

    public InterceptionModifier[] toArray(Set<InterceptionModifier> pInterceptionModifierSet) {
        if (pInterceptionModifierSet != null) {
            InterceptionModifier[] interceptionModifierArray = pInterceptionModifierSet.toArray(new InterceptionModifier[pInterceptionModifierSet.size()]);
            Arrays.sort(interceptionModifierArray, new Comparator<InterceptionModifier>(){

                @Override
                public int compare(InterceptionModifier pO1, InterceptionModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
                }
            });
            return interceptionModifierArray;
        }
        return new InterceptionModifier[0];
    }

    private InterceptionModifier getTacklezoneModifier(Game pGame, Player pPlayer) {
        int tacklezones = UtilPlayer.findTacklezones(pGame, pPlayer);
        for (InterceptionModifier modifier : InterceptionModifier.values()) {
            if (!modifier.isTacklezoneModifier() || modifier.getModifier() != tacklezones) continue;
            return modifier;
        }
        return null;
    }

    private InterceptionModifier getDisturbingPresenceModifier(Game pGame, Player pPlayer) {
        int disturbingPresences = UtilDisturbingPresence.findOpposingDisturbingPresences(pGame, pPlayer);
        for (InterceptionModifier modifier : InterceptionModifier.values()) {
            if (!modifier.isDisturbingPresenceModifier() || modifier.getModifier() != disturbingPresences) continue;
            return modifier;
        }
        return null;
    }

}

