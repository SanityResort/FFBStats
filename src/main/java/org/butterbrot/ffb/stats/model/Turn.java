package org.butterbrot.ffb.stats.model;

public final class Turn implements Data {
    private final boolean isHomeActive;
    private final String turnMode;
    private final int number;

    private final transient TeamStatsCollection globalHome;

    private final TeamStatsCollection rollsHome = new TeamStatsCollection();
    private final TeamStatsCollection rollsAway = new TeamStatsCollection();

    public Turn(boolean isHomeActive, String turnMode, int number, TeamStatsCollection globalHome) {
        this.isHomeActive = isHomeActive;
        this.turnMode = turnMode;
        this.number = number;
        this.globalHome = globalHome;
    }

    public TeamStatsCollection getTurnTeam(TeamStatsCollection globalTeam) {
        if (globalTeam == globalHome) {
            return rollsHome;
        }
        return rollsAway;
    }

    public boolean isHomeActive() {
        return isHomeActive;
    }

    public String getTurnMode() {
        return turnMode;
    }

    public int getNumber() {
        return number;
    }
}
