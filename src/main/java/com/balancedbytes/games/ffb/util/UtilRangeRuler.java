/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassModifierFactory;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.UtilPassing;
import java.util.Set;

public class UtilRangeRuler {
    public static RangeRuler createRangeRuler(Game pGame, Player pThrower, FieldCoordinate pTargetCoordinate, boolean pThrowTeamMate) {
        PassingDistance passingDistance;
        FieldCoordinate throwerCoordinate;
        RangeRuler rangeRuler = null;
        if (pGame != null && pThrower != null && pTargetCoordinate != null && (passingDistance = UtilPassing.findPassingDistance(pGame, throwerCoordinate = pGame.getFieldModel().getPlayerCoordinate(pThrower), pTargetCoordinate, pThrowTeamMate)) != null) {
            int minimumRoll = 0;
            Set<PassModifier> passModifiers = new PassModifierFactory().findPassModifiers(pGame, pThrower, passingDistance, pThrowTeamMate);
            minimumRoll = pThrowTeamMate ? UtilRangeRuler.minimumRollThrowTeamMate(pThrower, passingDistance, passModifiers) : UtilRangeRuler.minimumRollPass(pThrower, passingDistance, passModifiers);
            rangeRuler = new RangeRuler(pThrower.getId(), pTargetCoordinate, minimumRoll, pThrowTeamMate);
        }
        return rangeRuler;
    }

    public static int minimumRollPass(Player pThrower, PassingDistance pPassingDistance, Set<PassModifier> pPassModifiers) {
        int modifierTotal = 0;
        for (PassModifier passModifier : pPassModifiers) {
            modifierTotal += passModifier.getModifier();
        }
        return Math.max(Math.max(2 - (pPassingDistance.getModifier() - modifierTotal), 2), 7 - Math.min(pThrower.getAgility(), 6) - pPassingDistance.getModifier() + modifierTotal);
    }

    public static int minimumRollThrowTeamMate(Player pThrower, PassingDistance pPassingDistance, Set<PassModifier> pPassModifiers) {
        int modifierTotal = 0;
        for (PassModifier passModifier : pPassModifiers) {
            modifierTotal += passModifier.getModifier();
        }
        return Math.max(2, 2 - pPassingDistance.getModifier() + modifierTotal);
    }
}

