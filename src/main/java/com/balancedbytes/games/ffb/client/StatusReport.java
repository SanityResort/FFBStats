/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ArmorModifier;
import com.balancedbytes.games.ffb.BlockResult;
import com.balancedbytes.games.ffb.BlockResultFactory;
import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.CatchModifier;
import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.DodgeModifier;
import com.balancedbytes.games.ffb.HeatExhaustion;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.InjuryModifier;
import com.balancedbytes.games.ffb.InterceptionModifier;
import com.balancedbytes.games.ffb.KickoffResult;
import com.balancedbytes.games.ffb.KnockoutRecovery;
import com.balancedbytes.games.ffb.LeaderState;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.PickupModifier;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.PushbackMode;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.ServerStatus;
import com.balancedbytes.games.ffb.net.commands.ServerCommandJoin;
import com.balancedbytes.games.ffb.net.commands.ServerCommandLeave;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportApothecaryChoice;
import com.balancedbytes.games.ffb.report.ReportApothecaryRoll;
import com.balancedbytes.games.ffb.report.ReportArgueTheCallRoll;
import com.balancedbytes.games.ffb.report.ReportBiteSpectator;
import com.balancedbytes.games.ffb.report.ReportBlock;
import com.balancedbytes.games.ffb.report.ReportBlockChoice;
import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import com.balancedbytes.games.ffb.report.ReportBombOutOfBounds;
import com.balancedbytes.games.ffb.report.ReportBribesRoll;
import com.balancedbytes.games.ffb.report.ReportCardDeactivated;
import com.balancedbytes.games.ffb.report.ReportCardEffectRoll;
import com.balancedbytes.games.ffb.report.ReportCardsBought;
import com.balancedbytes.games.ffb.report.ReportCatchRoll;
import com.balancedbytes.games.ffb.report.ReportCoinThrow;
import com.balancedbytes.games.ffb.report.ReportConfusionRoll;
import com.balancedbytes.games.ffb.report.ReportDauntlessRoll;
import com.balancedbytes.games.ffb.report.ReportDefectingPlayers;
import com.balancedbytes.games.ffb.report.ReportDoubleHiredStarPlayer;
import com.balancedbytes.games.ffb.report.ReportFanFactorRoll;
import com.balancedbytes.games.ffb.report.ReportFoul;
import com.balancedbytes.games.ffb.report.ReportFumbblResultUpload;
import com.balancedbytes.games.ffb.report.ReportHandOver;
import com.balancedbytes.games.ffb.report.ReportInducement;
import com.balancedbytes.games.ffb.report.ReportInducementsBought;
import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.report.ReportInterceptionRoll;
import com.balancedbytes.games.ffb.report.ReportKickoffExtraReRoll;
import com.balancedbytes.games.ffb.report.ReportKickoffPitchInvasion;
import com.balancedbytes.games.ffb.report.ReportKickoffResult;
import com.balancedbytes.games.ffb.report.ReportKickoffRiot;
import com.balancedbytes.games.ffb.report.ReportKickoffScatter;
import com.balancedbytes.games.ffb.report.ReportKickoffThrowARock;
import com.balancedbytes.games.ffb.report.ReportLeader;
import com.balancedbytes.games.ffb.report.ReportList;
import com.balancedbytes.games.ffb.report.ReportMasterChefRoll;
import com.balancedbytes.games.ffb.report.ReportMostValuablePlayers;
import com.balancedbytes.games.ffb.report.ReportNoPlayersToField;
import com.balancedbytes.games.ffb.report.ReportPassBlock;
import com.balancedbytes.games.ffb.report.ReportPassRoll;
import com.balancedbytes.games.ffb.report.ReportPenaltyShootout;
import com.balancedbytes.games.ffb.report.ReportPettyCash;
import com.balancedbytes.games.ffb.report.ReportPilingOn;
import com.balancedbytes.games.ffb.report.ReportPlayCard;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import com.balancedbytes.games.ffb.report.ReportPushback;
import com.balancedbytes.games.ffb.report.ReportRaiseDead;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import com.balancedbytes.games.ffb.report.ReportReceiveChoice;
import com.balancedbytes.games.ffb.report.ReportReferee;
import com.balancedbytes.games.ffb.report.ReportScatterBall;
import com.balancedbytes.games.ffb.report.ReportScatterPlayer;
import com.balancedbytes.games.ffb.report.ReportSecretWeaponBan;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportSkillUse;
import com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import com.balancedbytes.games.ffb.report.ReportSpectators;
import com.balancedbytes.games.ffb.report.ReportStandUpRoll;
import com.balancedbytes.games.ffb.report.ReportStartHalf;
import com.balancedbytes.games.ffb.report.ReportTentaclesShadowingRoll;
import com.balancedbytes.games.ffb.report.ReportThrowIn;
import com.balancedbytes.games.ffb.report.ReportThrowTeamMateRoll;
import com.balancedbytes.games.ffb.report.ReportTimeoutEnforced;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import com.balancedbytes.games.ffb.report.ReportWeather;
import com.balancedbytes.games.ffb.report.ReportWinningsRoll;
import com.balancedbytes.games.ffb.report.ReportWizardUse;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

public class StatusReport {
    private FantasyFootballClient fClient;
    private int fIndent;
    private boolean fShowModifiersOnSuccess;
    private boolean fShowModifiersOnFailure;
    private boolean fPettyCashReportReceived;
    private boolean fCardsBoughtReportReceived;
    private boolean fInducmentsBoughtReportReceived;

    public StatusReport(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fShowModifiersOnSuccess = true;
        this.fShowModifiersOnFailure = true;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public int getIndent() {
        return this.fIndent;
    }

    public void setIndent(int pIndent) {
        this.fIndent = pIndent;
    }

    public void reportVersion() {
        StringBuilder status = new StringBuilder();
        status.append("FantasyFootballClient Version ").append("1.3.0");
        this.println(0, status.toString());
    }

    public void reportConnecting(InetAddress pInetAddress, int pPort) {
        StringBuilder status = new StringBuilder();
        status.append("Connecting to ").append(pInetAddress).append(":").append(pPort).append(" ...");
        this.println(0, status.toString());
    }

    public void reportIconLoadFailure(URL pIconUrl) {
        StringBuilder status = new StringBuilder();
        status.append("Unable to load icon from URL ").append(pIconUrl).append(".");
        this.println(0, status.toString());
    }

    public void reportRetrying() {
        this.println(0, "Retrying ...");
    }

    public void reportTimeout() {
        this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.BOLD, "The timelimit has been reached for this turn.");
    }

