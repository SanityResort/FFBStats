/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

public class UtilDisturbingPresence {
    public static int findOpposingDisturbingPresences(Game pGame, Player pPlayer) {
        int foulAppearances = 0;
        FieldModel fieldModel = pGame.getFieldModel();
        FieldCoordinate playerCoordinate = fieldModel.getPlayerCoordinate(pPlayer);
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, pPlayer);
        for (Player opposingPlayer : otherTeam.getPlayers()) {
            FieldCoordinate coordinate = fieldModel.getPlayerCoordinate(opposingPlayer);
            if (!UtilCards.hasSkill(pGame, opposingPlayer, Skill.DISTURBING_PRESENCE) || !FieldCoordinateBounds.FIELD.isInBounds(coordinate) || playerCoordinate.distanceInSteps(coordinate) > 3) continue;
            ++foulAppearances;
        }
        return foulAppearances;
    }
}

