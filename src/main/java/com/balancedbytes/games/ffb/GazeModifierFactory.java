/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class GazeModifierFactory
implements IRollModifierFactory {
    @Override
    public GazeModifier forName(String pName) {
        for (GazeModifier modifier : GazeModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<GazeModifier> findGazeModifiers(Game pGame) {
        GazeModifier tacklezoneModifier;
        HashSet<GazeModifier> gazeModifiers = new HashSet<GazeModifier>();
        Player player = pGame.getActingPlayer().getPlayer();
        if (player != null && (tacklezoneModifier = this.getTacklezoneModifier(pGame, player)) != null) {
            gazeModifiers.add(tacklezoneModifier);
        }
        return gazeModifiers;
    }

    public GazeModifier[] toArray(Set<GazeModifier> pGazeModifierSet) {
        if (pGazeModifierSet != null) {
            GazeModifier[] gazeModifierArray = pGazeModifierSet.toArray(new GazeModifier[pGazeModifierSet.size()]);
            Arrays.sort(gazeModifierArray, new Comparator<GazeModifier>(){

                @Override
                public int compare(GazeModifier pO1, GazeModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
                }
            });
            return gazeModifierArray;
        }
        return new GazeModifier[0];
    }

    private GazeModifier getTacklezoneModifier(Game pGame, Player pPlayer) {
        int tacklezones = UtilPlayer.findTacklezones(pGame, pPlayer);
        if (tacklezones > 1) {
            for (GazeModifier modifier : GazeModifier.values()) {
                if (!modifier.isTacklezoneModifier() || modifier.getModifier() != tacklezones - 1) continue;
                return modifier;
            }
        }
        return null;
    }

}

