package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import com.balancedbytes.games.ffb.report.ReportMasterChefRoll;
import com.balancedbytes.games.ffb.report.ReportPilingOn;
import org.butterbrot.ffb.stats.adapter.ReportPoInjury;
import org.butterbrot.ffb.stats.model.Turn;

import java.util.ArrayDeque;
import java.util.Deque;

public class StatsState {
    private int fameHome = 0;
    private int fameAway = 0;
    private boolean isHomePlaying = false;
    private TurnMode turnMode = null;
    private int turnNumber = 0;
    private String activePlayer = null;
    private ReportBlockRoll currentBlockRoll = null;
    private boolean lastReportWasBlockRoll = false;
    private boolean blockRerolled = false;
    private boolean startSecondHalf = false;
    private boolean startOvertime = false;
    private ReportPilingOn poReport = null;
    private boolean isActionTurn = false;
    private boolean ballScatters = false;
    private Deque<ReportPoInjury> injuries = new ArrayDeque<>();
    private Turn lastTurn;
    private ReportMasterChefRoll chefRoll;

    public ReportMasterChefRoll getChefRoll() {
        return chefRoll;
    }

    public void setChefRoll(ReportMasterChefRoll chefRoll) {
        this.chefRoll = chefRoll;
    }

    public int getFameHome() {
        return fameHome;
    }

    public void setFameHome(int fameHome) {
        this.fameHome = fameHome;
    }

    public int getFameAway() {
        return fameAway;
    }

    public void setFameAway(int fameAway) {
        this.fameAway = fameAway;
    }

    public boolean isHomePlaying() {
        return isHomePlaying;
    }

    public void setHomePlaying(boolean homePlaying) {
        isHomePlaying = homePlaying;
    }

    public TurnMode getTurnMode() {
        return turnMode;
    }

    public void setTurnMode(TurnMode turnMode) {
        this.turnMode = turnMode;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ReportBlockRoll getCurrentBlockRoll() {
        return currentBlockRoll;
    }

    public void setCurrentBlockRoll(ReportBlockRoll currentBlockRoll) {
        this.currentBlockRoll = currentBlockRoll;
    }

    public boolean isLastReportWasBlockRoll() {
        return lastReportWasBlockRoll;
    }

    public void setLastReportWasBlockRoll(boolean lastReportWasBlockRoll) {
        this.lastReportWasBlockRoll = lastReportWasBlockRoll;
    }

    public boolean isBlockRerolled() {
        return blockRerolled;
    }

    public void setBlockRerolled(boolean blockRerolled) {
        this.blockRerolled = blockRerolled;
    }

    public boolean isStartSecondHalf() {
        return startSecondHalf;
    }

    public void setStartSecondHalf(boolean startSecondHalf) {
        this.startSecondHalf = startSecondHalf;
    }

    public boolean isStartOvertime() {
        return startOvertime;
    }

    public void setStartOvertime(boolean startOvertime) {
        this.startOvertime = startOvertime;
    }

    public ReportPilingOn getPoReport() {
        return poReport;
    }

    public void setPoReport(ReportPilingOn poReport) {
        this.poReport = poReport;
    }

    public boolean isActionTurn() {
        return isActionTurn;
    }

    public void setActionTurn(boolean actionTurn) {
        isActionTurn = actionTurn;
    }

    public boolean isBallScatters() {
        return ballScatters;
    }

    public void setBallScatters(boolean ballScatters) {
        this.ballScatters = ballScatters;
    }

    public Deque<ReportPoInjury> getInjuries() {
        return injuries;
    }

    public void setInjuries(Deque<ReportPoInjury> injuries) {
        this.injuries = injuries;
    }

    public Turn getLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(Turn lastTurn) {
        this.lastTurn = lastTurn;
    }

    public boolean isNewTurn() {
        return lastTurn == null ||
                (lastTurn.isHomeActive() != this.isHomePlaying()) ||
                (lastTurn.getNumber() != this.getTurnNumber()) ||
                (!lastTurn.getTurnMode().equals(this.getTurnMode().getName()));
    }
}
