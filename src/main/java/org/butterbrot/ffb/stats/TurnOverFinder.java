package org.butterbrot.ffb.stats;

import refactored.com.balancedbytes.games.ffb.model.Player;
import refactored.com.balancedbytes.games.ffb.model.Team;
import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TurnOverFinder {

    private String activePlayer;

    private Deque<IReport> reports = new ArrayDeque<>();

    private String homeTeam;
    private Set<String> homePlayers = new HashSet<>();

    public void addHomePlayers(Team homeTeam) {
        this.homeTeam = homeTeam.getId();
        for (Player player: homeTeam.getPlayers()) {
            homePlayers.add(player.getId());
        }
    }

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

    public Optional<TurnOver> findTurnover() {


        return Optional.empty();
    }
}
