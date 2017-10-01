/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.CatchModifier;
import com.balancedbytes.games.ffb.CatchScatterThrowInMode;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilDisturbingPresence;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class CatchModifierFactory
implements IRollModifierFactory {
    @Override
    public CatchModifier forName(String pName) {
        for (CatchModifier modifier : CatchModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<CatchModifier> findCatchModifiers(Game pGame, Player pPlayer, CatchScatterThrowInMode pCatchMode) {
        HashSet<CatchModifier> catchModifiers = new HashSet<CatchModifier>();
        if (CatchScatterThrowInMode.CATCH_ACCURATE_PASS == pCatchMode || CatchScatterThrowInMode.CATCH_ACCURATE_BOMB == pCatchMode) {
            catchModifiers.add(CatchModifier.ACCURATE);
            if (UtilCards.hasSkill(pGame, pPlayer, Skill.DIVING_CATCH)) {
                catchModifiers.add(CatchModifier.DIVING_CATCH);
            }
        }
        if (CatchScatterThrowInMode.CATCH_HAND_OFF == pCatchMode) {
            catchModifiers.add(CatchModifier.HAND_OFF);
        }
        if (Weather.POURING_RAIN == pGame.getFieldModel().getWeather()) {
            catchModifiers.add(CatchModifier.POURING_RAIN);
        }
        if (UtilCards.hasSkill(pGame, pPlayer, Skill.EXTRA_ARMS)) {
            catchModifiers.add(CatchModifier.EXTRA_ARMS);
        }
        if (UtilCards.hasSkill(pGame, pPlayer, Skill.NERVES_OF_STEEL)) {
            catchModifiers.add(CatchModifier.NERVES_OF_STEEL);
        } else {
            CatchModifier tacklezoneModifier = this.getTacklezoneModifier(pGame, pPlayer);
            if (tacklezoneModifier != null) {
                catchModifiers.add(tacklezoneModifier);
            }
        }
        CatchModifier disturbingPresenceModifier = this.getDisturbingPresenceModifier(pGame, pPlayer);
        if (disturbingPresenceModifier != null) {
            catchModifiers.add(disturbingPresenceModifier);
        }
        return catchModifiers;
    }

    public CatchModifier[] toArray(Set<CatchModifier> pCatchModifierSet) {
        if (pCatchModifierSet != null) {
            CatchModifier[] catchModifierArray = pCatchModifierSet.toArray(new CatchModifier[pCatchModifierSet.size()]);
            Arrays.sort(catchModifierArray, new Comparator<CatchModifier>(){

                @Override
                public int compare(CatchModifier pO1, CatchModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
                }
            });
            return catchModifierArray;
        }
        return new CatchModifier[0];
    }

    private CatchModifier getTacklezoneModifier(Game pGame, Player pPlayer) {
        int tacklezones = UtilPlayer.findTacklezones(pGame, pPlayer);
        if (tacklezones > 0) {
            for (CatchModifier modifier : CatchModifier.values()) {
                if (!modifier.isTacklezoneModifier() || modifier.getModifier() != tacklezones) continue;
                return modifier;
            }
        }
        return null;
    }

    private CatchModifier getDisturbingPresenceModifier(Game pGame, Player pPlayer) {
        int disturbingPresences = UtilDisturbingPresence.findOpposingDisturbingPresences(pGame, pPlayer);
        if (disturbingPresences > 0) {
            for (CatchModifier modifier : CatchModifier.values()) {
                if (!modifier.isDisturbingPresenceModifier() || modifier.getModifier() != disturbingPresences) continue;
                return modifier;
            }
        }
        return null;
    }

}

