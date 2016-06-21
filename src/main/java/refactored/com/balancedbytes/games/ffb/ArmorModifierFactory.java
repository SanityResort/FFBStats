/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

public class ArmorModifierFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public ArmorModifier forName(String pName) {
        for (ArmorModifier modifier : ArmorModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    @Override
    public ArmorModifier forId(int pId) {
        if (pId > 0) {
            for (ArmorModifier modifier : ArmorModifier.values()) {
                if (modifier.getId() != pId) continue;
                return modifier;
            }
        }
        return null;
    }

    public ArmorModifier getFoulAssist(int pModifier) {
        for (ArmorModifier modifier : ArmorModifier.values()) {
            if (!modifier.isFoulAssistModifier() || modifier.getModifier() != pModifier) continue;
            return modifier;
        }
        return null;
    }

    public ArmorModifier[] toArray(Set<ArmorModifier> pArmorModifiers) {
        if (pArmorModifiers != null) {
            ArmorModifier[] modifierArray = pArmorModifiers.toArray(new ArmorModifier[pArmorModifiers.size()]);
            Arrays.sort(modifierArray, new Comparator<ArmorModifier>(){

                @Override
                public int compare(ArmorModifier pO1, ArmorModifier pO2) {
                    return pO1.getId() - pO2.getId();
                }
            });
            return modifierArray;
        }
        return new ArmorModifier[0];
    }

}

