package org.butterbrot.ffb.stats.model;

import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.report.ReportReRoll;

public class TurnOver implements Data {
    private final String action;
    private final int minRollOrDiceCount;
    private final boolean wasReRolled;
    private final boolean wasReRolledWithTeamReRoll;
    private final transient String activePlayer;

    public TurnOver(String action, int minRollOrDiceCount, ReportReRoll reportReRoll, String activePlayer) {
        this.action = action;
        this.minRollOrDiceCount = minRollOrDiceCount;
        this.wasReRolled = reportReRoll != null;
        this.wasReRolledWithTeamReRoll = reportReRoll != null &&
                (reportReRoll.getReRollSource() == ReRollSources.LEADER || reportReRoll.getReRollSource() == ReRollSources.TEAM_RE_ROLL);
        this.activePlayer = activePlayer;
    }

    public String getAction() {
        return action;
    }

    public int getMinRollOrDiceCount() {
        return minRollOrDiceCount;
    }

    public boolean isReRolled() {
        return wasReRolled;
    }

    public boolean isReRolledWithTeamReroll() {
        return wasReRolledWithTeamReRoll;
    }

    public String getActivePlayer() {
        return activePlayer;
    }
}
