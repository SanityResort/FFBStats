package org.butterbrot.ffb.stats;

public class TurnOver {
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
