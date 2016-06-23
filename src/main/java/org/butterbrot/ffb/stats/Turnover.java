package org.butterbrot.ffb.stats;

import refactored.com.balancedbytes.games.ffb.report.ReportId;

public class Turnover {
    private ReportId action;
    private int minRollOrDiceCount;
    private boolean wasReRolled;
    private boolean reRolledWithTeamReroll;

    public Turnover(ReportId action, int minRollOrDiceCount, boolean wasReRolled, boolean reRolledWithTeamReroll) {
        this.action = action;
        this.minRollOrDiceCount = minRollOrDiceCount;
        this.wasReRolled = wasReRolled;
        this.reRolledWithTeamReroll = reRolledWithTeamReroll;
    }
}
