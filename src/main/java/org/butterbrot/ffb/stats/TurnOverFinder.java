package org.butterbrot.ffb.stats;

import org.butterbrot.ffb.stats.analyzer.TurnOverAnalyzer;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.model.Player;
import refactored.com.balancedbytes.games.ffb.model.Team;
import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportBlockRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportScatterBall;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TurnOverFinder {

    private boolean homeTeamActive;

    private String activePlayer;

    private Deque<IReport> reports = new ArrayDeque<>();

    private String homeTeam;
    private Set<String> homePlayers = new HashSet<>();

    private Map<String, TurnOverAnalyzer> analyzers = new HashMap<>();

    public void addHomePlayers(Team homeTeam) {
        this.homeTeam = homeTeam.getId();
        for (Player player : homeTeam.getPlayers()) {
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
                return findWizardTurnOver();
            }
            report = reports.pollFirst();
        }
        return Optional.empty();
    }

    private Optional<TurnOver> findTurnOver(ReportPlayerAction action) {
        switch (action.getPlayerAction()) {
            case THROW_TEAM_MATE:
            case THROW_TEAM_MATE_MOVE:
                return findTtmTurnOver();
        }

        ReportReRoll reportReRoll = null;
        ReportSkillRoll reportSkillRoll = null;
        ReportBlockRoll reportBlockRoll = null;
        boolean blockingPlayerWasInjured = false;
        boolean ballScattered = false;
        for (IReport report: reports) {
            if (report instanceof ReportReRoll) {
                reportReRoll = (ReportReRoll) report;
            } else if(report instanceof ReportSkillRoll ) {
                reportBlockRoll = null;
                if (((ReportSkillRoll)report).isSuccessful()) {
                    if (!ballScattered) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                    }
                } else {
                    reportSkillRoll = (ReportSkillRoll) report;
                }
            } else if (report instanceof ReportInjury) {
                ReportInjury injury = (ReportInjury) report;
                if (injury.getDefenderId().equals(activePlayer)) {
                    if (reportBlockRoll != null) {
                        blockingPlayerWasInjured = true;
                    } else if (reportSkillRoll != null && ReportId.CHAINSAW_ROLL == reportSkillRoll.getId() && !injury.isArmorBroken()) {
                        reportSkillRoll = null;
                        reportReRoll = null;
                    }
                    break;
                }
            } else if (report instanceof ReportBlockRoll) {
                reportBlockRoll = (ReportBlockRoll) report;
                reportSkillRoll = null;
            } else if (report instanceof ReportScatterBall) {
                ballScattered = true;
            }
        }

        if (reportSkillRoll != null) {
            return Optional.of(new TurnOver(reportSkillRoll.getId().getTurnOverDesc(), reportSkillRoll.getMinimumRoll(), reportReRoll, action.getActingPlayerId()));
        }

        if (reportBlockRoll != null && blockingPlayerWasInjured) {
            int blockDiceCount = reportBlockRoll.getBlockRoll().length;
            boolean actingTeamWasChoosing = homePlayers.contains(activePlayer) == (reportBlockRoll.getChoosingTeamId().equals(homeTeam));
            if (!actingTeamWasChoosing) {
                blockDiceCount *= -1;
            }
            return Optional.of(new TurnOver(reportBlockRoll.getId().getTurnOverDesc(), blockDiceCount, reportReRoll, activePlayer));
        }

        return Optional.empty();
    }

    private Optional<TurnOver> findTtmTurnOver() {

        return Optional.empty();
    }

    private Optional<TurnOver> findWizardTurnOver() {
        return Optional.empty();
    }

/*
    public Optional<TurnOver> findTurnover() {
        Optional<String> keyOpt = getKey();
        if (keyOpt.isPresent()) {
            String key = keyOpt.get();
            if (analyzers.containsKey(key)) {
                return (analyzers.get(key).analyze(reports));
            }
        }
        return Optional.empty();
    }


    private Optional<String> getKey() {
        IReport report = reports.pollFirst();
        if (report instanceof ReportPlayerAction) {
            return Optional.ofNullable(((ReportPlayerAction) report).getPlayerAction().getName());
        } else if (report instanceof ReportSpecialEffectRoll) {
            return Optional.ofNullable(report.getId().getName());
        }
        return Optional.empty();
    }*/
}
