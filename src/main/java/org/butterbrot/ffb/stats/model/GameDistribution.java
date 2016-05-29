package org.butterbrot.ffb.stats.model;

import org.butterbrot.ffb.stats.collections.StatsCollection;

public class GameDistribution {
    private TeamDistribution home;
    private TeamDistribution away;

    public GameDistribution(StatsCollection collection) {
        home = new TeamDistribution(collection.getHome());
        away = new TeamDistribution(collection.getAway());
    }

    public TeamDistribution getHome() {
        return home;
    }

    public TeamDistribution getAway() {
        return away;
    }
}
