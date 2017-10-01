/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.GameList;
import com.balancedbytes.games.ffb.GameListEntry;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.PasswordChallenge;
import com.balancedbytes.games.ffb.TeamList;
import com.balancedbytes.games.ffb.TeamListEntry;
import com.balancedbytes.games.ffb.client.ClientParameters;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogGameChoice;
import com.balancedbytes.games.ffb.client.dialog.DialogInformation;
import com.balancedbytes.games.ffb.client.dialog.DialogLogin;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.dialog.DialogTeamChoice;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogJoinParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.ServerStatus;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameList;
import com.balancedbytes.games.ffb.net.commands.ServerCommandJoin;
import com.balancedbytes.games.ffb.net.commands.ServerCommandPasswordChallenge;
import com.balancedbytes.games.ffb.net.commands.ServerCommandStatus;
import com.balancedbytes.games.ffb.net.commands.ServerCommandTeamList;
import com.balancedbytes.games.ffb.net.commands.ServerCommandVersion;
import com.balancedbytes.games.ffb.util.StringTool;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientStateLogin
extends ClientState
implements IDialogCloseListener {
    private static final Pattern _PATTERN_VERSION = Pattern.compile("([0-9]+)\\.([0-9]+)\\.([0-9]+)");
    private DialogInformation fWaitingDialog;
    private ServerStatus fLastServerError;
    private String fGameName;
    private String fTeamHomeId;
    private String fTeamHomeName;
    private String fTeamAwayName;
    private byte[] fEncodedPassword;
    private int fPasswordLength;
    private boolean fListGames;
    private long fGameId;

    protected ClientStateLogin(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.LOGIN;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(false);
        this.setClickable(false);
        if (StringTool.isProvided(this.getClient().getParameters().getTeamId())) {
            this.fTeamHomeId = this.getClient().getParameters().getTeamId();
            this.fTeamHomeName = this.getClient().getParameters().getTeamName();
            this.fTeamAwayName = null;
        } else {
            this.fTeamHomeId = null;
            this.fTeamHomeName = this.getClient().getParameters().getTeamHome();
            this.fTeamAwayName = this.getClient().getParameters().getTeamAway();
        }
        this.getClient().getCommunication().sendRequestVersion();
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        pDialog.hideDialog();
        switch (pDialog.getId()) {
            case GAME_COACH_PASSWORD: {
                DialogLogin loginDialog = (DialogLogin)pDialog;
                this.fGameName = loginDialog.getGameName();
                this.fListGames = loginDialog.isListGames();
                this.fEncodedPassword = loginDialog.getEncodedPassword();
                this.fPasswordLength = loginDialog.getPasswordLength();
                this.sendChallenge();
                break;
            }
            case INFORMATION: {
                DialogInformation informationDialog = (DialogInformation)pDialog;
                if (informationDialog.getOptionType() == 1) {
                    this.showLoginDialog();
                    break;
                }
                this.getClient().exitClient();
                break;
            }
            case TEAM_CHOICE: {
                DialogTeamChoice teamChoiceDialog = (DialogTeamChoice)pDialog;
                TeamListEntry selectedTeamEntry = teamChoiceDialog.getSelectedTeamEntry();
                if (selectedTeamEntry != null) {
                    this.fTeamHomeId = selectedTeamEntry.getTeamId();
                    this.fTeamHomeName = selectedTeamEntry.getTeamName();
                    this.sendChallenge();
                    break;
                }
                this.showLoginDialog();
                break;
            }
            case GAME_CHOICE: {
                DialogGameChoice gameChoiceDialog = (DialogGameChoice)pDialog;
                GameListEntry selectedGameEntry = gameChoiceDialog.getSelectedGameEntry();
                if (selectedGameEntry != null) {
                    this.fGameId = selectedGameEntry.getGameId();
                    this.fListGames = false;
                    this.sendChallenge();
                    break;
                }
                this.showLoginDialog();
                break;
            }
        }
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        switch (pNetCommand.getId()) {
            case SERVER_VERSION: {
                String[] properties;
                ServerCommandVersion versionCommand = (ServerCommandVersion)pNetCommand;
                if (!this.checkVersion(versionCommand.getServerVersion(), versionCommand.getClientVersion())) break;
                for (String property : properties = versionCommand.getClientProperties()) {
                    this.getClient().setProperty(property, versionCommand.getClientPropertyValue(property));
                }
                this.showLoginDialog();
                break;
            }
            case SERVER_STATUS: {
                ServerCommandStatus errorCommand = (ServerCommandStatus)pNetCommand;
                this.fLastServerError = errorCommand.getServerStatus();
                DialogInformation informationDialog = null;
                informationDialog = this.fLastServerError == ServerStatus.FUMBBL_ERROR ? new DialogInformation(this.getClient(), "Fumbbl Error", errorCommand.getMessage(), 2) : new DialogInformation(this.getClient(), "Server Error", this.fLastServerError.getMessage(), 1);
                informationDialog.showDialog(this);
                break;
            }
            case SERVER_TEAM_LIST: {
                ServerCommandTeamList teamListCommand = (ServerCommandTeamList)pNetCommand;
                DialogTeamChoice teamChoiceDialog = new DialogTeamChoice(this.getClient(), teamListCommand.getTeamList());
                teamChoiceDialog.showDialog(this);
                break;
            }
            case SERVER_GAME_LIST: {
                ServerCommandGameList gameListCommand = (ServerCommandGameList)pNetCommand;
                GameList gameList = gameListCommand.getGameList();
                if (gameList.size() > 0) {
                    DialogGameChoice gameChoiceDialog = new DialogGameChoice(this.getClient(), gameListCommand.getGameList());
                    gameChoiceDialog.showDialog(this);
                    break;
                }
                DialogInformation noGamesDialog = new DialogInformation(this.getClient(), "No Open Games", "You do not have any open games to join.", 1);
                noGamesDialog.showDialog(this);
                break;
            }
            case SERVER_JOIN: {
                ServerCommandJoin joinCommand = (ServerCommandJoin)pNetCommand;
                if (joinCommand.getPlayerNames().length <= 1) {
                    if (this.getClient().getParameters().getGameId() == 0 && StringTool.isProvided(this.fGameName)) {
                        this.getClient().getUserInterface().getStatusReport().reportGameName(this.fGameName);
                    }
                    game.setDialogParameter(new DialogJoinParameter());
                } else {
                    game.setDialogParameter(null);
                }
                userInterface.getDialogManager().updateDialog();
                break;
            }
            case SERVER_PASSWORD_CHALLENGE: {
                ServerCommandPasswordChallenge passwordChallengeCommand = (ServerCommandPasswordChallenge)pNetCommand;
                String response = this.createResponse(passwordChallengeCommand.getChallenge());
                this.sendJoin(response);
                break;
            }
            default: {
                super.handleCommand(pNetCommand);
            }
        }
    }

    private boolean checkVersion(String pServerVersion, String pClientVersion) {
        if (this.checkVersionConflict(pClientVersion, "1.3.0")) {
            String[] messages = new String[]{"Server expects client version " + pClientVersion + " or newer.", "Client version is 1.3.0.", "Please update your client!"};
            this.fWaitingDialog = new DialogInformation(this.getClient(), "Client Version Conflict", messages, 2, false);
            this.fWaitingDialog.showDialog(this);
            return false;
        }
        if (this.checkVersionConflict("1.3.0", pServerVersion)) {
            String[] messages = new String[]{"Client expects server version 1.3.0 or newer.", "Server version is " + pServerVersion + ".", "Please wait for a server update!"};
            this.fWaitingDialog = new DialogInformation(this.getClient(), "Server Version Conflict", messages, 2, false);
            this.fWaitingDialog.showDialog(this);
            return false;
        }
        return true;
    }

    private boolean checkVersionConflict(String pVersionExpected, String pVersionIs) {
        int majorVersionExpected = 0;
        int minorVersionExpected = 0;
        int releaseExpected = 0;
        Matcher versionExpectedMatcher = _PATTERN_VERSION.matcher(pVersionExpected);
        if (versionExpectedMatcher.matches()) {
            majorVersionExpected = Integer.parseInt(versionExpectedMatcher.group(1));
            minorVersionExpected = Integer.parseInt(versionExpectedMatcher.group(2));
            releaseExpected = Integer.parseInt(versionExpectedMatcher.group(3));
        }
        int majorVersionIs = 0;
        int minorVersionIs = 0;
        int releaseIs = 0;
        Matcher versionIsMatcher = _PATTERN_VERSION.matcher(pVersionIs);
        if (versionIsMatcher.matches()) {
            majorVersionIs = Integer.parseInt(versionIsMatcher.group(1));
            minorVersionIs = Integer.parseInt(versionIsMatcher.group(2));
            releaseIs = Integer.parseInt(versionIsMatcher.group(3));
        }
        return majorVersionIs < majorVersionExpected || minorVersionIs < minorVersionExpected || releaseIs < releaseExpected;
    }

    private String createResponse(String pChallenge) {
        String response;
        try {
            response = PasswordChallenge.createResponse(pChallenge, this.fEncodedPassword);
        }
        catch (IOException ioe) {
            response = null;
        }
        catch (NoSuchAlgorithmException nsa) {
            response = null;
        }
        return response;
    }

    private void sendChallenge() {
        String authentication = this.getClient().getParameters().getAuthentication();
        if (StringTool.isProvided(authentication)) {
            this.sendJoin(authentication);
        } else {
            this.getClient().getCommunication().sendPasswordChallenge();
        }
    }

    private void sendJoin(String pResponse) {
        if (this.fListGames) {
            this.getClient().getCommunication().sendJoin(this.getClient().getParameters().getCoach(), pResponse, 0, null, null, null);
        } else {
            this.getClient().getCommunication().sendJoin(this.getClient().getParameters().getCoach(), pResponse, this.fGameId > 0 ? this.fGameId : this.getClient().getParameters().getGameId(), this.fGameName, this.fTeamHomeId, this.fTeamHomeName);
        }
    }

    private void showLoginDialog() {
        boolean hasGameId;
        boolean bl = hasGameId = this.getClient().getParameters().getGameId() > 0;
        if (StringTool.isProvided(this.getClient().getParameters().getAuthentication())) {
            this.fPasswordLength = -1;
        }
        DialogLogin loginDialog = new DialogLogin(this.getClient(), this.fEncodedPassword, this.fPasswordLength, this.fTeamHomeName, this.fTeamAwayName, !hasGameId);
        if (hasGameId && this.fPasswordLength < 0) {
            this.dialogClosed(loginDialog);
        } else if (this.fLastServerError == ServerStatus.ERROR_GAME_IN_USE) {
            loginDialog.showDialogWithError(this, 1);
        } else if (this.fLastServerError == ServerStatus.ERROR_UNKNOWN_COACH) {
            loginDialog.showDialogWithError(this, 2);
        } else if (this.fLastServerError == ServerStatus.ERROR_WRONG_PASSWORD) {
            loginDialog.setEncodedPassword(null, this.fPasswordLength);
            loginDialog.showDialogWithError(this, 3);
        } else {
            loginDialog.showDialog(this);
        }
    }

}

