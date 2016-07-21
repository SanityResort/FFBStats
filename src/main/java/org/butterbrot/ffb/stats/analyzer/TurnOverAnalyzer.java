package org.butterbrot.ffb.stats.analyzer;

import org.butterbrot.ffb.stats.TurnOver;
import refactored.com.balancedbytes.games.ffb.report.IReport;

import java.util.Deque;
import java.util.Optional;

public abstract class TurnOverAnalyzer {

    public abstract Optional<TurnOver> analyze(Deque<IReport> reports);

}
