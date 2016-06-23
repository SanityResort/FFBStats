package org.butterbrot.ffb.stats.collections;

import org.butterbrot.ffb.stats.TurnOver;
import refactored.com.balancedbytes.games.ffb.ArmorModifier;
import refactored.com.balancedbytes.games.ffb.KickoffResult;
import refactored.com.balancedbytes.games.ffb.TurnMode;
import refactored.com.balancedbytes.games.ffb.model.Player;
import refactored.com.balancedbytes.games.ffb.model.Team;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.get;

public class StatsCollection {

    private boolean finished = false;
    private TeamStatsCollection home;
    private TeamStatsCollection away;
    private int version = 3;
    private String replayId;
    private String weather;
    private List<Drive> firstHalf = new ArrayList<>();
    private List<Drive> secondHalf = new ArrayList<>();
    private List<Drive> overtime = new ArrayList<>();
    private transient List<Drive> currentHalf = firstHalf;
    private transient Map<String, Integer> armourValues = new HashMap<>();
    private transient Map<String, TeamStatsCollection> teams = new HashMap<>();

    public void setReplayId(String replayId) {
        this.replayId = replayId;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setHomeTeam(Team team) {
        home = new TeamStatsCollection(team.getName(), team.getCoach(), team.getRace());
        teams.put(team.getId(), home);
        for (Player player : team.getPlayers()) {
            teams.put(player.getId(), home);
            armourValues.put(player.getId(), player.getArmour());
        }
    }

    public void setAwayTeam(Team team) {
        away = new TeamStatsCollection(team.getName(), team.getCoach(), team.getRace());
        teams.put(team.getId(), away);
        for (Player player : team.getPlayers()) {
            teams.put(player.getId(), away);
            armourValues.put(player.getId(), player.getArmour());
        }
    }

    public TeamStatsCollection getHome() {
        return home;
    }

    public TeamStatsCollection getAway() {
        return away;
    }

    public void addSuccessRoll(String playerOrTeam, ReportId reportId, int minimumRoll) {
        teams.get(playerOrTeam).addSuccessRoll(reportId, minimumRoll);
        turnTeam(teams.get(playerOrTeam)).addSuccessRoll(reportId, minimumRoll);
    }

    public void removeSuccessRoll(String playerOrTeam, ReportId reportId, int minimumRoll) {
        teams.get(playerOrTeam).removeSuccessRoll(reportId, minimumRoll);
        turnTeam(teams.get(playerOrTeam)).removeSuccessRoll(reportId, minimumRoll);
    }

    public void addFailedRoll(String playerOrTeam, ReportId reportId, int minimumRoll) {
        teams.get(playerOrTeam).addFailedRoll(reportId, minimumRoll);
        turnTeam(teams.get(playerOrTeam)).addFailedRoll(reportId, minimumRoll);
    }

    public void addOpposingSuccessRoll(String playerOrTeam, ReportId reportId, int minimumRoll) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addSuccessRoll(reportId, minimumRoll);
        turnTeam(getOpposition(team)).addSuccessRoll(reportId, minimumRoll);
    }

    public void addSingleRoll(int roll, String playerOrTeam) {
        teams.get(playerOrTeam).addSingleRoll(roll);
        turnTeam(teams.get(playerOrTeam)).addSingleRoll(roll);
    }

