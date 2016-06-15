package org.butterbrot.ffb.stats.collections;

import java.util.HashMap;
import java.util.Map;

import refactored.com.balancedbytes.games.ffb.model.Player;
import refactored.com.balancedbytes.games.ffb.model.Team;

public class StatsCollection {

    private boolean finished = false;
    private TeamStatsCollection home;
    private TeamStatsCollection away;
    private int version = 1;
    private String replayId;

    private transient Map <String, TeamStatsCollection> teams = new HashMap<>();

    public void setReplayId(String replayId) {
        this.replayId = replayId;
    }

    public void setHomeTeam(Team team) {
        home = new TeamStatsCollection(team.getName(), team.getCoach(), team.getRace());
        teams.put(team.getId(), home);
        for (Player player : team.getPlayers()) {
            teams.put(player.getId(), home);
        }
    }

    public void setAwayTeam(Team team) {
        away = new TeamStatsCollection(team.getName(), team.getCoach(), team.getRace());
        teams.put(team.getId(), away);
        for (Player player : team.getPlayers()) {
            teams.put(player.getId(), away);
        }
    }

    public TeamStatsCollection getHome() {
        return home;
    }

    public TeamStatsCollection getAway() {
        return away;
    }

    public void addSuccessRoll(String playerOrTeam, boolean successful, int minimumRoll) {
        teams.get(playerOrTeam).addSuccessRoll(successful, minimumRoll);
    }

    public void addOpposingSuccessRoll(String playerOrTeam, boolean successful, int minimumRoll) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addSuccessRoll(successful, minimumRoll);
    }

    public void addSingleRoll(int roll, String playerOrTeam) {
        teams.get(playerOrTeam).addSingleRoll(roll);
    }

    public void addSingleOpposingRoll(int roll, String playerOrTeam) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addSingleRoll(roll);
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
        TeamStatsCollection blockerTeam = teams.get(blocker);
        blockerTeam.addBlockDice(rolls);
        TeamStatsCollection chooserTeam = teams.get(choosingTeam);
        int count = rolls.length * (chooserTeam == blockerTeam ? 1 : -1);
        if (count == -1) {
            throw new IllegalStateException(
                    "Dice count must not be -1, 1 dice blocks must be chosen by the blocker team, something went horribly wrong");
        }
        if (rerolled) {
            blockerTeam.addRerolledBlock(count);
            if (count < 0) {
                // work around a bug in report data. it seems that the choosing team does not get set properly for -2db
                // or -3db blocks that get rerolled. the block before the reroll is reported with the wrong choosing
                // team, so a -2db for team a got reported as a 2db for team b. it seems that the choosing team is only
                // set properly if an block die actually was chosen, otherwise the default value, i.e. the blocking
                // team, remains.
                // so we have to remove the wrong report and add a corrected report.

                blockerTeam.addBlock(count);
                getOpposition(blockerTeam).removeBlock(count * -1);
            }
        }
        blockerTeam.addBlock(count);
    }

    public void addBlockKnockDown(int diceCount, String knockedDownPlayer, String choosingTeam, String blocker) {
        TeamStatsCollection blockerTeam = teams.get(blocker);
        TeamStatsCollection chooserTeam = teams.get(choosingTeam);
        int count = diceCount * (chooserTeam == blockerTeam ? 1 : -1);
        if (count == -1) {
            throw new IllegalStateException(
                    "Dice count must not be -1, 1 dice blocks must be chosen by the blocker team, something went horribly wrong");
        }
        if (knockedDownPlayer.equals(blocker)) {
            blockerTeam.addFailedBlock(count);
        } else {
            blockerTeam.addSuccessfulBlock(count);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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
