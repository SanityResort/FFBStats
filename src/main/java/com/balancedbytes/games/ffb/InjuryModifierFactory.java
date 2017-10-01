/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.InjuryAttribute;
import com.balancedbytes.games.ffb.InjuryModifier;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.model.Player;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

public class InjuryModifierFactory
implements INamedObjectFactory {
    @Override
    public InjuryModifier forName(String pName) {
        for (InjuryModifier modifier : InjuryModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public InjuryModifier[] toArray(Set<InjuryModifier> pInjuryModifiers) {
        if (pInjuryModifiers != null) {
            InjuryModifier[] modifierArray = pInjuryModifiers.toArray(new InjuryModifier[pInjuryModifiers.size()]);
            Arrays.sort(modifierArray, new Comparator<InjuryModifier>(){

                @Override
                public int compare(InjuryModifier pO1, InjuryModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
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
        }
        return null;
    }

}

