/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.GoForItModifier;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.UtilCards;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class GoForItModifierFactory
implements IRollModifierFactory {
    @Override
    public GoForItModifier forName(String pName) {
        for (GoForItModifier modifier : GoForItModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    @Override
    public GoForItModifier forId(int pId) {
        for (GoForItModifier modifier : GoForItModifier.values()) {
            if (modifier.getId() != pId) continue;
            return modifier;
        }
        return null;
    }

    public Set<GoForItModifier> findGoForItModifiers(Game pGame) {
        HashSet<GoForItModifier> goForItModifiers = new HashSet<GoForItModifier>();
        if (Weather.BLIZZARD == pGame.getFieldModel().getWeather()) {
            goForItModifiers.add(GoForItModifier.BLIZZARD);
        }
        if (UtilCards.isCardActive(pGame, Card.GREASED_SHOES)) {
            goForItModifiers.add(GoForItModifier.GREASED_SHOES);
        }
        return goForItModifiers;
    }

    public GoForItModifier[] toArray(Set<GoForItModifier> pGoForItModifierSet) {
        if (pGoForItModifierSet != null) {
            GoForItModifier[] goForItModifierArray = pGoForItModifierSet.toArray(new GoForItModifier[pGoForItModifierSet.size()]);
            Arrays.sort(goForItModifierArray, new Comparator<GoForItModifier>(){

                @Override
                public int compare(GoForItModifier pO1, GoForItModifier pO2) {
                    return pO1.getId() - pO2.getId();
                }
            });
            return goForItModifierArray;
        }
        return new GoForItModifier[0];
    }

}

