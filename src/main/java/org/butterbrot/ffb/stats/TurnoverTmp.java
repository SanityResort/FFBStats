package org.butterbrot.ffb.stats;

import refactored.com.balancedbytes.games.ffb.report.ReportId;

public class TurnoverTmp {
    private ReportId action;
    private int minRollOrDiceCount;
    private boolean wasReRolled;
    private boolean reRolledWithTeamReroll;
    private String activePlayer;

    public TurnoverTmp(ReportId action, int minRollOrDiceCount, boolean wasReRolled, boolean reRolledWithTeamReroll, String activePlayer) {
        this.action = action;
        this.minRollOrDiceCount = minRollOrDiceCount;
        this.wasReRolled = wasReRolled;
        this.reRolledWithTeamReroll = reRolledWithTeamReroll;
        this.activePlayer = activePlayer;
    }

    public ReportId getAction() {
        return action;
    }

    public int getMinRollOrDiceCount() {
        return minRollOrDiceCount;
    }

    public boolean isWasReRolled() {
        return wasReRolled;
    }

    public boolean isReRolledWithTeamReroll() {
        return reRolledWithTeamReroll;
    }

    public String getActivePlayer() {
        return activePlayer;
    }
}
