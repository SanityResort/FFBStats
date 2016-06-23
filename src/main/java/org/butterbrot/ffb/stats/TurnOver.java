package org.butterbrot.ffb.stats;

import refactored.com.balancedbytes.games.ffb.report.ReportId;

public class TurnOver {
    private ReportId action;
    private int minRollOrDiceCount;
    private boolean wasReRolled;
    private boolean wasReRolledWithTeamReRoll;
    private String activePlayer;

    public TurnOver(ReportId action, int minRollOrDiceCount, boolean wasReRolled, boolean wasReRolledWithTeamReRoll, String activePlayer) {
        this.action = action;
        this.minRollOrDiceCount = minRollOrDiceCount;
        this.wasReRolled = wasReRolled;
        this.wasReRolledWithTeamReRoll = wasReRolledWithTeamReRoll;
        this.activePlayer = activePlayer;
    }

    public ReportId getAction() {
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
