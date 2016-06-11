package org.butterbrot.ffb.stats.model;

import org.butterbrot.ffb.stats.collections.StatsCollection;

public class GameDistribution {
    private String replayId;
    private boolean finished = false;
    private TeamDistribution home;
    private TeamDistribution away;

    public GameDistribution(StatsCollection collection, String replayId) {
        this.replayId = replayId;
        this.finished = collection.isFinished();
        home = new TeamDistribution(collection.getHome());
        away = new TeamDistribution(collection.getAway());
    }

    public boolean isFinished() {
        return finished;
    }

    public TeamDistribution getHome() {
        return home;
    }

    public TeamDistribution getAway() {
        return away;
    }
}
