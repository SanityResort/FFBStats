/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassingDistance;
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

public class PassModifierFactory
implements IRollModifierFactory {
    @Override
    public PassModifier forName(String pName) {
        for (PassModifier modifier : PassModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<PassModifier> findPassModifiers(Game pGame, Player pThrower, PassingDistance pPassingDistance, boolean pThrowTeamMate) {
        HashSet<PassModifier> passModifiers = new HashSet<PassModifier>();
        if (pThrower != null) {
            PassModifier disturbingPresenceModifier;
            if (pThrowTeamMate) {
                passModifiers.add(PassModifier.THROW_TEAM_MATE);
            }
            if (Weather.VERY_SUNNY == pGame.getFieldModel().getWeather()) {
                passModifiers.add(PassModifier.VERY_SUNNY);
            }
            if (Weather.BLIZZARD == pGame.getFieldModel().getWeather()) {
                passModifiers.add(PassModifier.BLIZZARD);
            }
            if (UtilCards.hasSkill(pGame, pThrower, Skill.NERVES_OF_STEEL)) {
                passModifiers.add(PassModifier.NERVES_OF_STEEL);
            } else {
                PassModifier tacklezoneModifier = this.getTacklezoneModifier(pGame, pThrower);
                if (tacklezoneModifier != null) {
                    passModifiers.add(tacklezoneModifier);
                }
            }
            if (UtilCards.hasSkill(pGame, pThrower, Skill.STRONG_ARM) && pPassingDistance != PassingDistance.QUICK_PASS) {
                passModifiers.add(PassModifier.STRONG_ARM);
            }
            if (UtilCards.hasSkill(pGame, pThrower, Skill.STUNTY)) {
                passModifiers.add(PassModifier.STUNTY);
            }
            if (UtilCards.hasSkill(pGame, pThrower, Skill.ACCURATE)) {
                passModifiers.add(PassModifier.ACCURATE);
            }
            if (UtilCards.hasCard(pGame, pThrower, Card.GROMSKULLS_EXPLODING_RUNES)) {
                passModifiers.add(PassModifier.GROMSKULLS_EXPLODING_RUNES);
            }
            if ((disturbingPresenceModifier = this.getDisturbingPresenceModifier(pGame, pThrower)) != null) {
                passModifiers.add(disturbingPresenceModifier);
            }
        }
        return passModifiers;
    }

    public PassModifier[] toArray(Set<PassModifier> pPassModifierSet) {
        if (pPassModifierSet != null) {
            PassModifier[] passModifierArray = pPassModifierSet.toArray(new PassModifier[pPassModifierSet.size()]);
            Arrays.sort(passModifierArray, new Comparator<PassModifier>(){

                @Override
                public int compare(PassModifier pO1, PassModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
                }
            });
            return passModifierArray;
        }
        return new PassModifier[0];
    }

    private PassModifier getTacklezoneModifier(Game pGame, Player pPlayer) {
        int tacklezones = UtilPlayer.findTacklezones(pGame, pPlayer);
        for (PassModifier modifier : PassModifier.values()) {
            if (!modifier.isTacklezoneModifier() || modifier.getModifier() != tacklezones) continue;
            return modifier;
        }
        return null;
    }

    private PassModifier getDisturbingPresenceModifier(Game pGame, Player pPlayer) {
        int disturbingPresences = UtilDisturbingPresence.findOpposingDisturbingPresences(pGame, pPlayer);
        for (PassModifier modifier : PassModifier.values()) {
            if (!modifier.isDisturbingPresenceModifier() || modifier.getModifier() != disturbingPresences) continue;
            return modifier;
        }
        return null;
    }

}

