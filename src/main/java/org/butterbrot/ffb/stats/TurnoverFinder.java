package org.butterbrot.ffb.stats;

import refactored.com.balancedbytes.games.ffb.report.IReport;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class TurnoverFinder {
    private Deque<IReport> reports = new ArrayDeque<>();

    public void add(IReport report) {
        reports.addLast(report);
    }

    public void reset() {
        reports.clear();
    }

    public Optional<Turnover> findTurnover() {



        return Optional.empty();
    }
}
