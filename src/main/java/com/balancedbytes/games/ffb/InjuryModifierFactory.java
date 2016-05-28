/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.InjuryAttribute;
import com.balancedbytes.games.ffb.InjuryModifier;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.model.Player;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

public class InjuryModifierFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public InjuryModifier forName(String pName) {
        for (InjuryModifier modifier : InjuryModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    @Override
    public InjuryModifier forId(int pId) {
        if (pId > 0) {
            for (InjuryModifier modifier : InjuryModifier.values()) {
                if (modifier.getId() != pId) continue;
                return modifier;
            }
        }
        return null;
    }

    public InjuryModifier[] toArray(Set<InjuryModifier> pInjuryModifiers) {
        if (pInjuryModifiers != null) {
            InjuryModifier[] modifierArray = pInjuryModifiers.toArray(new InjuryModifier[pInjuryModifiers.size()]);
            Arrays.sort(modifierArray, new Comparator<InjuryModifier>(){

                @Override
                public int compare(InjuryModifier pO1, InjuryModifier pO2) {
                    return pO1.getId() - pO2.getId();
                }
            });
            return modifierArray;
        }
        return new InjuryModifier[0];
    }

    public InjuryModifier getNigglingInjuryModifier(Player pPlayer) {
        if (pPlayer != null) {
            int nigglingInjuries = 0;
            for (SeriousInjury injury : pPlayer.getLastingInjuries()) {
                if (InjuryAttribute.NI != injury.getInjuryAttribute()) continue;
                ++nigglingInjuries;
            }
            for (InjuryModifier modifier : InjuryModifier.values()) {
                if (!modifier.isNigglingInjuryModifier() || modifier.getModifier() != nigglingInjuries) continue;
                return modifier;
            }
        }
        return null;
    }

}

