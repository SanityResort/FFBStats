/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.RightStuffModifier;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class RightStuffModifierFactory
implements IRollModifierFactory {
    @Override
    public RightStuffModifier forName(String pName) {
        for (RightStuffModifier modifier : RightStuffModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<RightStuffModifier> findRightStuffModifiers(Game pGame, Player pPlayer) {
        HashSet<RightStuffModifier> rightStuffModifiers = new HashSet<RightStuffModifier>();
        RightStuffModifier tacklezoneModifier = this.getTacklezoneModifier(pGame, pPlayer);
        if (tacklezoneModifier != null) {
            rightStuffModifiers.add(tacklezoneModifier);
        }
        return rightStuffModifiers;
    }

    public RightStuffModifier[] toArray(Set<RightStuffModifier> pRightStuffModifierSet) {
        if (pRightStuffModifierSet != null) {
            RightStuffModifier[] rightStuffModifierArray = pRightStuffModifierSet.toArray(new RightStuffModifier[pRightStuffModifierSet.size()]);
            Arrays.sort(rightStuffModifierArray, new Comparator<RightStuffModifier>(){

                @Override
                public int compare(RightStuffModifier pO1, RightStuffModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
                }
            });
            return rightStuffModifierArray;
        }
        return new RightStuffModifier[0];
    }

    private RightStuffModifier getTacklezoneModifier(Game pGame, Player pPlayer) {
        int tacklezones = UtilPlayer.findTacklezones(pGame, pPlayer);
        for (RightStuffModifier modifier : RightStuffModifier.values()) {
            if (!modifier.isTacklezoneModifier() || modifier.getModifier() != tacklezones) continue;
            return modifier;
        }
        return null;
    }

}

