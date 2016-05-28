/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.GazeModifier;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.model.ActingPlayer;
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
    public GazeModifier forId(int pId) {
        for (GazeModifier modifier : GazeModifier.values()) {
            if (modifier.getId() != pId) continue;
            return modifier;
        }
        return null;
    }

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
                    return pO1.getId() - pO2.getId();
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

