/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

public class UtilBlock {
    public static int findNrOfBlockDice(Game pGame, Player pAttacker, int pAttackerStrength, Player pDefender, boolean pUsingMultiBlock) {
        int nrOfDice = 0;
        if (pAttacker != null && pDefender != null) {
            int defenderStrength;
            int blockStrengthDefender;
            nrOfDice = 1;
            int blockStrengthAttacker = UtilPlayer.findBlockStrength(pGame, pAttacker, pAttackerStrength, pDefender);
            ActingPlayer actingPlayer = pGame.getActingPlayer();
            if (pAttacker == actingPlayer.getPlayer() && (actingPlayer.getPlayerAction() == PlayerAction.BLITZ || actingPlayer.getPlayerAction() == PlayerAction.BLITZ_MOVE) && actingPlayer.hasMoved() && UtilCards.hasCard(pGame, pDefender, Card.INERTIA_DAMPER)) {
                --blockStrengthAttacker;
            }
            if (blockStrengthAttacker > (blockStrengthDefender = UtilPlayer.findBlockStrength(pGame, pDefender, defenderStrength = pUsingMultiBlock ? UtilCards.getPlayerStrength(pGame, pDefender) + 2 : UtilCards.getPlayerStrength(pGame, pDefender), pAttacker))) {
                nrOfDice = 2;
            }
            if (blockStrengthAttacker > 2 * blockStrengthDefender) {
                nrOfDice = 3;
            }
            if (blockStrengthAttacker < blockStrengthDefender) {
                nrOfDice = -2;
            }
            if (blockStrengthAttacker * 2 < blockStrengthDefender) {
                nrOfDice = -3;
            }
            if (UtilCards.hasSkill(pGame, pAttacker, Skill.BALL_AND_CHAIN) && pAttacker.getTeam() == pDefender.getTeam()) {
                nrOfDice = Math.abs(nrOfDice);
            }
        }
        return nrOfDice;
    }
}

