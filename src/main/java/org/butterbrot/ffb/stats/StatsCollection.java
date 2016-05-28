package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.ArrayTool;

import java.util.HashMap;
import java.util.Map;

public class StatsCollection {

    private TeamStatsCollection home = new TeamStatsCollection();
    private TeamStatsCollection away = new TeamStatsCollection();

    private Map<String, TeamStatsCollection> teams = new HashMap<>();

    public void setHomeTeam(Team team) {
        System.out.println("Adding home team " + team.getId());
        teams.put(team.getId(), home);
        for (Player player : team.getPlayers()) {
            System.out.println("Adding home player " + player.getId());
            teams.put(player.getId(), home);
        }
    }

    public void setAwayTeam(Team team) {
        System.out.println("Adding away team " + team.getId());
        teams.put(team.getId(), away);
        for (Player player : team.getPlayers()) {
            System.out.println("Adding away player " + player.getId());
            teams.put(player.getId(), away);
        }
    }

    public TeamStatsCollection getHome() {
        return home;
    }

    public TeamStatsCollection getAway() {
        return away;
    }

    public void addSingleRoll(int roll, String playerOrTeam) {
        teams.get(playerOrTeam).addSingleRoll(roll);
    }

    public void addSingleOpposingRoll(int roll, String playerOrTeam) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addSingleRoll(roll);
    }

    public void addDoubleRoll(int[] rolls, String player) {
        teams.get(player).addDoubleRoll(rolls);
    }

    public void addDoubleOpposingRoll(int[] rolls, String playerOrTeam) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addDoubleRoll(rolls);
    }

    public void addArmourRoll(int[] rolls, String playerOrTeam) {
        getOpposition(teams.get(playerOrTeam)).addArmourRoll(rolls);
    }

    public void addInjuryRoll(int[] rolls, String playerOrTeam) {
        getOpposition(teams.get(playerOrTeam)).addInjuryRoll(rolls);
    }

    public void addBlockRolls(int[] rolls, String blocker, String choosingTeam, boolean rerolled) {
        System.out.println("Adding block: " + ArrayTool.join(rolls, ",") + " - blocker: " + blocker + " - choosing team: " + choosingTeam);
        TeamStatsCollection blockerTeam = teams.get(blocker);
        blockerTeam.addBlockDice(rolls);
        TeamStatsCollection chooserTeam = teams.get(choosingTeam);
        int count = rolls.length * (chooserTeam == blockerTeam ? 1 : -1);
        if (count == -1) {
            throw new IllegalStateException("Dice count must not be -1, 1 dice blocks must be chosen by the blocker team, something went horribly wrong");
        }
        if (rerolled) {
            blockerTeam.addRerolledBlock(count);
        } else {
            blockerTeam.addBlock(count);
        }
    }

    public void addBlockKnockDown(int diceCount, String knockedDownPlayer, String choosingTeam, String blocker) {
        System.out.println("Adding knockdown: " + diceCount + " - blocker: " + blocker + " - choosing team: " + choosingTeam + " - knocked down: " + knockedDownPlayer);
        TeamStatsCollection blockerTeam = teams.get(blocker);
        TeamStatsCollection chooserTeam = teams.get(choosingTeam);
        int count = diceCount * (chooserTeam == blockerTeam ? 1 : -1);
        if (count == -1) {
            throw new IllegalStateException("Dice count must not be -1, 1 dice blocks must be chosen by the blocker team, something went horribly wrong");
        }
        if (knockedDownPlayer.equals(blocker)) {
            blockerTeam.addFailedBlock(count);
        } else {
            blockerTeam.addSuccessfulBlock(count);
        }
    }

    private TeamStatsCollection getOpposition(TeamStatsCollection team) {
        if (team == home) {
            return away;
        }
        return home;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HOME TEAM\n");
        builder.append(home.toString());
        builder.append("AWAY TEAM\n");
        builder.append(away.toString());
        return builder.toString();
    }
}
