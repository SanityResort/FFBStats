package org.butterbrot.ffb.stats;

import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class TurnoverFinderTmp {

    private String activePlayer;

    private Deque<IReport> reports = new ArrayDeque<>();

    public void add(IReport report) {
        reports.addLast(report);
    }
    public void add(ReportPlayerAction report) {
        reports.addLast(report);
        activePlayer = report.getActingPlayerId();
    }

    public void reset() {
        reports.clear();
        activePlayer = null;
    }

    public Optional<TurnoverTmp> findTurnover() {


        return Optional.empty();
    }
}
