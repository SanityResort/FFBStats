/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.DodgeModifier;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class DodgeModifierFactory
implements IRollModifierFactory {
    @Override
    public DodgeModifier forName(String pName) {
        for (DodgeModifier modifier : DodgeModifier.values()) {
            if (!modifier.getName().equalsIgnoreCase(pName)) continue;
            return modifier;
        }
        return null;
    }

    public Set<DodgeModifier> findDodgeModifiers(Game pGame, FieldCoordinate pCoordinateFrom, FieldCoordinate pCoordinateTo, int pTacklezoneModifier) {
        DodgeModifier tacklezoneModifier;
        DodgeModifier prehensileTailModifier;
        HashSet<DodgeModifier> dodgeModifiers = new HashSet<DodgeModifier>();
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        if (UtilCards.hasSkill(pGame, actingPlayer, Skill.TWO_HEADS)) {
            dodgeModifiers.add(DodgeModifier.TWO_HEADS);
        }
        if (UtilCards.hasSkill(pGame, actingPlayer, Skill.TITCHY)) {
            dodgeModifiers.add(DodgeModifier.TITCHY);
        }
        if ((prehensileTailModifier = this.findPrehensileTailModifier(pGame, pCoordinateFrom)) != null) {
            dodgeModifiers.add(prehensileTailModifier);
        }
        if ((tacklezoneModifier = this.findTacklezoneModifier(pGame, pCoordinateTo, pTacklezoneModifier)) != null) {
            if (UtilCards.hasSkill(pGame, actingPlayer, Skill.STUNTY) && !UtilCards.hasSkill(pGame, actingPlayer, Skill.SECRET_WEAPON)) {
                dodgeModifiers.add(DodgeModifier.STUNTY);
            } else {
                dodgeModifiers.add(tacklezoneModifier);
            }
        }
        if (UtilCards.hasUnusedSkill(pGame, actingPlayer, Skill.BREAK_TACKLE)) {
            dodgeModifiers.add(DodgeModifier.BREAK_TACKLE);
        }
        return dodgeModifiers;
    }

    public DodgeModifier[] toArray(Set<DodgeModifier> pDodgeModifierSet) {
        if (pDodgeModifierSet != null) {
            DodgeModifier[] dodgeModifierArray = pDodgeModifierSet.toArray(new DodgeModifier[pDodgeModifierSet.size()]);
            Arrays.sort(dodgeModifierArray, new Comparator<DodgeModifier>(){

                @Override
                public int compare(DodgeModifier pO1, DodgeModifier pO2) {
                    return pO1.getName().compareTo(pO2.getName());
                }
            });
            return dodgeModifierArray;
        }
        return new DodgeModifier[0];
    }

    private DodgeModifier findTacklezoneModifier(Game pGame, FieldCoordinate pCoordinateTo, int pModifier) {
        Player[] adjacentPlayers;
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, actingPlayer.getPlayer());
        int tacklezones = pModifier;
        for (Player player : adjacentPlayers = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, otherTeam, pCoordinateTo, false)) {
            if (UtilCards.hasSkill(pGame, player, Skill.TITCHY)) continue;
            ++tacklezones;
        }
        return null;
    }

    private DodgeModifier findPrehensileTailModifier(Game pGame, FieldCoordinate pCoordinateFrom) {
        Player[] opponents;
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        Team otherTeam = UtilPlayer.findOtherTeam(pGame, actingPlayer.getPlayer());
        int nrOfPrehensileTails = 0;
        for (Player opponent : opponents = UtilPlayer.findAdjacentPlayersWithTacklezones(pGame, otherTeam, pCoordinateFrom, true)) {
            if (!UtilCards.hasSkill(pGame, opponent, Skill.PREHENSILE_TAIL)) continue;
            ++nrOfPrehensileTails;
        }
        if (nrOfPrehensileTails > 0) {
        }
        return null;
    }

}