    public void addSingleOpposingRoll(int roll, String playerOrTeam) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addSingleRoll(roll);
        turnTeam(getOpposition(team)).addSingleRoll(roll);
    }

    public void addDoubleOpposingRoll(int[] rolls, String playerOrTeam) {
        TeamStatsCollection team = teams.get(playerOrTeam);
        getOpposition(team).addDoubleRoll(rolls);
        turnTeam(getOpposition(team)).addDoubleRoll(rolls);
    }

    public void addArmourRoll(int[] rolls, String playerOrTeam) {
        getOpposition(teams.get(playerOrTeam)).addArmourRoll(rolls);
        turnTeam(getOpposition(teams.get(playerOrTeam))).addArmourRoll(rolls);
    }

    public void addInjuryRoll(int[] rolls, String playerOrTeam) {
        getOpposition(teams.get(playerOrTeam)).addInjuryRoll(rolls);
        turnTeam(getOpposition(teams.get(playerOrTeam))).addInjuryRoll(rolls);
    }

    public void addBlockRolls(int[] rolls, String blocker, String choosingTeam, boolean rerolled) {
        TeamStatsCollection blockerTeam = teams.get(blocker);
        TeamStatsCollection blockerTurnTeam = turnTeam(blockerTeam);
        blockerTeam.addBlockDice(rolls);
        blockerTurnTeam.addBlockDice(rolls);
        TeamStatsCollection chooserTeam = teams.get(choosingTeam);
        int count = rolls.length * (chooserTeam == blockerTeam ? 1 : -1);
        if (count == -1) {
            throw new IllegalStateException(
                    "Dice count must not be -1, 1 dice blocks must be chosen by the blocker team, something went horribly wrong");
        }
        if (rerolled) {
            blockerTeam.addRerolledBlock(count);
            blockerTurnTeam.addRerolledBlock(count);
            if (count < 0) {
                // work around a bug in report data. it seems that the choosing team does not get set properly for -2db
                // or -3db blocks that get rerolled. the block before the reroll is reported with the wrong choosing
                // team, so a -2db for team a got reported as a 2db for team b. it seems that the choosing team is only
                // set properly if an block die actually was chosen, otherwise the default value, i.e. the blocking
                // team, remains.
                // so we have to remove the wrong report and add a corrected report.

                blockerTeam.addBlock(count);
                getOpposition(blockerTeam).removeBlock(count * -1);
                blockerTurnTeam.addBlock(count);
                turnTeam(getOpposition(blockerTeam)).removeBlock(count * -1);
            }
        }
        blockerTeam.addBlock(count);
        blockerTurnTeam.addBlock(count);
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
            turnTeam(blockerTeam).addFailedBlock(count);
        } else {
            blockerTeam.addSuccessfulBlock(count);
            turnTeam(blockerTeam).addSuccessfulBlock(count);
        }
    }

    private TeamStatsCollection turnTeam(TeamStatsCollection globalTeam) {
        Drive drive = currentHalf.get(currentHalf.size() -1);
        List<Turn> turns = drive.getTurns();
        if (turns.isEmpty()) {
            return drive.getDriveTeam(globalTeam);
        }
        return turns.get(turns.size()-1).getTurnTeam(globalTeam);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void startSecondHalf() {
        currentHalf = secondHalf;
    }

    public void startOvertime() {
        currentHalf = overtime;
    }

    public void addDrive(KickoffResult kickoffResult) {
        currentHalf.add(new Drive(kickoffResult.getName(), home));
    }

    public void addKickOffRolls(int[] home, int[] away) {
        currentHalf.get(currentHalf.size() - 1).setKickOffRollsAway(away);
        currentHalf.get(currentHalf.size() - 1).setKickOffRollsHome(home);
    }

    public void addHeatRoll(int roll, String player) {
        if (teams.get(player).equals(home)) {
            currentHalf.get(currentHalf.size() - 1).addHeatRollAway(roll);
        } else {
            currentHalf.get(currentHalf.size() - 1).addHeatRollHome(roll);
        }
    }

    public void addKoRoll(int roll, String player) {
        if (teams.get(player).equals(home)) {
            currentHalf.get(currentHalf.size() - 1).addKoRollHome(roll);
        } else {
            currentHalf.get(currentHalf.size() - 1).addKoRollAway(roll);
        }
    }

    public void addTurn(boolean isHomeActive, TurnMode turnMode, int turnNumber) {
        currentHalf.get(currentHalf.size() - 1).addTurn(new Turn(isHomeActive, turnMode.getName(), turnNumber, home));
    }

    public void addArmourAndInjuryStats(Collection<ReportInjury> injuries) {
        for (ReportInjury injury: injuries) {
            TeamStatsCollection team = getOpposition(teams.get(injury.getDefenderId()));
            TeamStatsCollection turnTeam = turnTeam(team);
            int effectiveAV = armourValues.get(injury.getDefenderId());
            List<ArmorModifier> armorModifiers = injury.getArmorModifiers();
            if (armorModifiers.contains(ArmorModifier.CLAWS)) {
                effectiveAV = Math.min(7, effectiveAV);
            }
            boolean poUsedForArmour = injury.getPoReport() != null && !injury.getPoReport().isReRollInjury();
            boolean mbUsed = false;
            boolean dpUsed = false;
            for (ArmorModifier modifier: armorModifiers) {
                switch (modifier) {
                    case MIGHTY_BLOW:
                        mbUsed = true; break;
                    case DIRTY_PLAYER:
                        dpUsed = true;
                        break;
                    default:
                        effectiveAV-=modifier.getModifier();
                }
            }

            effectiveAV = Math.max(0,effectiveAV);
            team.addArmourBreak(effectiveAV,mbUsed,poUsedForArmour,dpUsed);
            turnTeam.addArmourBreak(effectiveAV,mbUsed,poUsedForArmour,dpUsed);

            if (injury.getInjury() != null) {
                InjuryState injuryState = InjuryState.fromValue(injury.getInjury().getId());
                if (injuryState != null) {
                    team.addCausedInjury(injury.getDefenderId(), injuryState);
                    turnTeam.addCausedInjury(injury.getDefenderId(), injuryState);
                }
            }
        }
    }

    public void addTurnOver(TurnOver turnOver) {
        TeamStatsCollection team = teams.get(turnOver.getActivePlayer());
        team.addTurnOver(turnOver);
        turnTeam(team).addTurnOver(turnOver);
    }

    private TeamStatsCollection getOpposition(TeamStatsCollection team) {
        if (team == home) {
            return away;
        }
        return home;
    }

    private static final class Drive {
        private final List<Turn> turns = new ArrayList<>();
        private final String kickOff;

        private int[] kickOffRollsHome;
        private int[] kickOffRollsAway;
        private List<Integer> koRollsHome = new ArrayList<>();
        private List<Integer> koRollsAway = new ArrayList<>();
        private List<Integer> heatRollsHome = new ArrayList<>();
        private List<Integer> heatRollsAway = new ArrayList<>();

        private TeamStatsCollection driveHome = new TeamStatsCollection();
        private TeamStatsCollection driveAway = new TeamStatsCollection();

        private final transient TeamStatsCollection globalHome;

        private Drive(String kickOff, TeamStatsCollection globalHome) {
            this.kickOff = kickOff;
            this.globalHome = globalHome;
        }

        public void setKickOffRollsHome(int[] kickOffRollsHome) {
            this.kickOffRollsHome = kickOffRollsHome;
        }

        public void setKickOffRollsAway(int[] kickOffRollsAway) {
            this.kickOffRollsAway = kickOffRollsAway;
        }

        void addTurn(Turn turn) {
            turns.add(turn);
        }

        void addKoRollHome(int roll) {
            koRollsHome.add(roll);
        }

        void addKoRollAway(int roll) {
            koRollsAway.add(roll);
        }

        void addHeatRollHome(int roll) {
            heatRollsHome.add(roll);
        }

        void addHeatRollAway(int roll) {
            heatRollsAway.add(roll);
        }

        public List<Turn> getTurns() {
            return turns;
        }
        public TeamStatsCollection getDriveTeam(TeamStatsCollection globalTeam) {
            if (globalTeam == globalHome) {
                return driveHome;
            }
            return driveAway;
        }
    }

    private static final class Turn {
        private final boolean isHomeActive;
        private final String turnMode;
        private final int number;

        private final transient TeamStatsCollection globalHome;

        private final TeamStatsCollection turnHome = new TeamStatsCollection();
        private final TeamStatsCollection turnAway = new TeamStatsCollection();

        public Turn(boolean isHomeActive, String turnMode, int number, TeamStatsCollection globalHome) {
            this.isHomeActive = isHomeActive;
            this.turnMode = turnMode;
            this.number = number;
            this.globalHome = globalHome;
        }

        public TeamStatsCollection getTurnTeam(TeamStatsCollection globalTeam) {
            if (globalTeam == globalHome) {
                return turnHome;
            }
            return turnAway;
        }
    }
}