    public void reportFumbblResultUpload(ReportFumbblResultUpload pReport) {
        StringBuilder status = new StringBuilder();
        status.append("Fumbbl Result Upload ");
        if (pReport.isSuccessful()) {
            status.append("ok");
        } else {
            status.append("failed");
        }
        this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.BOLD, status.toString());
        this.println(this.getIndent() + 1, pReport.getUploadStatus());
    }

    public void reportGameName(String pGameName) {
        if (StringTool.isProvided(pGameName)) {
            StringBuilder status = new StringBuilder();
            status.append("You have started a new game named \"").append(pGameName).append("\".");
            this.println(0, status.toString());
        }
    }

    public void reportSocketClosed() {
        if (this.getClient().getMode() != ClientMode.REPLAY) {
            this.println(ParagraphStyle.SPACE_ABOVE, TextStyle.NONE, "The connection to the server has been closed.");
            this.println(ParagraphStyle.SPACE_BELOW, TextStyle.NONE, "To re-connect you need to restart the client.");
        }
    }

    public void reportConnectionEstablished(boolean pSuccesful) {
        if (pSuccesful) {
            this.println(0, "Connection established.");
        } else {
            this.println(0, "Cannot connect to the server.");
        }
    }

    public void reportInducement(ReportInducement pReport) {
        Game game = this.getClient().getGame();
        if (StringTool.isProvided(pReport.getTeamId()) && pReport.getInducementType() != null) {
            if (pReport.getTeamId().equals(game.getTeamHome().getId())) {
                this.print(this.getIndent(), TextStyle.HOME, game.getTeamHome().getName());
            } else {
                this.print(this.getIndent(), TextStyle.AWAY, game.getTeamAway().getName());
            }
            StringBuilder status = new StringBuilder();
            switch (pReport.getInducementType()) {
                case EXTRA_TEAM_TRAINING: {
                    this.print(this.getIndent(), " use ");
                    this.print(this.getIndent(), TextStyle.BOLD, "Extra Team Training");
                    status.append(" to add ").append(pReport.getValue()).append(pReport.getValue() == 1 ? " Re-Roll." : " Re-Rolls.");
                    this.println(this.getIndent(), status.toString());
                    break;
                }
                case WANDERING_APOTHECARIES: {
                    this.print(this.getIndent(), " use ");
                    this.print(this.getIndent(), TextStyle.BOLD, "Wandering Apothecaries");
                    status.append(" to add ").append(pReport.getValue()).append(pReport.getValue() == 1 ? " Apothecary." : " Apothecaries.");
                    this.println(this.getIndent(), status.toString());
                    break;
                }
                case IGOR: {
                    this.print(this.getIndent(), " use ");
                    this.print(this.getIndent(), TextStyle.BOLD, "Igor");
                    this.println(this.getIndent(), " to re-roll the failed Regeneration.");
                    break;
                }
            }
        }
    }

    public void reportStartHalf(ReportStartHalf pReport) {
        StringBuilder status = new StringBuilder();
        status.append("Starting ");
        if (pReport.getHalf() > 2) {
            status.append("Overtime");
        } else if (pReport.getHalf() > 1) {
            status.append("2nd half");
        } else {
            status.append("1st half");
        }
        this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN, status.toString());
    }

    public void reportMasterChef(ReportMasterChefRoll pReport) {
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        int[] roll = pReport.getMasterChefRoll();
        status.append("Master Chef Roll [ ").append(roll[0]).append(" ][ ").append(roll[1]).append(" ][ ").append(roll[2]).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        this.printTeamName(game, false, pReport.getTeamId());
        status.append(" steal ");
        if (pReport.getReRollsStolen() == 0) {
            status.append(" no re-rolls from ");
        } else if (pReport.getReRollsStolen() == 1) {
            status.append(pReport.getReRollsStolen()).append(" re-roll from ");
        } else {
            status.append(pReport.getReRollsStolen()).append(" re-rolls from ");
        }
        this.print(this.getIndent() + 1, status.toString());
        if (game.getTeamHome().getId().equals(pReport.getTeamId())) {
            this.printTeamName(game, false, game.getTeamAway().getId());
        } else {
            this.printTeamName(game, false, game.getTeamHome().getId());
        }
        this.println(this.getIndent() + 1, ".");
    }

    public void reportLeader(ReportLeader pReport) {
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        LeaderState leaderState = pReport.getLeaderState();
        if (LeaderState.AVAILABLE.equals(leaderState)) {
            this.printTeamName(game, false, pReport.getTeamId());
            status.append(" gain a Leader re-roll.");
            this.print(this.getIndent() + 1, status.toString());
        } else {
            status.append("Leader re-roll removed from ");
            this.print(this.getIndent() + 1, status.toString());
            this.printTeamName(game, false, pReport.getTeamId());
        }
        this.println(this.getIndent() + 1, ".");
    }

    public void reportScatterBall(ReportScatterBall pReport) {
        int[] rolls;
        StringBuilder status = new StringBuilder();
        if (pReport.isGustOfWind()) {
            this.setIndent(this.getIndent() + 1);
            status.append("A gust of wind scatters the ball 1 square.");
            this.println(this.getIndent(), status.toString());
            status = new StringBuilder();
        }
        if (ArrayTool.isProvided(rolls = pReport.getRolls())) {
            if (rolls.length > 1) {
                status.append("Scatter Rolls [ ");
            } else {
                status.append("Scatter Roll [ ");
            }
            for (int i = 0; i < rolls.length; ++i) {
                if (i > 0) {
                    status.append(", ");
                }
                status.append(rolls[i]);
            }
            status.append(" ] ");
            Direction[] directions = pReport.getDirections();
            for (int i = 0; i < directions.length; ++i) {
                if (i > 0) {
                    status.append(", ");
                }
                status.append(directions[i].getName());
            }
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        }
        if (pReport.isGustOfWind()) {
            this.setIndent(this.getIndent() - 1);
        }
    }

    public void reportBombOutOfBounds(ReportBombOutOfBounds pReport) {
        this.println(this.getIndent(), TextStyle.BOLD, "Bomb scattered out of bounds.");
    }

    public void reportThrowIn(ReportThrowIn pReport) {
        int directionRoll = pReport.getDirectionRoll();
        int[] distanceRoll = pReport.getDistanceRoll();
        Direction direction = pReport.getDirection();
        if (distanceRoll != null && distanceRoll.length > 1 && direction != null) {
            StringBuilder status = new StringBuilder();
            status.append("Throw In Direction Roll [ ").append(directionRoll).append(" ] ").append(direction.getName());
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            status = new StringBuilder();
            status.append("Throw In Distance Roll [ ").append(distanceRoll[0]).append(" ][ ").append(distanceRoll[1]).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            this.println(this.getIndent() + 1, "The fans throw the ball back onto the pitch.");
            status = new StringBuilder();
            int distance = distanceRoll[0] + distanceRoll[1];
            status.append("It lands ").append(distance).append(" squares ").append(direction.getName());
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportJoin(ServerCommandJoin pJoinCommand) {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == pJoinCommand.getClientMode()) {
            this.print(0, TextStyle.BOLD, "Player ");
            if (StringTool.isProvided(game.getTeamHome().getCoach())) {
                if (game.getTeamHome().getCoach().equals(pJoinCommand.getCoach())) {
                    this.print(0, TextStyle.HOME_BOLD, pJoinCommand.getCoach());
                } else {
                    this.print(0, TextStyle.AWAY_BOLD, pJoinCommand.getCoach());
                }
            } else {
                this.print(0, TextStyle.BOLD, pJoinCommand.getCoach());
            }
            this.println(0, TextStyle.BOLD, " joins the game.");
        } else if (ClientMode.SPECTATOR == this.getClient().getMode()) {
            this.print(0, "Spectator ");
            this.print(0, pJoinCommand.getCoach());
            this.println(0, " joins the game.");
        } else {
            this.println(0, "A spectator joins the game.");
        }
    }

    public void reportLeave(ServerCommandLeave pLeaveCommand) {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == pLeaveCommand.getClientMode()) {
            if (game.getTeamHome() != null && StringTool.isProvided(game.getTeamHome().getCoach())) {
                this.print(0, TextStyle.BOLD, "Player ");
                if (game.getTeamHome().getCoach().equals(pLeaveCommand.getCoach())) {
                    this.print(0, TextStyle.HOME_BOLD, pLeaveCommand.getCoach());
                } else {
                    this.print(0, TextStyle.AWAY_BOLD, pLeaveCommand.getCoach());
                }
                this.println(0, TextStyle.BOLD, " leaves the game.");
            } else {
                this.println(0, TextStyle.BOLD, "The other player leaves the game.");
            }
        } else if (ClientMode.SPECTATOR == this.getClient().getMode()) {
            this.print(0, "Spectator ");
            this.print(0, pLeaveCommand.getCoach());
            this.println(0, " leaves the game.");
        } else {
            this.println(0, "A spectator leaves the game.");
        }
    }

    public void reportTimeoutEnforced(ReportTimeoutEnforced pReport) {
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        status.append("Coach ").append(pReport.getCoach()).append(" forces a Timeout.");
        if (game.getTeamHome().getCoach().equals(pReport.getCoach())) {
            this.println(ParagraphStyle.SPACE_ABOVE, TextStyle.HOME_BOLD, status.toString());
        } else {
            this.println(ParagraphStyle.SPACE_ABOVE, TextStyle.AWAY_BOLD, status.toString());
        }
        this.println(ParagraphStyle.SPACE_BELOW, TextStyle.NONE, "The turn will end after the Acting Player has finished moving.");
    }

    public void reportDoubleHiredStarPlayer(ReportDoubleHiredStarPlayer pReport) {
        StringBuilder status = new StringBuilder();
        status.append("Star Player ").append(pReport.getStarPlayerName());
        status.append(" takes money from both teams and plays for neither.");
        this.println(this.getIndent(), TextStyle.BOLD, status.toString());
    }

    public void reportGoingForIt(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Go For It Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            this.println(this.getIndent() + 1, " goes for it!");
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 1, " trips while going for it.");
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (Roll").append(this.formatRollModifiers(pReport.getRollModifiers())).append(" > ").append(pReport.getMinimumRoll() - 1).append(").");
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    private String formatRollModifiers(IRollModifier[] pRollModifiers) {
        StringBuilder modifiers = new StringBuilder();
        if (ArrayTool.isProvided(pRollModifiers)) {
            for (IRollModifier rollModifier : pRollModifiers) {
                if (rollModifier.getModifier() == 0) continue;
                if (rollModifier.getModifier() > 0) {
                    modifiers.append(" - ");
                } else {
                    modifiers.append(" + ");
                }
                if (!rollModifier.isModifierIncluded()) {
                    modifiers.append(Math.abs(rollModifier.getModifier())).append(" ");
                }
                modifiers.append(rollModifier.getName());
            }
        }
        return modifiers.toString();
    }

    public void reportSpectators(ReportSpectators pReport) {
        this.setIndent(0);
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        int[] fanRollHome = pReport.getSpectatorRollHome();
        status.append("Spectator Roll Home Team [ ").append(fanRollHome[0]).append(" ][ ").append(fanRollHome[1]).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        int rolledTotalHome = fanRollHome[0] + fanRollHome[1];
        status.append("Rolled Total of ").append(rolledTotalHome);
        int fanFactorHome = game.getTeamHome().getFanFactor();
        status.append(" + ").append(fanFactorHome).append(" Fan Factor");
        status.append(" = ").append(rolledTotalHome + fanFactorHome);
        this.println(this.getIndent() + 1, status.toString());
        status = new StringBuilder();
        status.append(StringTool.formatThousands(pReport.getSpectatorsHome())).append(" fans have come to support ");
        this.print(this.getIndent() + 1, status.toString());
        this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
        this.println(this.getIndent() + 1, ".");
        status = new StringBuilder();
        int[] fanRollAway = pReport.getSpectatorRollAway();
        status.append("Spectator Roll Away Team [ ").append(fanRollAway[0]).append(" ][ ").append(fanRollAway[1]).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        int rolledTotalAway = fanRollAway[0] + fanRollAway[1];
        status.append("Rolled Total of ").append(rolledTotalAway);
        int fanFactorAway = game.getTeamAway().getFanFactor();
        status.append(" + ").append(fanFactorAway).append(" Fan Factor");
        status.append(" = ").append(rolledTotalAway + fanFactorAway);
        this.println(this.getIndent() + 1, status.toString());
        status = new StringBuilder();
        status.append(StringTool.formatThousands(pReport.getSpectatorsAway())).append(" fans have come to support ");
        this.print(this.getIndent() + 1, status.toString());
        this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
        this.println(this.getIndent() + 1, ".");
        status = new StringBuilder();
        if (pReport.getFameHome() > pReport.getFameAway()) {
            status.append("Team ").append(game.getTeamHome().getName());
            if (pReport.getFameHome() - pReport.getFameAway() > 1) {
                status.append(" have the whole audience with them (FAME +2)!");
            } else {
                status.append(" have a fan advantage (FAME +1) for the game.");
            }
            this.println(this.getIndent(), TextStyle.HOME_BOLD, status.toString());
        } else if (pReport.getFameAway() > pReport.getFameHome()) {
            status.append("Team ").append(game.getTeamAway().getName());
            if (pReport.getFameAway() - pReport.getFameHome() > 1) {
                status.append(" have the whole audience with them (FAME +2)!");
            } else {
                status.append(" have a fan advantage (FAME +1) for the game.");
            }
            this.println(this.getIndent(), TextStyle.AWAY_BOLD, status.toString());
        } else {
            this.println(this.getIndent(), TextStyle.BOLD, "Both teams have equal fan support (FAME 0).");
        }
    }

    public void reportPettyCash(ReportPettyCash pReport) {
        Game game = this.getClient().getGame();
        if (!this.fPettyCashReportReceived) {
            this.fPettyCashReportReceived = true;
            this.println(this.getIndent(), TextStyle.BOLD, "Transfer Petty Cash");
        }
        this.print(this.getIndent() + 1, "Team ");
        Team team = null;
        if (game.getTeamHome().getId().equals(pReport.getTeamId())) {
            this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
            team = game.getTeamHome();
        } else {
            this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
            team = game.getTeamAway();
        }
        StringBuilder status = new StringBuilder();
        status.append(" transfers ");
        if (pReport.getGold() > 0) {
            status.append(StringTool.formatThousands(pReport.getGold()));
            status.append(" gold");
        } else {
            status.append("nothing");
        }
        status.append(" from the Treasury into Petty Cash.");
        this.println(this.getIndent() + 1, status.toString());
        if (pReport.getGold() > team.getTreasury()) {
            status = new StringBuilder();
            status.append("They received an extra ");
            status.append(StringTool.formatThousands(pReport.getGold() - team.getTreasury()));
            status.append(" gold for being the underdog.");
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportInducementsBought(ReportInducementsBought pReport) {
        Game game = this.getClient().getGame();
        if (!this.fInducmentsBoughtReportReceived) {
            this.fInducmentsBoughtReportReceived = true;
            this.println(this.getIndent(), TextStyle.BOLD, "Buy Inducements");
        }
        this.print(this.getIndent() + 1, "Team ");
        if (game.getTeamHome().getId().equals(pReport.getTeamId())) {
            this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
        } else {
            this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
        }
        StringBuilder status = new StringBuilder();
        status.append(" buys ");
        if (pReport.getNrOfInducements() == 0 && pReport.getNrOfStars() == 0 && pReport.getNrOfMercenaries() == 0) {
            status.append("no Inducements.");
        } else {
            ArrayList<String> itemList = new ArrayList<String>();
            if (pReport.getNrOfInducements() > 0) {
                if (pReport.getNrOfInducements() == 1) {
                    itemList.add("1 Inducement");
                } else {
                    itemList.add(StringTool.bind("$1 Inducements", pReport.getNrOfInducements()));
                }
            }
            if (pReport.getNrOfStars() > 0) {
                if (pReport.getNrOfStars() == 1) {
                    itemList.add("1 Star");
                } else {
                    itemList.add(StringTool.bind("$1 Stars", pReport.getNrOfStars()));
                }
            }
            if (pReport.getNrOfMercenaries() > 0) {
                if (pReport.getNrOfMercenaries() == 1) {
                    itemList.add("1 Mercenary");
                } else {
                    itemList.add(StringTool.bind("$1 Mercenaries", pReport.getNrOfMercenaries()));
                }
            }
            status.append(StringTool.buildEnumeration(itemList.toArray(new String[itemList.size()])));
            status.append(" for ").append(StringTool.formatThousands(pReport.getGold())).append(" gold total.");
        }
        this.println(this.getIndent() + 1, status.toString());
    }

    public void reportCardsBought(ReportCardsBought pReport) {
        Game game = this.getClient().getGame();
        if (!this.fCardsBoughtReportReceived) {
            this.fCardsBoughtReportReceived = true;
            this.println(this.getIndent(), TextStyle.BOLD, "Buy Cards");
        }
        this.print(this.getIndent() + 1, "Team ");
        if (game.getTeamHome().getId().equals(pReport.getTeamId())) {
            this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
        } else {
            this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
        }
        StringBuilder status = new StringBuilder();
        status.append(" buys ");
        if (pReport.getNrOfCards() == 0) {
            status.append("no Cards.");
        } else {
            if (pReport.getNrOfCards() == 1) {
                status.append("1 Card");
            } else {
                status.append(pReport.getNrOfCards()).append(" Cards");
            }
            status.append(" for ").append(StringTool.formatThousands(pReport.getGold())).append(" gold total.");
        }
        this.println(this.getIndent() + 1, status.toString());
    }

    public void reportKickoffExtraReRoll(ReportKickoffExtraReRoll pReport) {
        int totalAway;
        int totalHome;
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        KickoffResult kickoffResult = pReport.getKickoffResult();
        int fanFavouritesHome = UtilPlayer.findPlayersOnPitchWithSkill(game, game.getTeamHome(), Skill.FAN_FAVOURITE).length;
        int fanFavouritesAway = UtilPlayer.findPlayersOnPitchWithSkill(game, game.getTeamAway(), Skill.FAN_FAVOURITE).length;
        StringBuilder status = new StringBuilder();
        if (kickoffResult == KickoffResult.CHEERING_FANS) {
            status.append("Cheering Fans Roll Home Team [ ").append(pReport.getRollHome()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            totalHome = pReport.getRollHome() + gameResult.getTeamResultHome().getFame() + fanFavouritesHome + game.getTeamHome().getCheerleaders();
            status = new StringBuilder();
            status.append("Rolled ").append(pReport.getRollHome());
            status.append(" + ").append(gameResult.getTeamResultHome().getFame()).append(" FAME");
            status.append(" + ").append(fanFavouritesHome).append(" Fan Favourites");
            status.append(" + ").append(game.getTeamHome().getCheerleaders()).append(" Cheerleaders");
            status.append(" = ").append(totalHome).append(".");
            this.println(this.getIndent() + 1, status.toString());
            status = new StringBuilder();
            status.append("Cheering Fans Roll Away Team [ ").append(pReport.getRollAway()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            totalAway = pReport.getRollAway() + gameResult.getTeamResultAway().getFame() + fanFavouritesAway + game.getTeamAway().getCheerleaders();
            status = new StringBuilder();
            status.append("Rolled ").append(pReport.getRollAway());
            status.append(" + ").append(gameResult.getTeamResultAway().getFame()).append(" FAME");
            status.append(" + ").append(fanFavouritesAway).append(" Fan Favourites");
            status.append(" + ").append(game.getTeamAway().getCheerleaders()).append(" Cheerleaders");
            status.append(" = ").append(totalAway).append(".");
            this.println(this.getIndent() + 1, status.toString());
        }
        if (kickoffResult == KickoffResult.BRILLIANT_COACHING) {
            status.append("Brilliant Coaching Roll Home Team [ ").append(pReport.getRollHome()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            totalHome = pReport.getRollHome() + gameResult.getTeamResultHome().getFame() + fanFavouritesHome + game.getTeamHome().getAssistantCoaches();
            status = new StringBuilder();
            status.append("Rolled ").append(pReport.getRollHome());
            status.append(" + ").append(gameResult.getTeamResultHome().getFame()).append(" FAME");
            status.append(" + ").append(fanFavouritesHome).append(" Fan Favourites");
            status.append(" + ").append(game.getTeamHome().getAssistantCoaches()).append(" Assistant Coaches");
            status.append(" = ").append(totalHome).append(".");
            this.println(this.getIndent() + 1, status.toString());
            status = new StringBuilder();
            status.append("Brilliant Coaching Roll Away Team [ ").append(pReport.getRollAway()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            totalAway = pReport.getRollAway() + gameResult.getTeamResultAway().getFame() + fanFavouritesAway + game.getTeamAway().getAssistantCoaches();
            status = new StringBuilder();
            status.append("Rolled ").append(pReport.getRollAway());
            status.append(" + ").append(gameResult.getTeamResultAway().getFame()).append(" FAME");
            status.append(" + ").append(fanFavouritesAway).append(" Fan Favourites");
            status.append(" + ").append(game.getTeamAway().getAssistantCoaches()).append(" Assistant Coaches");
            status.append(" = ").append(totalAway).append(".");
            this.println(this.getIndent() + 1, status.toString());
        }
        if (pReport.isHomeGainsReRoll()) {
            this.print(this.getIndent(), "Team ");
            this.print(this.getIndent(), TextStyle.HOME, game.getTeamHome().getName());
            this.println(this.getIndent(), " gains a Re-Roll.");
        }
        if (pReport.isAwayGainsReRoll()) {
            this.print(this.getIndent(), "Team ");
            this.print(this.getIndent(), TextStyle.AWAY, game.getTeamAway().getName());
            this.println(this.getIndent(), " gains a Re-Roll.");
        }
    }

    public void reportKickoffThrowARock(ReportKickoffThrowARock pReport) {
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        int fanFavouritesHome = UtilPlayer.findPlayersOnPitchWithSkill(game, game.getTeamHome(), Skill.FAN_FAVOURITE).length;
        int fanFavouritesAway = UtilPlayer.findPlayersOnPitchWithSkill(game, game.getTeamAway(), Skill.FAN_FAVOURITE).length;
        StringBuilder status = new StringBuilder();
        status.append("Throw a Rock Roll Home Team [ ").append(pReport.getRollHome()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        int totalHome = pReport.getRollHome() + gameResult.getTeamResultHome().getFame() + fanFavouritesHome;
        status = new StringBuilder();
        status.append("Rolled ").append(pReport.getRollHome());
        status.append(" + ").append(gameResult.getTeamResultHome().getFame()).append(" FAME");
        status.append(" + ").append(fanFavouritesHome).append(" Fan Favourites");
        status.append(" = ").append(totalHome).append(".");
        this.println(this.getIndent() + 1, status.toString());
        status = new StringBuilder();
        status.append("Throw a Rock Roll Away Team [ ").append(pReport.getRollAway()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        int totalAway = pReport.getRollAway() + gameResult.getTeamResultAway().getFame() + fanFavouritesAway;
        status = new StringBuilder();
        status.append("Rolled ").append(pReport.getRollAway());
        status.append(" + ").append(gameResult.getTeamResultAway().getFame()).append(" FAME");
        status.append(" + ").append(fanFavouritesAway).append(" Fan Favourites");
        status.append(" = ").append(totalAway).append(".");
        this.println(this.getIndent() + 1, status.toString());
        for (String playerId : pReport.getPlayersHit()) {
            Player player = game.getPlayerById(playerId);
            this.print(this.getIndent(), false, player);
            this.println(this.getIndent(), " is hit by a rock.");
        }
    }

    public void reportSpecialEffectRoll(ReportSpecialEffectRoll pReport) {
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        if (pReport.getSpecialEffect() == SpecialEffect.LIGHTNING) {
            status.append("Lightning Spell Effect Roll [ ").append(pReport.getRoll()).append(" ]");
        }
        if (pReport.getSpecialEffect() == SpecialEffect.FIREBALL) {
            status.append("Fireball Spell Effect Roll [ ").append(pReport.getRoll()).append(" ]");
        }
        if (pReport.getSpecialEffect() == SpecialEffect.BOMB) {
            status.append("Bomb Effect Roll [ ");
            status.append(pReport.getRoll() > 0 ? Integer.valueOf(pReport.getRoll()) : "automatic success");
            status.append(" ]");
        }
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, game.getPlayerById(pReport.getPlayerId()));
        if (pReport.isSuccessful()) {
            if (pReport.getSpecialEffect().isWizardSpell()) {
                this.println(this.getIndent() + 1, " is hit by the spell.");
            } else {
                this.println(this.getIndent() + 1, " is hit by the explosion.");
            }
        } else if (pReport.getSpecialEffect().isWizardSpell()) {
            this.println(this.getIndent() + 1, " escapes the spell effect.");
        } else {
            this.println(this.getIndent() + 1, " escapes the explosion.");
        }
    }

    public void reportWizardUse(ReportWizardUse pReport) {
        Game game = this.getClient().getGame();
        this.print(this.getIndent(), TextStyle.BOLD, "The team wizard of ");
        if (game.getTeamHome().getId().equals(pReport.getTeamId())) {
            this.print(this.getIndent(), TextStyle.HOME_BOLD, game.getTeamHome().getName());
        } else {
            this.print(this.getIndent(), TextStyle.AWAY_BOLD, game.getTeamAway().getName());
        }
        if (pReport.getWizardSpell() == SpecialEffect.LIGHTNING) {
            this.println(this.getIndent(), TextStyle.BOLD, " casts a Lightning spell.");
        } else {
            this.println(this.getIndent(), TextStyle.BOLD, " casts a Fireball spell.");
        }
    }

    public void reportKickoffPitchInvasion(ReportKickoffPitchInvasion pReport) {
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        int fanFavouritesHome = UtilPlayer.findPlayersOnPitchWithSkill(game, game.getTeamHome(), Skill.FAN_FAVOURITE).length;
        int fanFavouritesAway = UtilPlayer.findPlayersOnPitchWithSkill(game, game.getTeamAway(), Skill.FAN_FAVOURITE).length;
        int[] rollsHome = pReport.getRollsHome();
        boolean[] playersAffectedHome = pReport.getPlayersAffectedHome();
        Player[] homePlayers = game.getTeamHome().getPlayers();
        for (int i = 0; i < homePlayers.length; ++i) {
            if (rollsHome[i] <= 0) continue;
            StringBuilder status = new StringBuilder();
            status.append("Pitch Invasion Roll [ ").append(rollsHome[i]).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 1, false, homePlayers[i]);
            status = new StringBuilder();
            if (playersAffectedHome[i]) {
                status.append(" has been stunned.");
            } else {
                status.append(" is unaffected.");
            }
            int total = rollsHome[i] + gameResult.getTeamResultAway().getFame() + fanFavouritesAway;
            status.append(" (Roll ").append(rollsHome[i]);
            status.append(" + ").append(gameResult.getTeamResultAway().getFame()).append(" opposing FAME");
            status.append(" + ").append(fanFavouritesAway).append(" opposing Fan Favourites");
            status.append(" = ").append(total).append(" Total)");
            this.println(this.getIndent() + 1, status.toString());
        }
        int[] rollsAway = pReport.getRollsAway();
        boolean[] playersAffectedAway = pReport.getPlayersAffectedAway();
        Player[] awayPlayers = game.getTeamAway().getPlayers();
        for (int i = 0; i < awayPlayers.length; ++i) {
            if (rollsAway[i] <= 0) continue;
            StringBuilder status = new StringBuilder();
            status.append("Pitch Invasion Roll [ ").append(rollsAway[i]).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 1, false, awayPlayers[i]);
            status = new StringBuilder();
            if (playersAffectedAway[i]) {
                status.append(" has been stunned.");
            } else {
                status.append(" is unaffected.");
            }
            int total = rollsAway[i] + gameResult.getTeamResultHome().getFame() + fanFavouritesHome;
            status.append(" (Roll ").append(rollsAway[i]);
            status.append(" + ").append(gameResult.getTeamResultHome().getFame()).append(" opposing FAME ");
            status.append(" + ").append(fanFavouritesHome).append(" opposing Fan Favourites");
            status.append(" = ").append(total).append(" Total)");
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportDefectingPlayers(ReportDefectingPlayers pReport) {
        Game game = this.getClient().getGame();
        Object[] playerIds = pReport.getPlayerIds();
        if (ArrayTool.isProvided(playerIds)) {
            int[] rolls = pReport.getRolls();
            boolean[] defecting = pReport.getDefectings();
            for (int i = 0; i < playerIds.length; ++i) {
                StringBuilder status = new StringBuilder();
                status.append("Defecting Players Roll [ ").append(rolls[i]).append(" ]");
                this.println(this.getIndent(), TextStyle.ROLL, status.toString());
                Player player = game.getPlayerById((String)playerIds[i]);
                this.print(this.getIndent() + 1, false, player);
                if (defecting[i]) {
                    this.println(this.getIndent() + 1, TextStyle.NONE, " leaves the team in disgust.");
                    continue;
                }
                this.println(this.getIndent() + 1, TextStyle.NONE, " stays with the team.");
            }
        }
    }

    public void reportSecretWeaponBan(ReportSecretWeaponBan pReport) {
        Game game = this.getClient().getGame();
        this.reportSecretWeaponBan(pReport, game.getTeamHome());
        this.reportSecretWeaponBan(pReport, game.getTeamAway());
    }

    private void reportSecretWeaponBan(ReportSecretWeaponBan pReport, Team pTeam) {
        Game game = this.getClient().getGame();
        Object[] playerIds = pReport.getPlayerIds();
        if (ArrayTool.isProvided(playerIds)) {
            int[] rolls = pReport.getRolls();
            boolean[] banned = pReport.getBans();
            for (int i = 0; i < playerIds.length; ++i) {
                Player player = game.getPlayerById((String)playerIds[i]);
                if (!pTeam.hasPlayer(player)) continue;
                if (banned[i]) {
                    this.print(this.getIndent(), "The ref bans ");
                    this.print(this.getIndent(), false, player);
                    this.println(this.getIndent(), " for using a Secret Weapon.");
                } else {
                    this.print(this.getIndent(), "The ref overlooks ");
                    this.print(this.getIndent(), false, player);
                    this.println(this.getIndent(), " using a Secret Weapon.");
                }
                Integer secretWeaponValue = player.getPosition().getSkillValue(Skill.SECRET_WEAPON);
                if (rolls[i] <= 0 || secretWeaponValue == null) continue;
                StringBuilder penalty = new StringBuilder();
                penalty.append("Penalty roll was ").append(rolls[i]);
                penalty.append(", banned on a ").append(secretWeaponValue).append("+");
                this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, penalty.toString());
            }
        }
    }

    public void reportKickoffRiot(ReportKickoffRiot pReport) {
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        if (pReport.getRoll() > 0) {
            status.append("Riot Roll [ ").append(pReport.getRoll()).append(" ]");
        } else {
            status.append("Riot in Turn ").append(game.isHomePlaying() ? game.getTurnDataAway().getTurnNr() : game.getTurnDataHome().getTurnNr());
        }
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        if (pReport.getTurnModifier() < 0) {
            this.println(this.getIndent() + 1, "The referee adjusts the clock back after the riot is over.");
            status = new StringBuilder();
            status.append("Turn Counter is moved ").append(Math.abs(pReport.getTurnModifier()));
            status.append(pReport.getTurnModifier() == -1 ? " step" : " steps").append(" backward.");
            this.println(this.getIndent() + 1, status.toString());
        } else {
            this.println(this.getIndent() + 1, "The referee does not stop the clock during the riot.");
            status = new StringBuilder();
            status.append("Turn Counter is moved ").append(Math.abs(pReport.getTurnModifier()));
            status.append(pReport.getTurnModifier() == -1 ? " step" : " steps").append(" forward.");
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportReRoll(ReportReRoll pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pReport.getPlayerId());
        StringBuilder status = new StringBuilder();
        if (ReRollSource.LONER == pReport.getReRollSource()) {
            status.append("Loner Roll [ ").append(pReport.getRoll()).append(" ]");
            this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 2, false, player);
            if (pReport.isSuccessful()) {
                this.println(this.getIndent() + 2, " may use a Team Re-Roll.");
            } else {
                this.println(this.getIndent() + 2, " wastes a Team Re-Roll.");
            }
        } else if (ReRollSource.PRO == pReport.getReRollSource()) {
            status.append("Pro Roll [ ").append(pReport.getRoll()).append(" ]");
            this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 2, false, player);
            status = new StringBuilder();
            if (pReport.isSuccessful()) {
                status.append("'s Pro skill allows ").append(player.getPlayerGender().getDative()).append(" to re-roll the action.");
            } else {
                status.append("'s Pro skill does not help ").append(player.getPlayerGender().getDative()).append(".");
            }
            this.println(this.getIndent() + 2, status.toString());
        } else {
            status.append("Re-Roll using ").append(pReport.getReRollSource().getName().toUpperCase());
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportDauntless(ReportDauntlessRoll pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        StringBuilder status = new StringBuilder();
        status.append("Dauntless Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        status = new StringBuilder();
        if (pReport.isSuccessful()) {
            status.append(" uses Dauntless to push ").append(player.getPlayerGender().getSelf()).append(" to Strength ").append(pReport.getStrength()).append(".");
        } else {
            status.append(" fails to push ").append(player.getPlayerGender().getGenitive()).append(" strength.");
        }
        this.println(this.getIndent() + 1, status.toString());
    }

    public void reportChainsaw(ReportSkillRoll pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        StringBuilder status = new StringBuilder();
        status.append("Chainsaw Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        status = new StringBuilder();
        if (pReport.isSuccessful()) {
            status.append(" uses ").append(player.getPlayerGender().getGenitive()).append(" Chainsaw.");
        } else {
            status.append("'s Chainsaw kicks back to hurt ").append(player.getPlayerGender().getDative()).append(".");
        }
        this.println(this.getIndent() + 1, status.toString());
    }

    public void reportFoulAppearance(ReportSkillRoll pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        StringBuilder status = new StringBuilder();
        status.append("Foul Appearance Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            this.println(this.getIndent() + 1, " resists the Foul Appearance.");
        } else {
            this.println(this.getIndent() + 1, " cannot overcome the Foul Appearance.");
        }
    }

    public void reportWeepingDagger(ReportSkillRoll pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        StringBuilder status = new StringBuilder();
        status.append("Weeping Dagger Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            this.println(this.getIndent() + 1, " poisons " + player.getPlayerGender().getGenitive() + " opponent.");
        } else {
            this.println(this.getIndent() + 1, " fails to poison " + player.getPlayerGender().getGenitive() + " opponent.");
        }
    }

    public void reportRaiseDead(ReportRaiseDead pReport) {
        Game game = this.getClient().getGame();
        Player raisedPlayer = game.getPlayerById(pReport.getPlayerId());
        this.print(this.getIndent(), false, raisedPlayer);
        if (pReport.isNurglesRot()) {
            this.print(this.getIndent(), " has been infected with Nurgle's Rot and will join team ");
        } else {
            this.print(this.getIndent(), " is raised from the dead to join team ");
        }
        if (game.getTeamHome().hasPlayer(raisedPlayer)) {
            this.print(this.getIndent(), TextStyle.HOME, game.getTeamHome().getName());
        } else {
            this.print(this.getIndent(), TextStyle.AWAY, game.getTeamAway().getName());
        }
        if (pReport.isNurglesRot()) {
            this.println(this.getIndent(), TextStyle.NONE, " as a Rotter in the next game.");
        } else {
            this.println(this.getIndent(), TextStyle.NONE, " as a Zombie.");
        }
    }

    public void reportKickoffResult(ReportKickoffResult pReport) {
        this.setIndent(0);
        StringBuilder status = new StringBuilder();
        int[] kickoffRoll = pReport.getKickoffRoll();
        status.append("Kick-off Event Roll [ ").append(kickoffRoll[0]).append(" ][ ").append(kickoffRoll[1]).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        status.append("Kick-off event is ").append(pReport.getKickoffResult().getName());
        this.println(this.getIndent() + 1, status.toString());
        this.println(this.getIndent() + 1, TextStyle.EXPLANATION, pReport.getKickoffResult().getDescription());
        this.setIndent(1);
    }

    public void reportKickoffScatter(ReportKickoffScatter pReport) {
        this.setIndent(0);
        StringBuilder status = new StringBuilder();
        status.append("Kick-off Scatter Roll [ ").append(pReport.getRollScatterDirection()).append(" ]");
        status.append("[ ").append(pReport.getRollScatterDistance()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        status.append("The kick will land ");
        status.append(pReport.getRollScatterDistance()).append(pReport.getRollScatterDistance() == 1 ? " square " : " squares ");
        status.append(pReport.getScatterDirection().getName().toLowerCase()).append(" of where it was aimed.");
        this.println(this.getIndent() + 1, status.toString());
        this.setIndent(1);
    }

    public void reportDodge(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pReport.getRoll() > 0) {
            status.append("Dodge Roll [ ").append(pReport.getRoll()).append(" ]");
        } else {
            status.append("New Dodge Result");
        }
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        if (!pReport.isReRolled()) {
            if (pReport.hasRollModifier(DodgeModifier.STUNTY)) {
                this.print(this.getIndent() + 1, false, actingPlayer.getPlayer());
                this.println(this.getIndent() + 1, " is Stunty and ignores tacklezones.");
            }
            if (pReport.hasRollModifier(DodgeModifier.BREAK_TACKLE)) {
                this.print(this.getIndent() + 1, false, actingPlayer.getPlayer());
                this.println(this.getIndent() + 1, " uses Break Tackle to break free.");
            }
        }
        this.print(this.getIndent() + 1, false, actingPlayer.getPlayer());
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" dodges successfully.");
            this.println(this.getIndent() + 1, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 1, " trips while dodging.");
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            if (pReport.hasRollModifier(DodgeModifier.BREAK_TACKLE)) {
                neededRoll.append(" using Break Tackle (ST ").append(Math.min(6, actingPlayer.getStrength()));
            } else {
                neededRoll.append(" (AG ").append(Math.min(6, actingPlayer.getPlayer().getAgility()));
            }
            neededRoll.append(" + 1 Dodge").append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportThrowTeamMateRoll(ReportThrowTeamMateRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player thrower = game.getActingPlayer().getPlayer();
        Player thrownPlayer = game.getPlayerById(pReport.getThrownPlayerId());
        if (!pReport.isReRolled()) {
            this.print(this.getIndent(), true, thrower);
            this.print(this.getIndent(), TextStyle.BOLD, " tries to throw ");
            this.print(this.getIndent(), true, thrownPlayer);
            this.println(this.getIndent(), TextStyle.BOLD, ":");
        }
        if (pReport.hasRollModifier(PassModifier.NERVES_OF_STEEL)) {
            Player player = this.getClient().getGame().getActingPlayer().getPlayer();
            this.reportNervesOfSteel(player, "pass");
        }
        status.append("Throw Team-Mate Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, thrower);
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" throws ").append(thrower.getPlayerGender().getGenitive()).append(" team-mate successfully.");
            this.println(this.getIndent() + 2, status.toString());
        } else {
            this.println(this.getIndent() + 2, " fumbles the throw.");
        }
        if (pReport.isSuccessful() && !pReport.isReRolled() && this.fShowModifiersOnSuccess) {
            neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+ to avoid a fumble");
        }
        if (!pReport.isSuccessful() && !pReport.isReRolled() && this.fShowModifiersOnFailure) {
            neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to avoid a fumble");
        }
        if (neededRoll != null) {
            neededRoll.append(" (Roll ");
            PassingDistance passingDistance = pReport.getPassingDistance();
            if (passingDistance.getModifier() >= 0) {
                neededRoll.append(" + ");
            } else {
                neededRoll.append(" - ");
            }
            neededRoll.append(Math.abs(passingDistance.getModifier())).append(" ").append(passingDistance.getName());
            neededRoll.append(this.formatRollModifiers(pReport.getRollModifiers())).append(" > 1).");
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
        this.setIndent(this.getIndent() + 1);
    }

    public void reportScatterPlayer(ReportScatterPlayer pReport) {
        int[] rolls = pReport.getRolls();
        if (ArrayTool.isProvided(rolls)) {
            StringBuilder status = new StringBuilder();
            if (rolls.length > 1) {
                status.append("Scatter Rolls [ ");
            } else {
                status.append("Scatter Roll [ ");
            }
            for (int i = 0; i < rolls.length; ++i) {
                if (i > 0) {
                    status.append(", ");
                }
                status.append(rolls[i]);
            }
            status.append(" ] ");
            Direction[] directions = pReport.getDirections();
            for (int i = 0; i < directions.length; ++i) {
                if (i > 0) {
                    status.append(", ");
                }
                status.append(directions[i].getName());
            }
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            status = new StringBuilder();
            status.append("Player scatters from square (");
            status.append(pReport.getStartCoordinate().getX()).append(",").append(pReport.getStartCoordinate().getY());
            status.append(") to square (");
            status.append(pReport.getEndCoordinate().getX()).append(",").append(pReport.getEndCoordinate().getY());
            status.append(").");
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportAlwaysHungry(ReportSkillRoll pReport) {
        Game game = this.getClient().getGame();
        Player thrower = game.getActingPlayer().getPlayer();
        StringBuilder status = new StringBuilder();
        status.append("Always Hungry Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, thrower);
        status = new StringBuilder();
        if (pReport.isSuccessful()) {
            status.append(" resists the hunger.");
        } else {
            status.append(" tries to eat ").append(thrower.getPlayerGender().getGenitive()).append(" team-mate.");
        }
        this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
    }

    public void reportArgueTheCall(ReportArgueTheCallRoll report) {
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(report.getPlayerId());
        StringBuilder status = new StringBuilder();
        status.append("Argue the Call Roll [ ").append(report.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        if (report.isSuccessful()) {
            this.print(this.getIndent() + 1, TextStyle.NONE, "The ref refrains from banning ");
            this.print(this.getIndent() + 1, false, player);
            status = new StringBuilder();
            status.append(" and ").append(player.getPlayerGender().getNominative()).append(" is sent to the reserve instead.");
            this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
        } else {
            this.print(this.getIndent() + 1, TextStyle.NONE, "The ref bans ");
            this.print(this.getIndent() + 1, false, player);
            this.println(this.getIndent() + 1, TextStyle.NONE, " from the game.");
        }
        if (report.isCoachBanned()) {
            this.print(this.getIndent() + 1, TextStyle.NONE, "Coach ");
            if (game.getTeamHome().hasPlayer(player)) {
                this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getCoach());
            } else {
                this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getCoach());
            }
            this.println(this.getIndent() + 1, TextStyle.NONE, " is also banned from the game.");
        }
    }

    public void reportBribes(ReportBribesRoll report) {
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(report.getPlayerId());
        StringBuilder status = new StringBuilder();
        status.append("Bribes Roll [ ").append(report.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        if (report.isSuccessful()) {
            this.print(this.getIndent() + 1, TextStyle.NONE, "The ref refrains from penalizing ");
            this.print(this.getIndent() + 1, false, player);
            status = new StringBuilder();
            status.append(" and ").append(player.getPlayerGender().getNominative()).append(" remains in the game.");
            this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
        } else {
            this.print(this.getIndent() + 1, TextStyle.NONE, "The ref appears to be unimpressed and ");
            this.print(this.getIndent() + 1, false, player);
            this.println(this.getIndent() + 1, TextStyle.NONE, " must leave the game.");
        }
    }

    public void reportEscape(ReportSkillRoll pReport) {
        Game game = this.getClient().getGame();
        Player thrownPlayer = game.getPlayerById(pReport.getPlayerId());
        StringBuilder status = new StringBuilder();
        status.append("Escape Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        this.print(this.getIndent() + 1, false, thrownPlayer);
        if (pReport.isSuccessful()) {
            status.append(" manages to wriggle free.");
        } else {
            status.append(" disappears in ").append(thrownPlayer.getPlayerGender().getGenitive()).append(" team-mate's stomach.");
        }
        this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
    }

    public void reportLeap(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Leap Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" leaps over ").append(player.getPlayerGender().getGenitive()).append(" opponents.");
            this.println(this.getIndent() + 1, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 1, " trips while leaping.");
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportBiteSpectator(ReportBiteSpectator pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pReport.getPlayerId());
        if (player != null) {
            this.print(this.getIndent(), true, player);
            this.println(this.getIndent(), TextStyle.BOLD, " heads off to the spectator ranks to bite some beautiful maiden.");
        }
    }

    public void reportJumpUp(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Jump Up Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" jumps up to block ").append(player.getPlayerGender().getGenitive()).append(" opponent.");
            this.println(this.getIndent() + 1, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            status = new StringBuilder();
            status.append(" doesn't get to ").append(player.getPlayerGender().getGenitive()).append(" feet.");
            this.println(this.getIndent() + 1, status.toString());
            status = new StringBuilder();
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportStandUp(ReportStandUpRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Stand Up Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" stands up.");
            this.println(this.getIndent() + 1, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+.");
            }
        } else {
            status = new StringBuilder();
            status.append(" doesn't get to ").append(player.getPlayerGender().getGenitive()).append(" feet.");
            this.println(this.getIndent() + 1, status.toString());
            status = new StringBuilder();
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed.");
            }
        }
        if (neededRoll != null) {
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportSafeThrow(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Safe Throw Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, player);
        if (pReport.isSuccessful()) {
            this.println(this.getIndent() + 2, " throws safely over any interceptors.");
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 2, "'s Safe Throw fails to stop the interception.");
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(" + Roll > 6).");
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportBloodLust(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Blood Lust Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            this.println(this.getIndent() + 1, " resists the Blood Lust.");
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 1, " gives in to the Blood Lust.");
            status = new StringBuilder();
            this.println(this.getIndent() + 1, "Player must feed at the end of the action or leave the pitch and suffer a turnover.");
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportCardEffectRoll(ReportCardEffectRoll pReport) {
        StringBuilder status = new StringBuilder();
        if (Card.WITCH_BREW == pReport.getCard()) {
            status.append("Witch Brew Roll [ ").append(pReport.getRoll()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            if (CardEffect.SEDATIVE == pReport.getCardEffect()) {
                this.println(this.getIndent() + 1, "Sedative! The player gains the Really Stupid skill until the drive ends.");
            } else if (CardEffect.MAD_CAP_MUSHROOM_POTION == pReport.getCardEffect()) {
                this.println(this.getIndent() + 1, "Mad Cap Mushroom potion! The player gains the Jump Up and No Hands skills until the drive ends.");
            } else {
                this.println(this.getIndent() + 1, "Snake Oil! Bad taste, but no effect.");
            }
        }
    }

    public void reportAnimosity(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        status.append("Animosity Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 1, false, player);
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" resists ").append(player.getPlayerGender().getGenitive()).append(" Animosity.");
            this.println(this.getIndent() + 1, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            status = new StringBuilder();
            status.append(" gives in to ").append(player.getPlayerGender().getGenitive()).append(" Animosity.");
            this.println(this.getIndent() + 1, status.toString());
            status = new StringBuilder();
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportRightStuff(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        status.append("Right Stuff Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        Player thrownPlayer = game.getPlayerById(pReport.getPlayerId());
        this.print(this.getIndent() + 1, false, thrownPlayer);
        if (pReport.isSuccessful()) {
            status = new StringBuilder();
            status.append(" lands on ").append(thrownPlayer.getPlayerGender().getGenitive()).append(" feet.");
            this.println(this.getIndent() + 1, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 1, " crashes to the ground.");
            status = new StringBuilder();
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, thrownPlayer.getAgility())).append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportConfusion(ReportConfusionRoll pReport) {
        if (pReport.getConfusionSkill() != null) {
            StringBuilder status = new StringBuilder();
            StringBuilder neededRoll = null;
            Game game = this.getClient().getGame();
            Player player = game.getActingPlayer().getPlayer();
            status.append(pReport.getConfusionSkill().getName()).append(" Roll [ ").append(pReport.getRoll()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 1, false, player);
            if (pReport.isSuccessful()) {
                this.println(this.getIndent() + 1, " is able to act normally.");
                if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                    neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
                }
            } else {
                if (Skill.WILD_ANIMAL == pReport.getConfusionSkill()) {
                    this.println(this.getIndent() + 1, " roars in rage.");
                } else if (Skill.TAKE_ROOT == pReport.getConfusionSkill()) {
                    this.println(this.getIndent() + 1, " takes root.");
                } else {
                    this.println(this.getIndent() + 1, " is confused.");
                }
                status = new StringBuilder();
                if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                    neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
                }
            }
            if (neededRoll != null) {
                if (Skill.WILD_ANIMAL == pReport.getConfusionSkill()) {
                    if (pReport.getMinimumRoll() > 2) {
                        neededRoll.append(" (Wild Animal does not attack)");
                    } else {
                        neededRoll.append(" (Wild Animal does attack)");
                    }
                }
                if (Skill.REALLY_STUPID == pReport.getConfusionSkill()) {
                    if (pReport.getMinimumRoll() > 2) {
                        neededRoll.append(" (Really Stupid player without assistance)");
                    } else {
                        neededRoll.append(" (Really Stupid player gets help from team-mates)");
                    }
                }
                neededRoll.append(".");
                this.println(this.getIndent() + 1, TextStyle.NEEDED_ROLL, neededRoll.toString());
            }
        }
    }

    public void reportCatch(ReportCatchRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pReport.getPlayerId());
        if (!pReport.isReRolled()) {
            this.print(this.getIndent(), true, player);
            if (pReport.isBomb()) {
                this.println(this.getIndent(), TextStyle.BOLD, " tries to catch the bomb:");
            } else {
                this.println(this.getIndent(), TextStyle.BOLD, " tries to catch the ball:");
            }
            if (pReport.hasRollModifier(CatchModifier.NERVES_OF_STEEL)) {
                this.reportNervesOfSteel(player, "catch");
            }
        }
        status.append("Catch Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, player);
        if (pReport.isSuccessful()) {
            if (pReport.isBomb()) {
                this.println(this.getIndent() + 2, " catches the bomb.");
            } else {
                this.println(this.getIndent() + 2, " catches the ball.");
            }
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            if (pReport.isBomb()) {
                this.println(this.getIndent() + 2, " drops the bomb.");
            } else {
                this.println(this.getIndent() + 2, " drops the ball.");
            }
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportInterception(ReportInterceptionRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pReport.getPlayerId());
        if (!pReport.isReRolled()) {
            this.print(this.getIndent(), true, player);
            if (pReport.isBomb()) {
                this.println(this.getIndent(), TextStyle.BOLD, " tries to intercept the bomb:");
            } else {
                this.println(this.getIndent(), TextStyle.BOLD, " tries to intercept the ball:");
            }
            if (pReport.hasRollModifier(InterceptionModifier.NERVES_OF_STEEL)) {
                this.reportNervesOfSteel(player, "intercept");
            }
        }
        status.append("Interception Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, player);
        if (pReport.isSuccessful()) {
            if (pReport.isBomb()) {
                this.println(this.getIndent() + 2, " intercepts the bomb.");
            } else {
                this.println(this.getIndent() + 2, " intercepts the ball.");
            }
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            if (pReport.isBomb()) {
                this.println(this.getIndent() + 2, " fails to intercept the bomb.");
            } else {
                this.println(this.getIndent() + 2, " fails to intercept the ball.");
            }
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(" - 2 Interception").append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportHypnoticGaze(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        if (!pReport.isReRolled()) {
            this.print(this.getIndent(), true, player);
            this.print(this.getIndent(), TextStyle.BOLD, " gazes upon ");
            this.print(this.getIndent(), true, game.getDefender());
            this.println(this.getIndent(), TextStyle.BOLD, ":");
        }
        status.append("Hypnotic Gaze Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, player);
        status = new StringBuilder();
        if (pReport.isSuccessful()) {
            status.append(" hypnotizes ").append(player.getPlayerGender().getGenitive()).append(" victim.");
            this.println(this.getIndent() + 2, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            status.append(" fails to affect ").append(player.getPlayerGender().getGenitive()).append(" victim.");
            this.println(this.getIndent() + 2, status.toString());
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportPickup(ReportSkillRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player player = game.getActingPlayer().getPlayer();
        if (!pReport.isReRolled()) {
            this.print(this.getIndent(), true, player);
            this.println(this.getIndent(), TextStyle.BOLD, " tries to pick up the ball:");
            if (pReport.hasRollModifier(PickupModifier.BIG_HAND)) {
                this.print(this.getIndent() + 1, false, player);
                this.println(this.getIndent() + 1, " is using Big Hand to ignore any tacklezones on the ball.");
            }
        }
        status.append("Pickup Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, player);
        if (pReport.isSuccessful()) {
            this.println(this.getIndent() + 2, " picks up the ball.");
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            this.println(this.getIndent() + 2, " drops the ball.");
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            neededRoll.append(" (AG ").append(Math.min(6, player.getAgility())).append(" + 1 Pickup").append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportTentaclesShadowingRoll(ReportTentaclesShadowingRoll pReport) {
        StringBuilder status = null;
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        Player defender = game.getPlayerById(pReport.getDefenderId());
        if (!pReport.isReRolled()) {
            if (pReport.getSkill() == Skill.SHADOWING) {
                this.print(this.getIndent(), true, defender);
                this.print(this.getIndent(), TextStyle.BOLD, " tries to shadow ");
                this.print(this.getIndent(), true, actingPlayer.getPlayer());
                this.println(this.getIndent(), TextStyle.BOLD, ":");
            }
            if (pReport.getSkill() == Skill.TENTACLES) {
                status = new StringBuilder();
                this.print(this.getIndent(), true, defender);
                this.print(this.getIndent(), TextStyle.BOLD, " tries to hold ");
                this.print(this.getIndent(), true, actingPlayer.getPlayer());
                status.append(" with ").append(defender.getPlayerGender().getGenitive()).append(" tentacles:");
                this.println(this.getIndent(), TextStyle.BOLD, status.toString());
            }
        }
        int rolledTotal = 0;
        if (ArrayTool.isProvided(pReport.getRoll())) {
            rolledTotal = pReport.getRoll()[0] + pReport.getRoll()[1];
        }
        if (pReport.getSkill() == Skill.SHADOWING) {
            if (rolledTotal > 0) {
                status = new StringBuilder();
                status.append("Shadowing Escape Roll [ ").append(pReport.getRoll()[0]).append(" ][ ").append(pReport.getRoll()[1]).append(" ] = ").append(rolledTotal);
                this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
            }
            status = new StringBuilder();
            if (pReport.isSuccessful()) {
                this.print(this.getIndent() + 2, false, actingPlayer.getPlayer());
                status.append(" escapes ").append(actingPlayer.getPlayer().getPlayerGender().getGenitive()).append(" opponent.");
                this.println(this.getIndent() + 2, status.toString());
                if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                    neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
                }
            } else {
                this.print(this.getIndent() + 2, false, defender);
                status.append(" shadows ").append(defender.getPlayerGender().getGenitive()).append(" opponent successfully.");
                this.println(this.getIndent() + 2, status.toString());
                if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                    neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
                }
            }
            if (neededRoll != null) {
                neededRoll.append(" (MA ").append(UtilCards.getPlayerMovement(game, actingPlayer.getPlayer()));
                neededRoll.append(" - MA ").append(UtilCards.getPlayerMovement(game, defender));
                neededRoll.append(" + Roll > 7).");
                this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
            }
        }
        if (pReport.getSkill() == Skill.TENTACLES) {
            if (rolledTotal > 0) {
                status = new StringBuilder();
                status.append("Tentacles Escape Roll [ ").append(pReport.getRoll()[0]).append(" ][ ").append(pReport.getRoll()[1]).append(" ] = ").append(rolledTotal);
                this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
            }
            status = new StringBuilder();
            if (pReport.isSuccessful()) {
                this.print(this.getIndent() + 2, false, actingPlayer.getPlayer());
                status.append(" escapes ").append(actingPlayer.getPlayer().getPlayerGender().getGenitive()).append(" opponent.");
                this.println(this.getIndent() + 2, status.toString());
                if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                    neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
                }
            } else {
                this.print(this.getIndent() + 2, false, defender);
                status.append(" holds ").append(defender.getPlayerGender().getGenitive()).append(" opponent successfully.");
                this.println(this.getIndent() + 2, status.toString());
                if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                    neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
                }
            }
            if (neededRoll != null) {
                neededRoll.append(" (ST ").append(actingPlayer.getStrength());
                neededRoll.append(" - ST ").append(UtilCards.getPlayerStrength(game, defender));
                neededRoll.append(" + Roll > 5).");
                this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
            }
        }
    }

    public void reportWeather(ReportWeather pReport) {
        int[] roll = pReport.getWeatherRoll();
        StringBuilder status = new StringBuilder();
        status.append("Weather Roll [ ").append(roll[0]).append(" ][ ").append(roll[1]).append(" ] ");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        Weather weather = pReport.getWeather();
        status = new StringBuilder();
        status.append("Weather is ").append(weather.getName());
        this.println(this.getIndent() + 1, status.toString());
        this.println(this.getIndent() + 1, TextStyle.EXPLANATION, weather.getDescription());
    }

    public void reportCoinThrow(ReportCoinThrow pReport) {
        this.setIndent(0);
        Game game = this.getClient().getGame();
        this.println(this.getIndent(), TextStyle.BOLD, "The referee throws the coin.");
        this.print(this.getIndent() + 1, "Coach ");
        if (game.getTeamHome().getCoach().equals(pReport.getCoach())) {
            this.print(this.getIndent() + 1, TextStyle.HOME, pReport.getCoach());
        } else {
            this.print(this.getIndent() + 1, TextStyle.AWAY, pReport.getCoach());
        }
        StringBuilder status = new StringBuilder();
        status.append(" chooses ").append(pReport.isCoinChoiceHeads() ? "HEADS." : "TAILS.");
        this.println(this.getIndent() + 1, status.toString());
        status = new StringBuilder();
        status.append("Coin throw is ");
        status.append(pReport.isCoinThrowHeads() ? "HEADS." : "TAILS.");
        this.println(this.getIndent() + 1, status.toString());
    }

    public void reportReceiveChoice(ReportReceiveChoice pReport) {
        Game game = this.getClient().getGame();
        this.print(this.getIndent() + 1, "Team ");
        this.printTeamName(game, false, pReport.getTeamId());
        StringBuilder status = new StringBuilder();
        status.append(" is ").append(pReport.isReceiveChoice() ? "receiving." : "kicking.");
        this.println(this.getIndent() + 1, status.toString());
    }

    public void reportPlayCard(ReportPlayCard pReport) {
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        status.append("Card ").append(pReport.getCard().getName());
        if (StringTool.isProvided(pReport.getPlayerId())) {
            status.append(" is played on ");
        } else {
            status.append(" is played.");
        }
        this.print(this.getIndent(), TextStyle.BOLD, status.toString());
        if (StringTool.isProvided(pReport.getPlayerId())) {
            Player player = game.getPlayerById(pReport.getPlayerId());
            this.print(this.getIndent(), true, player);
            this.println(this.getIndent(), TextStyle.BOLD, ".");
        } else {
            this.println();
        }
    }

    public void reportCardDeactivated(ReportCardDeactivated pReport) {
        StringBuilder status = new StringBuilder();
        status.append("Card ").append(pReport.getCard().getName());
        status.append(" effect ended.");
        this.println(this.getIndent(), TextStyle.BOLD, status.toString());
    }

    public void reportHandOver(ReportHandOver pReport) {
        Game game = this.getClient().getGame();
        Player thrower = game.getActingPlayer().getPlayer();
        Player catcher = game.getPlayerById(pReport.getCatcherId());
        this.print(this.getIndent(), true, thrower);
        this.print(this.getIndent(), TextStyle.BOLD, " hands over the ball to ");
        this.print(this.getIndent(), true, catcher);
        this.println(this.getIndent(), TextStyle.BOLD, ":");
    }

    public void reportPass(ReportPassRoll pReport) {
        StringBuilder status = new StringBuilder();
        StringBuilder neededRoll = null;
        Game game = this.getClient().getGame();
        Player thrower = game.getPlayerById(pReport.getPlayerId());
        if (!pReport.isReRolled()) {
            this.print(this.getIndent(), true, thrower);
            Player catcher = game.getFieldModel().getPlayer(game.getPassCoordinate());
            if (pReport.isHailMaryPass()) {
                if (pReport.isBomb()) {
                    this.println(this.getIndent(), TextStyle.BOLD, " throws a Hail Mary bomb:");
                } else {
                    this.println(this.getIndent(), TextStyle.BOLD, " throws a Hail Mary pass:");
                }
            } else if (catcher != null) {
                if (pReport.isBomb()) {
                    this.print(this.getIndent(), TextStyle.BOLD, " throws a bomb at ");
                } else {
                    this.print(this.getIndent(), TextStyle.BOLD, " passes the ball to ");
                }
                this.print(this.getIndent(), true, catcher);
                this.println(this.getIndent(), TextStyle.BOLD, ":");
            } else if (pReport.isBomb()) {
                this.println(this.getIndent(), TextStyle.BOLD, " throws a bomb to an empty field:");
            } else {
                this.println(this.getIndent(), TextStyle.BOLD, " passes the ball to an empty field:");
            }
        }
        if (pReport.hasRollModifier(PassModifier.NERVES_OF_STEEL)) {
            Player player = this.getClient().getGame().getActingPlayer().getPlayer();
            this.reportNervesOfSteel(player, "pass");
        }
        status.append("Pass Roll [ ").append(pReport.getRoll()).append(" ]");
        this.println(this.getIndent() + 1, TextStyle.ROLL, status.toString());
        this.print(this.getIndent() + 2, false, thrower);
        if (pReport.isSuccessful()) {
            if (pReport.isBomb()) {
                this.println(this.getIndent() + 2, " throws the bomb successfully.");
            } else {
                this.println(this.getIndent() + 2, " passes the ball.");
            }
            if (!pReport.isReRolled() && this.fShowModifiersOnSuccess) {
                neededRoll = new StringBuilder().append("Succeeded on a roll of ").append(pReport.getMinimumRoll()).append("+");
            }
        } else {
            if (pReport.isHeldBySafeThrow()) {
                this.println(this.getIndent() + 2, " holds on to the ball.");
            } else if (pReport.isFumble()) {
                if (pReport.isBomb()) {
                    this.println(this.getIndent() + 2, " fumbles the bomb.");
                } else {
                    this.println(this.getIndent() + 2, " fumbles the ball.");
                }
            } else {
                this.println(this.getIndent() + 2, " misses the throw.");
            }
            if (!pReport.isReRolled() && this.fShowModifiersOnFailure) {
                neededRoll = new StringBuilder().append("Roll a ").append(pReport.getMinimumRoll()).append("+ to succeed");
            }
        }
        if (neededRoll != null) {
            if (!pReport.isHailMaryPass()) {
                neededRoll.append(" (AG").append(Math.min(6, thrower.getAgility()));
                PassingDistance passingDistance = pReport.getPassingDistance();
                if (passingDistance.getModifier() >= 0) {
                    neededRoll.append(" + ");
                } else {
                    neededRoll.append(" - ");
                }
                neededRoll.append(Math.abs(passingDistance.getModifier())).append(" ").append(passingDistance.getName());
                neededRoll.append(this.formatRollModifiers(pReport.getRollModifiers())).append(" + Roll > 6).");
            }
            this.println(this.getIndent() + 2, TextStyle.NEEDED_ROLL, neededRoll.toString());
        }
    }

    public void reportGameEnd() {
        this.setIndent(0);
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        int scoreDiffHome = gameResult.getTeamResultHome().getScore() - gameResult.getTeamResultAway().getScore();
        StringBuilder status = new StringBuilder();
        if (gameResult.getTeamResultHome().hasConceded()) {
            status.append("Coach ").append(game.getTeamHome().getCoach()).append(" concedes the game.");
            this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_HOME, status.toString());
        } else if (gameResult.getTeamResultAway().hasConceded()) {
            status.append("Coach ").append(game.getTeamAway().getCoach()).append(" concedes the game.");
            this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_AWAY, status.toString());
        } else if (scoreDiffHome > 0) {
            status.append(game.getTeamHome().getName()).append(" win the game.");
            this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_HOME, status.toString());
        } else if (scoreDiffHome < 0) {
            status.append(game.getTeamAway().getName()).append(" win the game.");
            this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_AWAY, status.toString());
        } else {
            status.append("The game ends in a tie.");
            this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN, status.toString());
        }
    }

    public void reportMostValuablePlayers(ReportMostValuablePlayers pReport) {
        Player player;
        this.reportGameEnd();
        Game game = this.getClient().getGame();
        this.println(this.getIndent(), TextStyle.BOLD, "Most Valuable Players");
        for (String playerId : pReport.getPlayerIdsHome()) {
            player = game.getPlayerById(playerId);
            this.print(this.getIndent() + 1, TextStyle.NONE, "The jury voted ");
            this.print(this.getIndent() + 1, TextStyle.HOME, player.getName());
            this.print(this.getIndent() + 1, TextStyle.NONE, " the most valuable player of ");
            this.print(this.getIndent() + 1, TextStyle.NONE, player.getPlayerGender().getGenitive());
            this.println(this.getIndent() + 1, TextStyle.NONE, " team.");
        }
        for (String playerId : pReport.getPlayerIdsAway()) {
            player = game.getPlayerById(playerId);
            this.print(this.getIndent() + 1, TextStyle.NONE, "The jury voted ");
            this.print(this.getIndent() + 1, TextStyle.AWAY, player.getName());
            this.print(this.getIndent() + 1, TextStyle.NONE, " the most valuable player of ");
            this.print(this.getIndent() + 1, TextStyle.NONE, player.getPlayerGender().getGenitive());
            this.println(this.getIndent() + 1, TextStyle.NONE, " team.");
        }
    }

    public void reportWinningsRoll(ReportWinningsRoll pReport) {
        StringBuilder status;
        Game game = this.getClient().getGame();
        if (pReport.getWinningsRollAway() == 0 && pReport.getWinningsRollHome() > 0) {
            this.print(this.getIndent(), TextStyle.NONE, "Coach ");
            this.print(this.getIndent(), TextStyle.HOME, game.getTeamHome().getCoach());
            this.println(this.getIndent(), TextStyle.NONE, " re-rolls winnings.");
        }
        if (pReport.getWinningsRollHome() == 0 && pReport.getWinningsRollAway() > 0) {
            this.print(this.getIndent(), TextStyle.NONE, "Coach ");
            this.print(this.getIndent(), TextStyle.AWAY, game.getTeamAway().getCoach());
            this.println(this.getIndent(), TextStyle.NONE, " re-rolls winnings.");
        }
        if (pReport.getWinningsRollHome() > 0) {
            status = new StringBuilder();
            status.append("Winnings Roll Home Team [ ").append(pReport.getWinningsRollHome()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
            status = new StringBuilder();
            status.append(" earn ").append(StringTool.formatThousands(pReport.getWinningsHome())).append(" goldcoins.");
            this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
        }
        if (pReport.getWinningsRollAway() > 0) {
            status = new StringBuilder();
            status.append("Winnings Roll Away Team [ ").append(pReport.getWinningsRollAway()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
            status = new StringBuilder();
            status.append(" earn ").append(StringTool.formatThousands(pReport.getWinningsAway())).append(" in gold.");
            this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
        }
        if (pReport.getWinningsRollHome() == 0 && pReport.getWinningsRollAway() == 0) {
            if (pReport.getWinningsHome() > 0) {
                this.println(this.getIndent(), TextStyle.BOLD, "Winnings: Concession of Away Team");
                this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
                this.print(this.getIndent() + 1, TextStyle.NONE, " win ");
                this.print(this.getIndent() + 1, TextStyle.NONE, Integer.toString(pReport.getWinningsHome()));
                this.println(this.getIndent() + 1, TextStyle.NONE, " in gold.");
                this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
                this.println(this.getIndent() + 1, TextStyle.NONE, " get nothing.");
            }
            if (pReport.getWinningsAway() > 0) {
                this.println(this.getIndent(), TextStyle.BOLD, "Winnings: Concession of Home Team");
                this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
                this.print(this.getIndent() + 1, TextStyle.NONE, " win ");
                this.print(this.getIndent() + 1, TextStyle.NONE, Integer.toString(pReport.getWinningsAway()));
                this.println(this.getIndent() + 1, TextStyle.NONE, " in gold.");
                this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
                this.println(this.getIndent() + 1, TextStyle.NONE, " get nothing.");
            }
        }
    }

    public void reportFanFactorRoll(ReportFanFactorRoll pReport) {
        int i;
        Game game = this.getClient().getGame();
        StringBuilder status = new StringBuilder();
        if (ArrayTool.isProvided(pReport.getFanFactorRollHome())) {
            status.append("Fan Factor Roll Home Team ");
            int[] fanFactorRollHome = pReport.getFanFactorRollHome();
            for (i = 0; i < fanFactorRollHome.length; ++i) {
                status.append("[ ").append(fanFactorRollHome[i]).append(" ]");
            }
        } else {
            status.append("Fan Factor: Concession of Home Team");
        }
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        status.append("FanFactor ").append(game.getTeamHome().getFanFactor());
        if (pReport.getFanFactorModifierHome() < 0) {
            status.append(" - ").append(Math.abs(pReport.getFanFactorModifierHome()));
        } else {
            status.append(" + ").append(pReport.getFanFactorModifierHome());
        }
        status.append(" = ").append(game.getTeamHome().getFanFactor() + pReport.getFanFactorModifierHome());
        this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
        this.print(this.getIndent() + 1, TextStyle.HOME, game.getTeamHome().getName());
        if (pReport.getFanFactorModifierHome() > 0) {
            this.println(this.getIndent() + 1, TextStyle.NONE, " win some new fans.");
        } else if (pReport.getFanFactorModifierHome() < 0) {
            this.println(this.getIndent() + 1, TextStyle.NONE, " lose some fans.");
        } else {
            this.println(this.getIndent() + 1, TextStyle.NONE, " keep their fans.");
        }
        status = new StringBuilder();
        if (ArrayTool.isProvided(pReport.getFanFactorRollAway())) {
            status.append("Fan Factor Roll Away Team ");
            int[] fanFactorRollAway = pReport.getFanFactorRollAway();
            for (i = 0; i < fanFactorRollAway.length; ++i) {
                status.append("[ ").append(fanFactorRollAway[i]).append(" ]");
            }
        } else {
            status.append("Fan Factor: Concession of Away Team");
        }
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        status = new StringBuilder();
        status.append("FanFactor ").append(game.getTeamAway().getFanFactor());
        if (pReport.getFanFactorModifierAway() < 0) {
            status.append(" - ").append(Math.abs(pReport.getFanFactorModifierAway()));
        } else {
            status.append(" + ").append(pReport.getFanFactorModifierAway());
        }
        status.append(" = ").append(game.getTeamAway().getFanFactor() + pReport.getFanFactorModifierAway());
        this.println(this.getIndent() + 1, TextStyle.NONE, status.toString());
        this.print(this.getIndent() + 1, TextStyle.AWAY, game.getTeamAway().getName());
        if (pReport.getFanFactorModifierAway() > 0) {
            this.println(this.getIndent() + 1, TextStyle.NONE, " win some new fans.");
        } else if (pReport.getFanFactorModifierAway() < 0) {
            this.println(this.getIndent() + 1, TextStyle.NONE, " lose some fans.");
        } else {
            this.println(this.getIndent() + 1, TextStyle.NONE, " keep their fans.");
        }
    }

    public void reportNoPlayersToField(ReportNoPlayersToField pReport) {
        this.setIndent(0);
        Game game = this.getClient().getGame();
        if (StringTool.isProvided(pReport.getTeamId())) {
            StringBuilder status = new StringBuilder();
            if (game.getTeamHome().getId().equals(pReport.getTeamId())) {
                status.append(game.getTeamHome().getName()).append(" can field no players.");
                this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_HOME, status.toString());
            } else {
                status.append(game.getTeamAway().getName()).append(" can field no players.");
                this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_AWAY, status.toString());
            }
        } else {
            this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN, "Both teams can field no players.");
        }
        if (StringTool.isProvided(pReport.getTeamId())) {
            this.println(this.getIndent(), TextStyle.BOLD, "The opposing team is awarded a touchdown.");
        }
        this.println(ParagraphStyle.SPACE_BELOW, TextStyle.BOLD, "The turn counter is advanced 2 steps.");
    }

    public void reportTurnEnd(ReportTurnEnd pReport) {
        KnockoutRecovery[] knockoutRecoveries;
        HeatExhaustion[] heatExhaustions;
        this.setIndent(0);
        Game game = this.getClient().getGame();
        Player touchdownPlayer = game.getPlayerById(pReport.getPlayerIdTouchdown());
        if (touchdownPlayer != null) {
            this.print(this.getIndent(), true, touchdownPlayer);
            this.println(this.getIndent() + 1, TextStyle.BOLD, " scores a touchdown.");
        }
        if (ArrayTool.isProvided(knockoutRecoveries = pReport.getKnockoutRecoveries())) {
            for (KnockoutRecovery knockoutRecovery : knockoutRecoveries) {
                StringBuilder status = new StringBuilder();
                status.append("Knockout Recovery Roll [ ").append(knockoutRecovery.getRoll()).append(" ] ");
                if (knockoutRecovery.getBloodweiserBabes() > 0) {
                    status.append(" + ").append(knockoutRecovery.getBloodweiserBabes()).append(" Bloodweiser Babes");
                }
                this.println(this.getIndent(), TextStyle.ROLL, status.toString());
                Player player = game.getPlayerById(knockoutRecovery.getPlayerId());
                this.print(this.getIndent() + 1, false, player);
                if (knockoutRecovery.isRecovering()) {
                    this.println(this.getIndent() + 1, " is regaining consciousness.");
                    continue;
                }
                this.println(this.getIndent() + 1, " stays unconscious.");
            }
        }
        if (ArrayTool.isProvided(heatExhaustions = pReport.getHeatExhaustions())) {
            for (HeatExhaustion heatExhaustion : heatExhaustions) {
                StringBuilder status = new StringBuilder();
                status.append("Heat Exhaustion Roll [ ").append(heatExhaustion.getRoll()).append(" ] ");
                this.println(this.getIndent(), TextStyle.ROLL, status.toString());
                Player player = game.getPlayerById(heatExhaustion.getPlayerId());
                this.print(this.getIndent() + 1, false, player);
                if (heatExhaustion.isExhausted()) {
                    this.println(this.getIndent() + 1, " is suffering from heat exhaustion.");
                    continue;
                }
                this.println(this.getIndent() + 1, " is unaffected.");
            }
        }
        if (TurnMode.REGULAR == game.getTurnMode()) {
            StringBuilder status = new StringBuilder();
            if (game.isHomePlaying()) {
                status.append(game.getTeamHome().getName()).append(" start turn ").append(game.getTurnDataHome().getTurnNr()).append(".");
                this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_HOME, status.toString());
            } else {
                status.append(game.getTeamAway().getName()).append(" start turn ").append(game.getTurnDataAway().getTurnNr()).append(".");
                this.println(ParagraphStyle.SPACE_ABOVE_BELOW, TextStyle.TURN_AWAY, status.toString());
            }
        }
    }

    public void reportBlock(ReportBlock pReport) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        Player attacker = actingPlayer.getPlayer();
        Player defender = game.getPlayerById(pReport.getDefenderId());
        this.print(this.getIndent(), true, attacker);
        if (actingPlayer.getPlayerAction() == PlayerAction.BLITZ) {
            this.print(this.getIndent(), TextStyle.BOLD, " blitzes ");
        } else {
            this.print(this.getIndent(), TextStyle.BOLD, " blocks ");
        }
        this.print(this.getIndent(), true, defender);
        this.println(this.getIndent(), TextStyle.BOLD, ":");
        this.setIndent(this.getIndent() + 1);
    }

    public void reportBlockRoll(ReportBlockRoll pReport) {
        if (ArrayTool.isProvided(pReport.getBlockRoll())) {
            StringBuilder status = new StringBuilder();
            status.append("Block Roll");
            BlockResultFactory blockResultFactory = new BlockResultFactory();
            for (int i = 0; i < pReport.getBlockRoll().length; ++i) {
                BlockResult blockResult = blockResultFactory.forRoll(pReport.getBlockRoll()[i]);
                status.append(" [ ").append(blockResult.getName()).append(" ]");
            }
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        }
    }

    public void reportBlockChoice(ReportBlockChoice pReport) {
        StringBuilder status = new StringBuilder();
        status.append("Block Result [ ").append(pReport.getBlockResult().getName()).append(" ]");
        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
        Game game = this.getClient().getGame();
        Player attacker = game.getActingPlayer().getPlayer();
        Player defender = game.getPlayerById(pReport.getDefenderId());
        switch (pReport.getBlockResult()) {
            case BOTH_DOWN: {
                if (UtilCards.hasSkill(game, attacker, Skill.BLOCK)) {
                    this.print(this.getIndent() + 1, false, attacker);
                    status = new StringBuilder();
                    status.append(" has been saved by ").append(attacker.getPlayerGender().getGenitive()).append(" Block skill.");
                    this.println(this.getIndent() + 1, status.toString());
                }
                if (!UtilCards.hasSkill(game, defender, Skill.BLOCK)) break;
                this.print(this.getIndent() + 1, false, defender);
                status = new StringBuilder();
                status.append(" has been saved by ").append(defender.getPlayerGender().getGenitive()).append(" Block skill.");
                this.println(this.getIndent() + 1, status.toString());
                break;
            }
            case POW_PUSHBACK: {
                if (!UtilCards.hasSkill(game, defender, Skill.DODGE) || !UtilCards.hasSkill(game, attacker, Skill.TACKLE)) break;
                this.print(this.getIndent() + 1, false, attacker);
                this.println(this.getIndent() + 1, " uses Tackle to bring opponent down.");
                break;
            }
        }
    }

    public void reportPushback(ReportPushback pReport) {
        Game game = this.getClient().getGame();
        int indent = this.getIndent() + 1;
        StringBuilder status = new StringBuilder();
        Player defender = game.getPlayerById(pReport.getDefenderId());
        if (pReport.getPushbackMode() == PushbackMode.SIDE_STEP) {
            this.print(indent, false, defender);
            status.append(" uses Side Step to avoid being pushed.");
            this.println(indent, status.toString());
        }
        if (pReport.getPushbackMode() == PushbackMode.GRAB) {
            ActingPlayer actingPlayer = game.getActingPlayer();
            this.print(indent, false, actingPlayer.getPlayer());
            status.append(" uses Grab to place ").append(actingPlayer.getPlayer().getPlayerGender().getGenitive()).append(" opponent.");
            this.println(indent, status.toString());
        }
    }

    public void reportRegeneration(ReportSkillRoll pReport) {
        if (pReport.getRoll() > 0) {
            StringBuilder status = new StringBuilder();
            status.append("Regeneration Roll [ ").append(pReport.getRoll()).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            Player player = this.getClient().getGame().getPlayerById(pReport.getPlayerId());
            this.print(this.getIndent() + 1, false, player);
            if (pReport.isSuccessful()) {
                this.println(this.getIndent() + 1, " regenerates.");
            } else {
                this.println(this.getIndent() + 1, " does not regenerate.");
            }
        }
    }

    public void reportInjury(ReportInjury pReport) {
        Game game = this.getClient().getGame();
        Player defender = game.getPlayerById(pReport.getDefenderId());
        Player attacker = game.getPlayerById(pReport.getAttackerId());
        StringBuilder status = new StringBuilder();
        switch (pReport.getInjuryType()) {
            case CROWDPUSH: {
                this.print(this.getIndent() + 1, false, defender);
                this.println(this.getIndent() + 1, " is pushed into the crowd.");
                break;
            }
            case STAB: {
                if (attacker != null) {
                    this.print(this.getIndent(), true, attacker);
                    this.print(this.getIndent(), TextStyle.BOLD, " stabs ");
                    this.print(this.getIndent(), true, defender);
                } else {
                    this.print(this.getIndent(), true, defender);
                    this.print(this.getIndent(), TextStyle.BOLD, " is stabbed");
                }
                this.println(this.getIndent(), TextStyle.BOLD, ":");
                this.setIndent(this.getIndent() + 1);
                break;
            }
            case BITTEN: {
                this.print(this.getIndent(), true, attacker);
                this.print(this.getIndent(), TextStyle.BOLD, " bites ");
                this.print(this.getIndent(), true, defender);
                this.println(this.getIndent(), TextStyle.BOLD, ":");
                this.setIndent(this.getIndent() + 1);
                break;
            }
            case BALL_AND_CHAIN: {
                this.print(this.getIndent() + 1, false, defender);
                status.append(" is knocked out by ");
                status.append(defender.getPlayerGender().getGenitive());
                status.append(" own Ball & Chain.");
                this.println(this.getIndent() + 1, status.toString());
                status = new StringBuilder();
                break;
            }
        }
        int[] armorRoll = pReport.getArmorRoll();
        if (ArrayTool.isProvided(armorRoll)) {
            status.append("Armour Roll [ ").append(armorRoll[0]).append(" ][ ").append(armorRoll[1]).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            status = new StringBuilder();
            int rolledTotal = armorRoll[0] + armorRoll[1];
            status.append("Rolled Total of ").append(rolledTotal);
            int armorModifierTotal = 0;
            boolean usingClaws = false;
            for (ArmorModifier armorModifier : pReport.getArmorModifiers()) {
                usingClaws |= armorModifier == ArmorModifier.CLAWS;
                if (armorModifier.getModifier() == 0) continue;
                armorModifierTotal += armorModifier.getModifier();
                if (armorModifier.getModifier() > 0) {
                    status.append(" + ");
                } else {
                    status.append(" - ");
                }
                if (!armorModifier.isFoulAssistModifier()) {
                    status.append(Math.abs(armorModifier.getModifier())).append(" ");
                }
                status.append(armorModifier.getName());
            }
            if (armorModifierTotal != 0) {
                status.append(" = ").append(rolledTotal + armorModifierTotal);
            }
            this.println(this.getIndent() + 1, status.toString());
            if (attacker != null && usingClaws) {
                status = new StringBuilder();
                this.print(this.getIndent() + 1, false, attacker);
                this.println(this.getIndent() + 1, " uses Claws to reduce opponents armour to 7.");
            }
            status = new StringBuilder();
            if (pReport.isArmorBroken()) {
                this.print(this.getIndent() + 1, "The armour of ");
                this.print(this.getIndent() + 1, false, defender);
                this.println(this.getIndent() + 1, " has been broken.");
            } else {
                this.print(this.getIndent() + 1, false, defender);
                status = new StringBuilder();
                status.append(" has been saved by ").append(defender.getPlayerGender().getGenitive()).append(" armour.");
                this.println(this.getIndent() + 1, status.toString());
            }
        }
        if (pReport.isArmorBroken()) {
            boolean thickSkullUsed = false;
            boolean stuntyUsed = false;
            status = new StringBuilder();
            int[] injuryRoll = pReport.getInjuryRoll();
            if (ArrayTool.isProvided(injuryRoll)) {
                status.append("Injury Roll [ ").append(injuryRoll[0]).append(" ][ ").append(injuryRoll[1]).append(" ]");
                this.println(this.getIndent(), TextStyle.ROLL, status.toString());
                status = new StringBuilder();
                int rolledTotal = injuryRoll[0] + injuryRoll[1];
                status.append("Rolled Total of ").append(rolledTotal);
                int injuryModifierTotal = 0;
                for (InjuryModifier injuryModifier : pReport.getInjuryModifiers()) {
                    injuryModifierTotal += injuryModifier.getModifier();
                    if (injuryModifier.getModifier() == 0) {
                        if (injuryModifier == InjuryModifier.THICK_SKULL) {
                            thickSkullUsed = true;
                        }
                        if (injuryModifier != InjuryModifier.STUNTY) continue;
                        stuntyUsed = true;
                        continue;
                    }
                    if (injuryModifier.isNigglingInjuryModifier()) {
                        status.append(" +").append(injuryModifier.getName());
                        continue;
                    }
                    if (injuryModifier.getModifier() > 0) {
                        status.append(" +").append(injuryModifier.getModifier()).append(" ").append(injuryModifier.getName());
                        continue;
                    }
                    status.append(" ").append(injuryModifier.getModifier()).append(" ").append(injuryModifier.getName());
                }
                if (injuryModifierTotal != 0) {
                    status.append(" = ").append(rolledTotal + injuryModifierTotal);
                }
                this.println(this.getIndent() + 1, status.toString());
                if (stuntyUsed) {
                    this.print(this.getIndent() + 1, false, defender);
                    status = new StringBuilder();
                    status.append(" is Stunty and more easily hurt because of that.");
                    this.println(this.getIndent() + 1, status.toString());
                }
                if (thickSkullUsed) {
                    this.print(this.getIndent() + 1, false, defender);
                    status = new StringBuilder();
                    status.append("'s Thick Skull helps ").append(defender.getPlayerGender().getDative()).append(" to stay on the pitch.");
                    this.println(this.getIndent() + 1, status.toString());
                }
                if (ArrayTool.isProvided(pReport.getCasualtyRoll())) {
                    this.print(this.getIndent() + 1, false, defender);
                    this.println(this.getIndent() + 1, " suffers a casualty.");
                    int[] casualtyRoll = pReport.getCasualtyRoll();
                    status = new StringBuilder();
                    status.append("Casualty Roll [ ").append(casualtyRoll[0]).append(" ][ ").append(casualtyRoll[1]).append(" ]");
                    this.println(this.getIndent(), TextStyle.ROLL, status.toString());
                    this.reportInjury(defender, pReport.getInjury(), pReport.getSeriousInjury());
                    if (ArrayTool.isProvided(pReport.getCasualtyRollDecay())) {
                        this.print(this.getIndent() + 1, false, defender);
                        status = new StringBuilder();
                        status.append("'s body is decaying and ").append(defender.getPlayerGender().getNominative()).append(" suffers a 2nd casualty.");
                        this.println(this.getIndent() + 1, status.toString());
                        status = new StringBuilder();
                        int[] casualtyRollDecay = pReport.getCasualtyRollDecay();
                        status.append("Casualty Roll [ ").append(casualtyRollDecay[0]).append(" ][ ").append(casualtyRollDecay[1]).append(" ]");
                        this.println(this.getIndent(), TextStyle.ROLL, status.toString());
                        this.reportInjury(defender, pReport.getInjuryDecay(), pReport.getSeriousInjuryDecay());
                    }
                } else {
                    this.reportInjury(defender, pReport.getInjury(), pReport.getSeriousInjury());
                }
            }
        }
    }

    private void reportInjury(Player pDefender, PlayerState pInjury, SeriousInjury pSeriousInjury) {
        StringBuilder status = new StringBuilder();
        this.print(this.getIndent() + 1, false, pDefender);
        status.append(" ").append(pInjury.getDescription()).append(".");
        this.println(this.getIndent() + 1, status.toString());
        if (pSeriousInjury != null) {
            this.print(this.getIndent() + 1, false, pDefender);
            status = new StringBuilder();
            status.append(" ").append(pSeriousInjury.getDescription()).append(".");
            this.println(this.getIndent() + 1, status.toString());
        }
    }

    public void reportFoul(ReportFoul pReport) {
        Game game = this.getClient().getGame();
        Player attacker = game.getActingPlayer().getPlayer();
        Player defender = game.getPlayerById(pReport.getDefenderId());
        this.print(this.getIndent(), true, attacker);
        this.print(this.getIndent(), TextStyle.BOLD, " fouls ");
        this.print(this.getIndent(), true, defender);
        this.println(this.getIndent(), ":");
        this.setIndent(this.getIndent() + 1);
    }

    private void reportNervesOfSteel(Player pPlayer, String pDoWithTheBall) {
        if (pPlayer != null) {
            this.print(this.getIndent(), false, pPlayer);
            StringBuilder status = new StringBuilder();
            status.append(" is using Nerves of Steel to ").append(pDoWithTheBall).append(" the ball.");
            this.println(this.getIndent(), status.toString());
        }
    }

    public void reportPassBlock(ReportPassBlock pReport) {
        Game game = this.getClient().getGame();
        if (!pReport.isPassBlockAvailable()) {
            TextStyle textStyle = game.getTeamHome().getId().equals(pReport.getTeamId()) ? TextStyle.HOME : TextStyle.AWAY;
            this.println(this.getIndent(), textStyle, "No pass blockers in range to intercept.");
        }
    }

    public void reportPlayerAction(ReportPlayerAction pReport) {
        String actionDescription;
        this.setIndent(0);
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pReport.getActingPlayerId());
        PlayerAction playerAction = pReport.getPlayerAction();
        String string = actionDescription = playerAction != null ? playerAction.getDescription() : null;
        if (player != null && StringTool.isProvided(actionDescription)) {
            this.print(this.getIndent(), true, player);
            StringBuilder status = new StringBuilder();
            status.append(" ").append(actionDescription).append(".");
            this.println(this.getIndent(), TextStyle.BOLD, status.toString());
        }
        this.setIndent(this.getIndent() + 1);
    }

    public void reportApothecaryRoll(ReportApothecaryRoll pReport) {
        int[] casualtyRoll = pReport.getCasualtyRoll();
        if (ArrayTool.isProvided(casualtyRoll)) {
            this.println(this.getIndent(), TextStyle.BOLD, "Apothecary used.");
            Player player = this.getClient().getGame().getPlayerById(pReport.getPlayerId());
            StringBuilder status = new StringBuilder();
            status.append("Casualty Roll [ ").append(casualtyRoll[0]).append(" ][ ").append(casualtyRoll[1]).append(" ]");
            this.println(this.getIndent(), TextStyle.ROLL, status.toString());
            PlayerState injury = pReport.getPlayerState();
            this.print(this.getIndent() + 1, false, player);
            status = new StringBuilder();
            status.append(" ").append(injury.getDescription()).append(".");
            this.println(this.getIndent() + 1, status.toString());
            SeriousInjury seriousInjury = pReport.getSeriousInjury();
            if (seriousInjury != null) {
                this.print(this.getIndent() + 1, false, player);
                status = new StringBuilder();
                status.append(" ").append(seriousInjury.getDescription()).append(".");
                this.println(this.getIndent() + 1, status.toString());
            }
        }
    }

    public void reportApothecaryChoice(ReportApothecaryChoice pReport) {
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        Player player = game.getPlayerById(pReport.getPlayerId());
        if (pReport.getPlayerState() != null && pReport.getPlayerState().getBase() == 9) {
            this.print(this.getIndent(), TextStyle.BOLD, "The apothecary patches ");
            this.print(this.getIndent(), true, player);
            StringBuilder status = new StringBuilder();
            status.append(" up so ").append(player.getPlayerGender().getNominative()).append(" is able to play again.");
            this.println(this.getIndent(), TextStyle.BOLD, status.toString());
        } else {
            this.print(this.getIndent(), "Coach ");
            if (game.getTeamHome().hasPlayer(player)) {
                this.print(this.getIndent(), TextStyle.HOME, game.getTeamHome().getCoach());
            } else {
                this.print(this.getIndent(), TextStyle.AWAY, game.getTeamAway().getCoach());
            }
            PlayerState playerStateOld = game.getFieldModel().getPlayerState(player);
            SeriousInjury seriousInjuryOld = gameResult.getPlayerResult(player).getSeriousInjury();
            if (pReport.getPlayerState() != playerStateOld || pReport.getSeriousInjury() != seriousInjuryOld) {
                this.println(this.getIndent(), " chooses the new injury result.");
            } else {
                this.println(this.getIndent(), " keeps the old injury result.");
            }
        }
    }

    public void reportSkillUse(ReportSkillUse pReport) {
        Game game = this.getClient().getGame();
        if (pReport.getSkill() != null) {
            Player player = game.getPlayerById(pReport.getPlayerId());
            int indent = this.getIndent();
            if (pReport.getSkill() != Skill.KICK) {
                ++indent;
            }
            StringBuilder status = new StringBuilder();
            if (!pReport.isUsed()) {
                if (player != null) {
                    this.print(indent, false, player);
                    status.append(" does not use ").append(pReport.getSkill().getName());
                } else {
                    status.append(pReport.getSkill().getName()).append(" is not used");
                }
                if (pReport.getSkillUse() != null) {
                    status.append(" ").append(pReport.getSkillUse().getDescription(player));
                }
                status.append(".");
                this.println(indent, status.toString());
            } else {
                if (player != null) {
                    this.print(indent, false, player);
                    status.append(" uses ").append(pReport.getSkill().getName());
                } else {
                    status.append(pReport.getSkill().getName()).append(" used");
                }
                if (pReport.getSkillUse() != null) {
                    status.append(" ").append(pReport.getSkillUse().getDescription(player));
                }
                status.append(".");
                this.println(indent, status.toString());
            }
        }
    }

    public void reportPilingOn(ReportPilingOn pReport) {
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pReport.getPlayerId());
        if (player != null) {
            int indent = this.getIndent() + 1;
            this.print(indent, false, player);
            StringBuilder status = new StringBuilder();
            if (!pReport.isUsed()) {
                status.append(" does not use ").append(Skill.PILING_ON.getName()).append(".");
            } else {
                status.append(" uses ").append(Skill.PILING_ON.getName()).append(" to re-roll ");
                status.append(pReport.isReRollInjury() ? "Injury" : "Armor").append(".");
            }
            this.println(indent, status.toString());
        }
    }

    public void reportServerUnreachable() {
        this.println();
        this.println(0, TextStyle.BOLD, "Server unreachable - Communication stopped.");
        this.println();
    }

    public void reportStatus(ServerStatus status) {
        this.println();
        this.println(0, TextStyle.BOLD, status.getMessage());
        this.println();
    }

    public void reportServerMessage(ServerStatus pServerStatus) {
        this.println(this.getIndent(), TextStyle.NONE, pServerStatus.getMessage());
    }

    public void reportReferee(ReportReferee pReport) {
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (pReport.isFoulingPlayerBanned()) {
            this.print(this.getIndent(), "The referee spots the foul and bans ");
            this.print(this.getIndent(), false, actingPlayer.getPlayer());
            this.println(this.getIndent(), " from the game.");
        } else {
            this.println(this.getIndent(), "The referee didn't spot the foul.");
        }
    }

    public void reportPenaltyShootout(ReportPenaltyShootout pReport) {
        int penaltyScoreHome = pReport.getRollHome() + pReport.getReRollsLeftHome();
        this.print(0, TextStyle.ROLL, "Penalty Shootout Roll Home [" + pReport.getRollHome() + "]");
        this.print(0, TextStyle.ROLL, " + " + pReport.getReRollsLeftHome() + " ReRolls");
        this.println(0, TextStyle.ROLL, " = " + penaltyScoreHome);
        int penaltyScoreAway = pReport.getRollAway() + pReport.getReRollsLeftAway();
        this.print(0, TextStyle.ROLL, "Penalty Shootout Roll Away [" + pReport.getRollAway() + "]");
        this.print(0, TextStyle.ROLL, " + " + pReport.getReRollsLeftAway() + " ReRolls");
        this.println(0, TextStyle.ROLL, " = " + penaltyScoreAway);
        Game game = this.getClient().getGame();
        if (penaltyScoreHome > penaltyScoreAway) {
            this.print(1, TextStyle.HOME, game.getTeamHome().getName());
            this.println(1, TextStyle.NONE, " win the penalty shootout.");
        } else {
            this.print(1, TextStyle.AWAY, game.getTeamAway().getName());
            this.println(1, TextStyle.NONE, " win the penalty shootout.");
        }
    }

    public void report(ReportList pReportList) {
        block83 : for (IReport report : pReportList.getReports()) {
            switch (report.getId()) {
                case ALWAYS_HUNGRY_ROLL: {
                    this.reportAlwaysHungry((ReportSkillRoll)report);
                    continue block83;
                }
                case ARGUE_THE_CALL: {
                    this.reportArgueTheCall((ReportArgueTheCallRoll)report);
                    continue block83;
                }
                case CATCH_ROLL: {
                    this.reportCatch((ReportCatchRoll)report);
                    continue block83;
                }
                case CONFUSION_ROLL: {
                    this.reportConfusion((ReportConfusionRoll)report);
                    continue block83;
                }
                case DAUNTLESS_ROLL: {
                    this.reportDauntless((ReportDauntlessRoll)report);
                    continue block83;
                }
                case DODGE_ROLL: {
                    this.reportDodge((ReportSkillRoll)report);
                    continue block83;
                }
                case ESCAPE_ROLL: {
                    this.reportEscape((ReportSkillRoll)report);
                    continue block83;
                }
                case FOUL_APPEARANCE_ROLL: {
                    this.reportFoulAppearance((ReportSkillRoll)report);
                    continue block83;
                }
                case GO_FOR_IT_ROLL: {
                    this.reportGoingForIt((ReportSkillRoll)report);
                    continue block83;
                }
                case INTERCEPTION_ROLL: {
                    this.reportInterception((ReportInterceptionRoll)report);
                    continue block83;
                }
                case LEAP_ROLL: {
                    this.reportLeap((ReportSkillRoll)report);
                    continue block83;
                }
                case PASS_ROLL: {
                    this.reportPass((ReportPassRoll)report);
                    continue block83;
                }
                case PICK_UP_ROLL: {
                    this.reportPickup((ReportSkillRoll)report);
                    continue block83;
                }
                case RIGHT_STUFF_ROLL: {
                    this.reportRightStuff((ReportSkillRoll)report);
                    continue block83;
                }
                case REGENERATION_ROLL: {
                    this.reportRegeneration((ReportSkillRoll)report);
                    continue block83;
                }
                case SAFE_THROW_ROLL: {
                    this.reportSafeThrow((ReportSkillRoll)report);
                    continue block83;
                }
                case TENTACLES_SHADOWING_ROLL: {
                    this.reportTentaclesShadowingRoll((ReportTentaclesShadowingRoll)report);
                    continue block83;
                }
                case RE_ROLL: {
                    this.reportReRoll((ReportReRoll)report);
                    continue block83;
                }
                case SKILL_USE: {
                    this.reportSkillUse((ReportSkillUse)report);
                    continue block83;
                }
                case FOUL: {
                    this.reportFoul((ReportFoul)report);
                    continue block83;
                }
                case HAND_OVER: {
                    this.reportHandOver((ReportHandOver)report);
                    continue block83;
                }
                case PLAYER_ACTION: {
                    this.reportPlayerAction((ReportPlayerAction)report);
                    continue block83;
                }
                case INJURY: {
                    this.reportInjury((ReportInjury)report);
                    continue block83;
                }
                case APOTHECARY_ROLL: {
                    this.reportApothecaryRoll((ReportApothecaryRoll)report);
                    continue block83;
                }
                case APOTHECARY_CHOICE: {
                    this.reportApothecaryChoice((ReportApothecaryChoice)report);
                    continue block83;
                }
                case THROW_IN: {
                    this.reportThrowIn((ReportThrowIn)report);
                    continue block83;
                }
                case SCATTER_BALL: {
                    this.reportScatterBall((ReportScatterBall)report);
                    continue block83;
                }
                case BLOCK: {
                    this.reportBlock((ReportBlock)report);
                    continue block83;
                }
                case BLOCK_CHOICE: {
                    this.reportBlockChoice((ReportBlockChoice)report);
                    continue block83;
                }
                case SPECTATORS: {
                    this.reportSpectators((ReportSpectators)report);
                    continue block83;
                }
                case WEATHER: {
                    this.reportWeather((ReportWeather)report);
                    continue block83;
                }
                case COIN_THROW: {
                    this.reportCoinThrow((ReportCoinThrow)report);
                    continue block83;
                }
                case RECEIVE_CHOICE: {
                    this.reportReceiveChoice((ReportReceiveChoice)report);
                    continue block83;
                }
                case TURN_END: {
                    this.reportTurnEnd((ReportTurnEnd)report);
                    continue block83;
                }
                case PUSHBACK: {
                    this.reportPushback((ReportPushback)report);
                    continue block83;
                }
                case KICKOFF_RESULT: {
                    this.reportKickoffResult((ReportKickoffResult)report);
                    continue block83;
                }
                case KICKOFF_SCATTER: {
                    this.reportKickoffScatter((ReportKickoffScatter)report);
                    continue block83;
                }
                case KICKOFF_EXTRA_REROLL: {
                    this.reportKickoffExtraReRoll((ReportKickoffExtraReRoll)report);
                    continue block83;
                }
                case KICKOFF_RIOT: {
                    this.reportKickoffRiot((ReportKickoffRiot)report);
                    continue block83;
                }
                case KICKOFF_THROW_A_ROCK: {
                    this.reportKickoffThrowARock((ReportKickoffThrowARock)report);
                    continue block83;
                }
                case REFEREE: {
                    this.reportReferee((ReportReferee)report);
                    continue block83;
                }
                case KICKOFF_PITCH_INVASION: {
                    this.reportKickoffPitchInvasion((ReportKickoffPitchInvasion)report);
                    continue block83;
                }
                case THROW_TEAM_MATE_ROLL: {
                    this.reportThrowTeamMateRoll((ReportThrowTeamMateRoll)report);
                    continue block83;
                }
                case SCATTER_PLAYER: {
                    this.reportScatterPlayer((ReportScatterPlayer)report);
                    continue block83;
                }
                case TIMEOUT_ENFORCED: {
                    this.reportTimeoutEnforced((ReportTimeoutEnforced)report);
                    continue block83;
                }
                case WINNINGS_ROLL: {
                    this.reportWinningsRoll((ReportWinningsRoll)report);
                    continue block83;
                }
                case FAN_FACTOR_ROLL: {
                    this.reportFanFactorRoll((ReportFanFactorRoll)report);
                    continue block83;
                }
                case MOST_VALUABLE_PLAYERS: {
                    this.reportMostValuablePlayers((ReportMostValuablePlayers)report);
                    continue block83;
                }
                case JUMP_UP_ROLL: {
                    this.reportJumpUp((ReportSkillRoll)report);
                    continue block83;
                }
                case STAND_UP_ROLL: {
                    this.reportStandUp((ReportStandUpRoll)report);
                    continue block83;
                }
                case BRIBES_ROLL: {
                    this.reportBribes((ReportBribesRoll)report);
                    continue block83;
                }
                case FUMBBL_RESULT_UPLOAD: {
                    this.reportFumbblResultUpload((ReportFumbblResultUpload)report);
                    continue block83;
                }
                case START_HALF: {
                    this.reportStartHalf((ReportStartHalf)report);
                    continue block83;
                }
                case MASTER_CHEF_ROLL: {
                    this.reportMasterChef((ReportMasterChefRoll)report);
                    continue block83;
                }
                case DEFECTING_PLAYERS: {
                    this.reportDefectingPlayers((ReportDefectingPlayers)report);
                    continue block83;
                }
                case INDUCEMENT: {
                    this.reportInducement((ReportInducement)report);
                    continue block83;
                }
                case PILING_ON: {
                    this.reportPilingOn((ReportPilingOn)report);
                    continue block83;
                }
                case CHAINSAW_ROLL: {
                    this.reportChainsaw((ReportSkillRoll)report);
                    continue block83;
                }
                case LEADER: {
                    this.reportLeader((ReportLeader)report);
                    continue block83;
                }
                case SECRET_WEAPON_BAN: {
                    this.reportSecretWeaponBan((ReportSecretWeaponBan)report);
                    continue block83;
                }
                case BLOOD_LUST_ROLL: {
                    this.reportBloodLust((ReportSkillRoll)report);
                    continue block83;
                }
                case HYPNOTIC_GAZE_ROLL: {
                    this.reportHypnoticGaze((ReportSkillRoll)report);
                    continue block83;
                }
                case BITE_SPECTATOR: {
                    this.reportBiteSpectator((ReportBiteSpectator)report);
                    continue block83;
                }
                case ANIMOSITY_ROLL: {
                    this.reportAnimosity((ReportSkillRoll)report);
                    continue block83;
                }
                case RAISE_DEAD: {
                    this.reportRaiseDead((ReportRaiseDead)report);
                    continue block83;
                }
                case BLOCK_ROLL: {
                    this.reportBlockRoll((ReportBlockRoll)report);
                    continue block83;
                }
                case PENALTY_SHOOTOUT: {
                    this.reportPenaltyShootout((ReportPenaltyShootout)report);
                    continue block83;
                }
                case DOUBLE_HIRED_STAR_PLAYER: {
                    this.reportDoubleHiredStarPlayer((ReportDoubleHiredStarPlayer)report);
                    continue block83;
                }
                case SPELL_EFFECT_ROLL: {
                    this.reportSpecialEffectRoll((ReportSpecialEffectRoll)report);
                    continue block83;
                }
                case WIZARD_USE: {
                    this.reportWizardUse((ReportWizardUse)report);
                    continue block83;
                }
                case PASS_BLOCK: {
                    this.reportPassBlock((ReportPassBlock)report);
                    continue block83;
                }
                case NO_PLAYERS_TO_FIELD: {
                    this.reportNoPlayersToField((ReportNoPlayersToField)report);
                    continue block83;
                }
                case PLAY_CARD: {
                    this.reportPlayCard((ReportPlayCard)report);
                    continue block83;
                }
                case CARD_DEACTIVATED: {
                    this.reportCardDeactivated((ReportCardDeactivated)report);
                    continue block83;
                }
                case BOMB_OUT_OF_BOUNDS: {
                    this.reportBombOutOfBounds((ReportBombOutOfBounds)report);
                    continue block83;
                }
                case PETTY_CASH: {
                    this.reportPettyCash((ReportPettyCash)report);
                    continue block83;
                }
                case INDUCEMENTS_BOUGHT: {
                    this.reportInducementsBought((ReportInducementsBought)report);
                    continue block83;
                }
                case CARDS_BOUGHT: {
                    this.reportCardsBought((ReportCardsBought)report);
                    continue block83;
                }
                case CARD_EFFECT_ROLL: {
                    this.reportCardEffectRoll((ReportCardEffectRoll)report);
                    continue block83;
                }
                case WEEPING_DAGGER_ROLL: {
                    this.reportWeepingDagger((ReportSkillRoll)report);
                    continue block83;
                }
                case GAME_OPTIONS: {
                    continue block83;
                }
                default: {
                    throw new IllegalStateException("Unhandled report id " + report.getId().getName() + ".");
                }
            }
        }
    }

    private ParagraphStyle findParagraphStyle(int pIndent) {
        ParagraphStyle paragraphStyle = null;
        switch (pIndent) {
            case 0: {
                paragraphStyle = ParagraphStyle.INDENT_0;
                break;
            }
            case 1: {
                paragraphStyle = ParagraphStyle.INDENT_1;
                break;
            }
            case 2: {
                paragraphStyle = ParagraphStyle.INDENT_2;
                break;
            }
            case 3: {
                paragraphStyle = ParagraphStyle.INDENT_3;
                break;
            }
            case 4: {
                paragraphStyle = ParagraphStyle.INDENT_4;
                break;
            }
            case 5: {
                paragraphStyle = ParagraphStyle.INDENT_5;
                break;
            }
            case 6: {
                paragraphStyle = ParagraphStyle.INDENT_6;
            }
        }
        return paragraphStyle;
    }

    private void print(int pIndent, TextStyle pTextStyle, String pText) {
        this.print(this.findParagraphStyle(pIndent), pTextStyle, pText);
    }

    private void print(int pIndent, String pText) {
        this.print(this.findParagraphStyle(pIndent), null, pText);
    }

    private void print(ParagraphStyle pParagraphStyle, TextStyle pTextStyle, String pText) {
    }

    private void println(int pIndent, TextStyle pTextStyle, String pText) {
        this.println(this.findParagraphStyle(pIndent), pTextStyle, pText);
    }

    private void println(int pIndent, String pText) {
        this.println(this.findParagraphStyle(pIndent), null, pText);
    }

    private void println() {
        this.println(this.findParagraphStyle(0), null, null);
    }

    private void println(ParagraphStyle pParagraphStyle, TextStyle pTextStyle, String pText) {
    }

    private void print(int pIndent, boolean pBold, Player pPlayer) {
        if (pPlayer != null) {
            ParagraphStyle paragraphStyle = this.findParagraphStyle(pIndent);
            if (this.getClient().getGame().getTeamHome().hasPlayer(pPlayer)) {
                if (pBold) {
                    this.print(paragraphStyle, TextStyle.HOME_BOLD, pPlayer.getName());
                } else {
                    this.print(paragraphStyle, TextStyle.HOME, pPlayer.getName());
                }
            } else if (pBold) {
                this.print(paragraphStyle, TextStyle.AWAY_BOLD, pPlayer.getName());
            } else {
                this.print(paragraphStyle, TextStyle.AWAY, pPlayer.getName());
            }
        }
    }

    private void printTeamName(Game pGame, boolean pBold, String pTeamId) {
        if (pGame.getTeamHome().getId().equals(pTeamId)) {
            if (pBold) {
                this.print(this.getIndent() + 1, TextStyle.HOME_BOLD, pGame.getTeamHome().getName());
            } else {
                this.print(this.getIndent() + 1, TextStyle.HOME, pGame.getTeamHome().getName());
            }
        } else if (pBold) {
            this.print(this.getIndent() + 1, TextStyle.AWAY_BOLD, pGame.getTeamAway().getName());
        } else {
            this.print(this.getIndent() + 1, TextStyle.AWAY, pGame.getTeamAway().getName());
        }
    }

}

