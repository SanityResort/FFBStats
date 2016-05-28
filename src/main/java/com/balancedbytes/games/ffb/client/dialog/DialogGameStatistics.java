/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.Dialog;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TeamResult;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.InternalFrameEvent;

public class DialogGameStatistics
extends Dialog {
    private static final String _FONT_BOLD_OPEN = "<font face=\"Sans Serif\" size=\"-1\"><b>";
    private static final String _FONT_BOLD_CLOSE = "</b></font>";
    private static final String _FONT_LARGE_BOLD_OPEN = "<font face=\"Sans Serif\" size=\"+1\"><b>";
    private static final String _FONT_BLUE_BOLD_OPEN = "<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>";
    private static final String _FONT_LARGE_BLUE_BOLD_OPEN = "<font color=\"#0000ff\" face=\"Sans Serif\" size=\"+1\"><b>";
    private static final String _FONT_RED_BOLD_OPEN = "<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>";
    private static final String _FONT_LARGE_RED_BOLD_OPEN = "<font color=\"#ff0000\" face=\"Sans Serif\" size=\"+1\"><b>";
    private static final String _BACKGROUND_COLOR_SPP = "#e0e0e0";
    private static final String _BACKGROUND_COLOR_TOTAL_SPP = "#c0c0c0";
    private JTabbedPane fTabbedPane;

    public DialogGameStatistics(FantasyFootballClient pClient) {
        super(pClient, "Game Statistics", true);
        Game game = this.getClient().getGame();
        JScrollPane teamComparisonPane = new JScrollPane(this.createTeamComparisonEditorPane());
        JScrollPane teamHomePane = new JScrollPane(this.createTeamEditorPane(game.getTeamHome()));
        JScrollPane teamAwayPane = new JScrollPane(this.createTeamEditorPane(game.getTeamAway()));
        this.fTabbedPane = new JTabbedPane();
        this.fTabbedPane.addTab("Team Comparison", teamComparisonPane);
        this.fTabbedPane.addTab("<html><font color=\"#ff0000\">Details Home Team</font></html>", teamHomePane);
        this.fTabbedPane.addTab("<html><font color=\"#0000ff\">Details Away Team</font></html>", teamAwayPane);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 0));
        infoPanel.add(this.fTabbedPane);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(infoPanel);
        this.pack();
        this.setLocationToCenter();
    }

    @Override
    public DialogId getId() {
        return DialogId.GAME_STATISTICS;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent pE) {
        if (this.getCloseListener() != null) {
            this.getCloseListener().dialogClosed(this);
        }
    }

    private JEditorPane createTeamComparisonEditorPane() {
        JEditorPane teamComparisonPane = new JEditorPane();
        teamComparisonPane.setEditable(false);
        teamComparisonPane.setContentType("text/html");
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        StringBuilder statistics = new StringBuilder();
        statistics.append("<html>\n");
        statistics.append("<body>\n");
        statistics.append("<font face=\"Sans Serif\" size=\"+1\"><b>").append("Team Comparison").append("</b></font>").append("<br/>\n");
        statistics.append("<br/>\n");
        statistics.append("<table border=\"1\" cellspacing=\"0\">\n");
        statistics.append("<tr>\n");
        statistics.append("  <td></td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(game.getTeamHome().getName()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(game.getTeamAway().getName()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Team Value").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(StringTool.formatThousands(gameResult.getTeamResultHome().getTeamValue() / 1000)).append("k").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(StringTool.formatThousands(gameResult.getTeamResultAway().getTeamValue() / 1000)).append("k").append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Completions").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalCompletions()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalCompletions()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Touchdowns").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().getScore()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().getScore()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Interceptions").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalInterceptions()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalInterceptions()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Casualties").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalCasualties()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalCasualties()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Earned SPPs").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalEarnedSpps()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalEarnedSpps()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Suffered Injuries").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>");
        statistics.append(gameResult.getTeamResultHome().getBadlyHurtSuffered());
        statistics.append(" / ").append(gameResult.getTeamResultHome().getSeriousInjurySuffered());
        statistics.append(" / ").append(gameResult.getTeamResultHome().getRipSuffered());
        statistics.append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>");
        statistics.append(gameResult.getTeamResultAway().getBadlyHurtSuffered());
        statistics.append(" / ").append(gameResult.getTeamResultAway().getSeriousInjurySuffered());
        statistics.append(" / ").append(gameResult.getTeamResultAway().getRipSuffered());
        statistics.append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Passing").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalPassing()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalPassing()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Rushing").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalRushing()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalRushing()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Blocks").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalBlocks()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalBlocks()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Fouls").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().totalFouls()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().totalFouls()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Fan Factor").append("</b></font>").append("</td>\n");
        StringBuilder fanFactorHome = new StringBuilder();
        fanFactorHome.append(game.getTeamHome().getFanFactor());
        if (gameResult.getTeamResultHome().getFanFactorModifier() > 0) {
            fanFactorHome.append(" + ").append(gameResult.getTeamResultHome().getFanFactorModifier());
        }
        if (gameResult.getTeamResultHome().getFanFactorModifier() < 0) {
            fanFactorHome.append(" - ").append(Math.abs(gameResult.getTeamResultHome().getFanFactorModifier()));
        }
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(fanFactorHome.toString()).append("</b></font>").append("</td>\n");
        StringBuilder fanFactorAway = new StringBuilder();
        fanFactorAway.append(game.getTeamAway().getFanFactor());
        if (gameResult.getTeamResultAway().getFanFactorModifier() > 0) {
            fanFactorAway.append(" + ").append(gameResult.getTeamResultAway().getFanFactorModifier());
        }
        if (gameResult.getTeamResultAway().getFanFactorModifier() < 0) {
            fanFactorAway.append(" - ").append(Math.abs(gameResult.getTeamResultAway().getFanFactorModifier()));
        }
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(fanFactorAway.toString()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Assistant Coaches").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(game.getTeamHome().getAssistantCoaches()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(game.getTeamAway().getAssistantCoaches()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Cheerleaders").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(game.getTeamHome().getCheerleaders()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(game.getTeamAway().getCheerleaders()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Spectators").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().getSpectators()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().getSpectators()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        statistics.append("<tr>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Fame").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().getFame()).append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().getFame()).append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        if (gameResult.getTeamResultHome().getWinnings() > 0 || gameResult.getTeamResultAway().getWinnings() > 0) {
            statistics.append("<tr>\n");
            statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Winnings").append("</b></font>").append("</td>\n");
            statistics.append("  <td align=\"right\">").append("<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultHome().getWinnings()).append("</b></font>").append("</td>\n");
            statistics.append("  <td align=\"right\">").append("<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(gameResult.getTeamResultAway().getWinnings()).append("</b></font>").append("</td>\n");
            statistics.append("</tr>\n");
        }
        statistics.append("</table>\n");
        statistics.append("</body>\n");
        statistics.append("</html>");
        teamComparisonPane.setText(statistics.toString());
        return teamComparisonPane;
    }

    private JEditorPane createTeamEditorPane(Team pTeam) {
        JEditorPane teamPane = new JEditorPane();
        teamPane.setEditable(false);
        teamPane.setContentType("text/html");
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        boolean homeTeam = game.getTeamHome() == pTeam;
        StringBuilder statistics = new StringBuilder();
        statistics.append("<html>\n");
        statistics.append("<body>\n");
        statistics.append(homeTeam ? "<font color=\"#ff0000\" face=\"Sans Serif\" size=\"+1\"><b>" : "<font color=\"#0000ff\" face=\"Sans Serif\" size=\"+1\"><b>").append(pTeam.getName()).append("</b></font>").append("<br/>\n");
        statistics.append("<br/>\n");
        statistics.append("<table border=\"1\" cellspacing=\"0\">\n");
        statistics.append("<tr>\n");
        statistics.append("  <td colspan=\"2\" align=\"left\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Player").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Turns").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Cps").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("TDs").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Int").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Cas").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("MVP").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\" bgcolor=\"").append("#c0c0c0").append("\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("SPP").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Pass").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Rush").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Blocks").append("</b></font>").append("</td>\n");
        statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append("Fouls").append("</b></font>").append("</td>\n");
        statistics.append("</tr>\n");
        for (Player player : pTeam.getPlayers()) {
            PlayerResult playerResult = gameResult.getPlayerResult(player);
            statistics.append("<tr>\n");
            statistics.append("  <td align=\"right\">").append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(player.getNr()).append("</b></font>").append("</td>\n");
            statistics.append("  <td align=\"left\">").append(homeTeam ? "<font color=\"#ff0000\" face=\"Sans Serif\" size=\"-1\"><b>" : "<font color=\"#0000ff\" face=\"Sans Serif\" size=\"-1\"><b>").append(player.getName()).append("</b></font>").append("</td>\n");
            statistics.append("  <td align=\"right\">").append(this.formatPlayerStat(playerResult.getTurnsPlayed())).append("</td>\n");
            statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append(this.formatPlayerStat(playerResult.getCompletions())).append("</td>\n");
            statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append(this.formatPlayerStat(playerResult.getTouchdowns())).append("</td>\n");
            statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append(this.formatPlayerStat(playerResult.getInterceptions())).append("</td>\n");
            statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append(this.formatPlayerStat(playerResult.getCasualties())).append("</td>\n");
            statistics.append("  <td align=\"right\" bgcolor=\"").append("#e0e0e0").append("\">").append(this.formatPlayerStat(playerResult.getPlayerAwards())).append("</td>\n");
            statistics.append("  <td align=\"right\" bgcolor=\"").append("#c0c0c0").append("\">").append(this.formatPlayerStat(playerResult.totalEarnedSpps())).append("</td>\n");
            statistics.append("  <td align=\"right\">").append(this.formatPlayerStat(playerResult.getPassing())).append("</td>\n");
            statistics.append("  <td align=\"right\">").append(this.formatPlayerStat(playerResult.getRushing())).append("</td>\n");
            statistics.append("  <td align=\"right\">").append(this.formatPlayerStat(playerResult.getBlocks())).append("</td>\n");
            statistics.append("  <td align=\"right\">").append(this.formatPlayerStat(playerResult.getFouls())).append("</td>\n");
            statistics.append("</tr>\n");
        }
        statistics.append("</table>\n");
        statistics.append("</p>\n");
        statistics.append("</body>\n");
        statistics.append("</html>");
        teamPane.setText(statistics.toString());
        teamPane.setCaretPosition(0);
        return teamPane;
    }

    private String formatPlayerStat(int pPlayerStat) {
        StringBuilder formattedStat = new StringBuilder();
        if (pPlayerStat != 0) {
            formattedStat.append("<font face=\"Sans Serif\" size=\"-1\"><b>").append(pPlayerStat).append("</b></font>");
        } else {
            formattedStat.append("&nbsp;");
        }
        return formattedStat.toString();
    }

    @Override
    protected void setLocationToCenter() {
        Dimension dialogSize = this.getSize();
        Dimension frameSize = this.getClient().getUserInterface().getSize();
        Dimension menuBarSize = this.getClient().getUserInterface().getGameMenuBar().getSize();
        this.setLocation((frameSize.width - dialogSize.width) / 2, (frameSize.height - dialogSize.height) / 2 - menuBarSize.height);
    }
}

