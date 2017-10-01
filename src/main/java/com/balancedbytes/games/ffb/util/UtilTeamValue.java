/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.model.Team;

public class UtilTeamValue {
    public static int findTeamValue(Team pTeam) {
        Roster roster;
        int teamValue = 0;
        if (pTeam != null && (roster = pTeam.getRoster()) != null) {
            teamValue += pTeam.getReRolls() * roster.getReRollCost();
            teamValue += pTeam.getFanFactor() * 10000;
            teamValue += pTeam.getAssistantCoaches() * 10000;
            teamValue += pTeam.getCheerleaders() * 10000;
            teamValue += pTeam.getApothecaries() * 50000;
            for (Player player : pTeam.getPlayers()) {
                if (player.getRecoveringInjury() != null) continue;
                teamValue += UtilTeamValue.findPlayerValue(player);
            }
        }
        return teamValue;
    }

    private static int findPlayerValue(Player pPlayer) {
        RosterPosition position;
        int playerValue = 0;
        if (pPlayer != null && (position = pPlayer.getPosition()) != null) {
            playerValue += position.getCost();
            block5 : for (Skill skill : pPlayer.getSkills()) {
                if (position.hasSkill(skill) || skill == Skill.LONER) continue;
                switch (skill) {
                    case AGILITY_INCREASE: {
                        playerValue += 40000;
                        continue block5;
                    }
                    case STRENGTH_INCREASE: {
                        playerValue += 50000;
                        continue block5;
                    }
                    case MOVEMENT_INCREASE: 
                    case ARMOUR_INCREASE: {
                        playerValue += 30000;
                        continue block5;
                    }
                    default: {
                        if (position.isDoubleCategory(skill.getCategory())) {
                            playerValue += 30000;
                            continue block5;
                        }
                        playerValue += 20000;
                    }
                }
            }
        }
        return playerValue;
    }

}

