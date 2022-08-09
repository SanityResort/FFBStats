package org.butterbrot.ffb.stats.evaluation.turnover;

import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportSpecialEffectRoll;
import org.butterbrot.ffb.stats.model.TurnOver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class TurnOverFinder {

    private boolean homeTeamActive;

    private String activePlayer;

    private final Deque<IReport> reports = new ArrayDeque<>();

    private String homeTeam;
    private final Set<String> homePlayers = new HashSet<>();

    public boolean isHomeTeamActive() {
        return homeTeamActive;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Deque<IReport> getReports() {
        return reports;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Set<String> getHomePlayers() {
        return homePlayers;
    }

    public void addHomePlayers(Team homeTeam) {
        this.homeTeam = homeTeam.getId();
        for (Player<?> player : homeTeam.getPlayers()) {
            homePlayers.add(player.getId());
        }
    }

    public void setHomeTeamActive(boolean homeTeamActive) {
        this.homeTeamActive = homeTeamActive;
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
        IReport report = reports.pollFirst();
        while (report != null) {
            if (report instanceof ReportPlayerAction) {
                return findTurnOver((ReportPlayerAction) report);
            } else if (report instanceof ReportSpecialEffectRoll) {
                return findWizardTurnOver((ReportSpecialEffectRoll) report);
            }
            report = reports.pollFirst();
        }
        return Optional.empty();
    }

    protected abstract Optional<TurnOver> findTurnOver(ReportPlayerAction action);

    protected abstract Optional<TurnOver> findWizardTurnOver(ReportSpecialEffectRoll wizardReport);
}
