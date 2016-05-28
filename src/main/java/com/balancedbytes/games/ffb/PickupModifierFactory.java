/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.PickupModifier;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class PickupModifierFactory
implements IRollModifierFactory {
    @Override
    public PickupModifier forId(int pId) {
        for (PickupModifier modifier : PickupModifier.values()) {
            if (modifier.getId() != pId) continue;
            return modifier;
        }
        return null;
    }

    @Override
    public PickupModifier forName(String pName) {
        for (PickupModifier modifier : PickupModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<PickupModifier> findPickupModifiers(Game pGame) {
        HashSet<PickupModifier> pickupModifiers = new HashSet<PickupModifier>();
        Player player = pGame.getActingPlayer().getPlayer();
        if (player != null) {
            if (UtilCards.hasSkill(pGame, player, Skill.EXTRA_ARMS)) {
                pickupModifiers.add(PickupModifier.EXTRA_ARMS);
            }
            if (UtilCards.hasSkill(pGame, player, Skill.BIG_HAND)) {
                pickupModifiers.add(PickupModifier.BIG_HAND);
            } else {
                PickupModifier tacklezoneModifier;
                if (Weather.POURING_RAIN == pGame.getFieldModel().getWeather()) {
                    pickupModifiers.add(PickupModifier.POURING_RAIN);
                }
                if ((tacklezoneModifier = this.getTacklezoneModifier(pGame, player)) != null) {
                    pickupModifiers.add(tacklezoneModifier);
                }
            }
        }
        return pickupModifiers;
    }

    public PickupModifier[] toArray(Set<PickupModifier> pPickupModifierSet) {
        if (pPickupModifierSet != null) {
            PickupModifier[] pickupModifierArray = pPickupModifierSet.toArray(new PickupModifier[pPickupModifierSet.size()]);
            Arrays.sort(pickupModifierArray, new Comparator<PickupModifier>(){

                @Override
                public int compare(PickupModifier pO1, PickupModifier pO2) {
                    return pO1.getId() - pO2.getId();
                }
            });
            return pickupModifierArray;
        }
        return new PickupModifier[0];
    }

    private PickupModifier getTacklezoneModifier(Game pGame, Player pPlayer) {
        int tacklezones = UtilPlayer.findTacklezones(pGame, pPlayer);
        if (tacklezones > 0) {
            for (PickupModifier modifier : PickupModifier.values()) {
                if (!modifier.isTacklezoneModifier() || modifier.getModifier() != tacklezones) continue;
                return modifier;
            }
        }
        return null;
    }

}

