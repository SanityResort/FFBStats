package org.butterbrot.ffb.stats.model;

import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import org.butterbrot.ffb.stats.model.Data;

public class TurnOver implements Data {
    private String action;
    private int minRollOrDiceCount;
    private boolean wasReRolled;
    private boolean wasReRolledWithTeamReRoll;
    private transient String activePlayer;

    public TurnOver(String action, int minRollOrDiceCount, boolean wasReRolled, boolean wasReRolledWithTeamReRoll, String activePlayer) {
        this.action = action;
        this.minRollOrDiceCount = minRollOrDiceCount;
        this.wasReRolled = wasReRolled;
        this.wasReRolledWithTeamReRoll = wasReRolledWithTeamReRoll;
        this.activePlayer = activePlayer;
    }

    public TurnOver(String action, int minRollOrDiceCount, ReportReRoll reportReRoll, String activePlayer) {
        this.action = action;
        this.minRollOrDiceCount = minRollOrDiceCount;
        this.wasReRolled = reportReRoll != null;
        this.wasReRolledWithTeamReRoll = reportReRoll != null &&
                (reportReRoll.getReRollSource() == ReRollSource.LEADER || reportReRoll.getReRollSource() == ReRollSource.TEAM_RE_ROLL);
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
