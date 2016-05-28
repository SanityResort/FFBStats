/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IProgressListener;
import com.balancedbytes.games.ffb.client.ReplayControl;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerFactory;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.ui.ChatComponent;
import com.balancedbytes.games.ffb.client.ui.ChatLogScrollPane;
import com.balancedbytes.games.ffb.client.ui.LogComponent;
import com.balancedbytes.games.ffb.dialog.DialogStartGameParameter;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameOptions;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TeamResult;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportList;
import com.balancedbytes.games.ffb.util.UtilBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ClientReplayer
implements ActionListener {
    private FantasyFootballClient fClient;
    private static final int[] _TIMER_SETTINGS = new int[]{800, 400, 200, 100, 50, 25, 10};
    private int fFirstCommandNr;
    private List<ServerCommand> fReplayList;
    private List<ServerCommand> fUnseenList;
    private int fLastReplayPosition;
    private int fReplaySpeed;
    private boolean fReplayDirectionForward;
    private boolean fStopping;
    private int fUnseenPosition;
    private boolean fSkipping;
    private Timer fTimer;

    public ClientReplayer(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fReplayList = new ArrayList<ServerCommand>();
        this.fUnseenList = new ArrayList<ServerCommand>();
        this.fLastReplayPosition = -1;
        this.fReplaySpeed = 1;
        this.fTimer = new Timer(1000, this);
        this.fTimer.setInitialDelay(0);
    }

    public ReplayControl getReplayControl() {
        return this.getClient().getUserInterface().getChat().getReplayControl();
    }

    public boolean isReplaying() {
        return this.getClient().getUserInterface().getChat().isReplayShown();
    }

    public boolean isReplayingSingleSpeedForward() {
        return this.isReplaying() && this.isReplayDirectionForward() && this.getReplaySpeed() <= 1 && !this.fSkipping;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public void add(ServerCommand pServerCommand) {
        if (pServerCommand != null) {
            if (this.isReplaying() || this.fStopping) {
                if (pServerCommand.isReplayable() || pServerCommand.getId() == NetCommandId.SERVER_TALK) {
                    List<ServerCommand> list = this.fUnseenList;
                    synchronized (list) {
                        this.fUnseenList.add(pServerCommand);
                    }
                }
            } else if (pServerCommand.isReplayable()) {
                List<ServerCommand> list = this.fReplayList;
                synchronized (list) {
                    this.fReplayList.add(pServerCommand);
                }
                if (pServerCommand.getCommandNr() > 0 && (this.fFirstCommandNr == 0 || pServerCommand.getCommandNr() < this.fFirstCommandNr)) {
                    this.fFirstCommandNr = pServerCommand.getCommandNr();
                }
            }
        }
    }

    public void init(ServerCommand[] pServerCommands, IProgressListener pProgressListener) {
        List<ServerCommand> oldReplayList = this.fReplayList;
        this.fReplayList = new ArrayList<ServerCommand>();
        for (ServerCommand command : pServerCommands) {
            this.fReplayList.add(command);
        }
        this.fReplayList.addAll(oldReplayList);
        this.getClient().getUserInterface().getLog().detachLogDocument();
        if (pProgressListener != null) {
            pProgressListener.initProgress(0, this.getReplaySize() - 1);
        }
        this.fLastReplayPosition = 0;
        this.fReplayDirectionForward = true;
        this.replayTo(this.getReplaySize(), ClientCommandHandlerMode.INITIALIZING, pProgressListener);
        this.getClient().getUserInterface().getLog().attachLogDocument();
    }

    private void setReplaySpeed(int pReplaySpeed) {
        this.fReplaySpeed = pReplaySpeed;
        this.fTimer.setDelay(_TIMER_SETTINGS[this.fReplaySpeed]);
        this.fTimer.setInitialDelay(_TIMER_SETTINGS[this.fReplaySpeed]);
    }

    public int getReplaySpeed() {
        return this.fReplaySpeed;
    }

    public void increaseReplaySpeed() {
        if (this.fReplaySpeed < _TIMER_SETTINGS.length - 1) {
            this.setReplaySpeed(this.fReplaySpeed + 1);
        }
    }

    public void decreaseReplaySpeed() {
        if (this.fReplaySpeed > 0) {
            this.setReplaySpeed(this.fReplaySpeed - 1);
        }
    }

    public void play(boolean pDirectionForward) {
        this.fReplayDirectionForward = pDirectionForward;
        this.setReplaySpeed(1);
        if (this.fLastReplayPosition < 0) {
            this.fLastReplayPosition = this.fReplayDirectionForward ? 0 : Math.max(this.getReplaySize(), 0);
        }
        this.resume();
    }

    public void skip(boolean pDirectionForward) {
        boolean oldReplayDirectionForward;
        boolean running;
        int position;
        oldReplayDirectionForward = this.fReplayDirectionForward;
        running = this.fTimer.isRunning();
        if (running) {
            this.pause();
        }
        if (pDirectionForward) {
            position = this.getReplaySize() - 1;
            for (int i = this.fLastReplayPosition + 1; i < this.getReplaySize(); ++i) {
                if (!this.isRegularEndTurnCommand(this.getReplayCommand(i))) continue;
                position = i;
                break;
            }
        } else {
            position = 0;
            for (int i = this.fLastReplayPosition - 1; i > 0; --i) {
                if (!this.isRegularEndTurnCommand(this.getReplayCommand(i))) continue;
                position = i;
                break;
            }
        }
        this.fReplayDirectionForward = pDirectionForward;
        this.fSkipping = true;
        this.replayTo(position, ClientCommandHandlerMode.REPLAYING, null);
        this.fSkipping = false;
        this.fReplayDirectionForward = oldReplayDirectionForward;
        if (running) {
            this.resume();
        }
    }

    private boolean isRegularEndTurnCommand(ServerCommand pServerCommand) {
        if (NetCommandId.SERVER_MODEL_SYNC == pServerCommand.getId() && ((ServerCommandModelSync)pServerCommand).getReportList().hasReport(ReportId.TURN_END)) {
            return this.getClient().getUserInterface().getLog().hasCommandHighlight(pServerCommand.getCommandNr());
        }
        return false;
    }

    public boolean isRunning() {
        return this.fTimer.isRunning();
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        if (this.fStopping) {
            if (this.fUnseenPosition < this.getUnseenSize()) {
                ServerCommand unseenCommand;
                List<ServerCommand> list = this.fUnseenList;
                synchronized (list) {
                    unseenCommand = this.fUnseenList.get(this.fUnseenPosition);
                }
                if (unseenCommand != null) {
                    Object commandHandler;
                    if (unseenCommand.getId() == NetCommandId.SERVER_TALK) {
                        commandHandler = this.getClient().getCommandHandlerFactory().getCommandHandler(NetCommandId.SERVER_TALK);
                        ((ClientCommandHandler)commandHandler).handleNetCommand(unseenCommand, ClientCommandHandlerMode.INITIALIZING);
                    } else {
                        commandHandler = this.fReplayList;
                        synchronized (commandHandler) {
                            this.fReplayList.add(unseenCommand);
                        }
                        this.fLastReplayPosition = this.getReplaySize() - 1;
                        this.replayTo(this.fLastReplayPosition + 1, ClientCommandHandlerMode.INITIALIZING, null);
                    }
                }
                ++this.fUnseenPosition;
            } else {
                this.fStopping = false;
                this.fTimer.stop();
                this.fUnseenList.clear();
                this.getClient().getUserInterface().getLog().getLogScrollPane().setScrollBarToMaximum();
            }
        } else if (this.fReplayDirectionForward) {
            if (this.fLastReplayPosition < this.getReplaySize()) {
                this.replayTo(this.fLastReplayPosition + 1, ClientCommandHandlerMode.REPLAYING, null);
            } else {
                this.pause();
                this.getReplayControl().showPause();
            }
        } else if (this.fLastReplayPosition > 0) {
            this.replayTo(this.fLastReplayPosition - 1, ClientCommandHandlerMode.REPLAYING, null);
        } else {
            this.pause();
            this.getReplayControl().showPause();
        }
    }

    private int getReplaySize() {
        List<ServerCommand> list = this.fReplayList;
        synchronized (list) {
            return this.fReplayList.size();
        }
    }

    private int getUnseenSize() {
        List<ServerCommand> list = this.fUnseenList;
        synchronized (list) {
            return this.fUnseenList.size();
        }
    }

    public ServerCommand getReplayCommand(int pPosition) {
        List<ServerCommand> list = this.fReplayList;
        synchronized (list) {
            return this.fReplayList.get(pPosition);
        }
    }

    public void pause() {
        if (this.isRunning()) {
            this.fTimer.stop();
        }
    }

    public void resume() {
        if (!this.isRunning()) {
            this.fTimer.start();
        }
    }

    private void replayTo(int pReplayPosition, ClientCommandHandlerMode pMode, IProgressListener pProgressListener) {
        int start = 0;
        if (this.fLastReplayPosition >= 0 && this.fLastReplayPosition < pReplayPosition) {
            start = this.fLastReplayPosition;
        }
        if (start == 0) {
            this.getClient().setGame(this.createGame());
            this.getClient().getUserInterface().init();
        }
        ServerCommand serverCommand = null;
        for (int i = start; i < pReplayPosition; ++i) {
            serverCommand = this.getReplayCommand(i);
            if (serverCommand != null) {
                this.getClient().getCommandHandlerFactory().handleNetCommand(serverCommand, pMode);
            }
            if (pProgressListener == null) continue;
            pProgressListener.updateProgress(i - start);
        }
        this.fLastReplayPosition = pReplayPosition;
        if (serverCommand != null && pMode == ClientCommandHandlerMode.REPLAYING) {
            this.highlightCommand(serverCommand.getCommandNr());
        }
        this.refreshUserInterface();
    }

    public void replayToCommand(int pCommandNr) {
        this.pause();
        this.getClient().getUserInterface().getChat().getReplayControl().showPause();
        int position = this.findPositionForCommand(pCommandNr);
        if (position >= 0) {
            this.fLastReplayPosition = 0;
            this.fReplayDirectionForward = true;
            this.fSkipping = true;
            this.replayTo(position + 1, ClientCommandHandlerMode.REPLAYING, null);
            this.fSkipping = false;
        }
    }

    private int findPositionForCommand(int pCommandNr) {
        int position = -1;
        for (int i = 0; i < this.getReplaySize(); ++i) {
            ServerCommand serverCommand = this.getReplayCommand(i);
            if (serverCommand.getCommandNr() != pCommandNr) continue;
            position = i;
            break;
        }
        return position;
    }

    private void highlightCommand(int pCommandNr) {
        LogComponent log = this.getClient().getUserInterface().getLog();
        boolean commandShown = log.highlightCommand(pCommandNr, this.fReplayDirectionForward);
        if (!commandShown && !this.fReplayDirectionForward) {
            int commandNr = pCommandNr;
            while (!commandShown && commandNr > log.getMinimumCommandNr()) {
                commandShown = log.highlightCommand(--commandNr, this.fReplayDirectionForward);
            }
        }
    }

    private Game createGame() {
        Game oldGame = this.getClient().getGame();
        Game game = new Game();
        game.setId(oldGame.getId());
        game.setTurnTime(oldGame.getTurnTime());
        game.setGameTime(oldGame.getGameTime());
        game.setHomePlaying(true);
        game.setTurnMode(TurnMode.START_GAME);
        game.setDialogParameter(new DialogStartGameParameter());
        game.getFieldModel().setWeather(Weather.NICE);
        game.getOptions().init(oldGame.getOptions());
        GameResult oldGameResult = oldGame.getGameResult();
        this.addTeam(game, oldGame.getTeamHome(), oldGameResult.getTeamResultHome(), true);
        this.addTeam(game, oldGame.getTeamAway(), oldGameResult.getTeamResultAway(), false);
        return game;
    }

    private void addTeam(Game pGame, Team pTeam, TeamResult pOldTeamResult, boolean pHomeTeam) {
        Player[] players = pTeam.getPlayers();
        if (pHomeTeam) {
            pGame.getFieldModel().remove(pGame.getTeamHome());
            pGame.setTeamHome(pTeam);
        } else {
            pGame.getFieldModel().remove(pGame.getTeamAway());
            pGame.setTeamAway(pTeam);
        }
        FieldModel fieldModel = pGame.getFieldModel();
        for (int i = 0; i < players.length; ++i) {
            PlayerType playerType = players[i].getPlayerType();
            if (playerType == null || playerType == PlayerType.MERCENARY || playerType == PlayerType.STAR || playerType == PlayerType.RAISED_FROM_DEAD) {
                fieldModel.remove(players[i]);
                pTeam.removePlayer(players[i]);
                continue;
            }
            PlayerResult playerResult = pGame.getGameResult().getPlayerResult(players[i]);
            if (players[i].getRecoveringInjury() != null) {
                fieldModel.setPlayerState(players[i], new PlayerState(10));
                playerResult.setSendToBoxReason(SendToBoxReason.MNG);
            } else {
                fieldModel.setPlayerState(players[i], new PlayerState(9));
            }
            playerResult.setCurrentSpps(pOldTeamResult.getPlayerResult(players[i]).getCurrentSpps());
            UtilBox.putPlayerIntoBox(pGame, players[i]);
        }
    }

    private void refreshUserInterface() {
        UserInterface userInterface = this.getClient().getUserInterface();
        userInterface.refresh();
        this.getClient().updateClientState();
        userInterface.getDialogManager().updateDialog();
    }

    public boolean isReplayDirectionForward() {
        return this.fReplayDirectionForward;
    }

    public void start() {
        this.setReplaySpeed(0);
        this.getClient().getUserInterface().getChat().showReplay(true);
        this.getClient().getUserInterface().getLog().enableReplay(true);
        this.getReplayControl().showPause();
        this.getReplayControl().setActive(false);
    }

    public void positionOnFirstCommand() {
        LogComponent log = this.getClient().getUserInterface().getLog();
        this.replayToCommand(log.findCommandNr(1));
        try {
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    ClientReplayer.this.getClient().getUserInterface().getLog().getLogScrollPane().setScrollBarToMinimum();
                }
            });
        }
        catch (Exception pE) {
            throw new FantasyFootballException(pE);
        }
    }

    public void positionOnLastCommand() {
        this.fReplayDirectionForward = false;
        this.fLastReplayPosition = Math.max(this.getReplaySize() - 1, 0);
        ServerCommand serverCommand = this.getReplayCommand(this.fLastReplayPosition);
        if (serverCommand != null) {
            this.highlightCommand(serverCommand.getCommandNr());
        }
        try {
            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeAndWait(new Runnable(){

                    @Override
                    public void run() {
                        ClientReplayer.this.getClient().getUserInterface().getLog().getLogScrollPane().setScrollBarToMaximum();
                    }
                });
            } else {
                this.getClient().getUserInterface().getLog().getLogScrollPane().setScrollBarToMaximum();
            }
        }
        catch (Exception pE) {
            throw new FantasyFootballException(pE);
        }
    }

    public void stop() {
        this.pause();
        this.replayTo(this.getReplaySize(), ClientCommandHandlerMode.REPLAYING, null);
        this.fUnseenPosition = 0;
        this.fStopping = true;
        this.setReplaySpeed(4);
        this.getClient().getUserInterface().getLog().hideHighlight();
        this.getClient().getUserInterface().getLog().enableReplay(false);
        this.getClient().getUserInterface().getChat().showReplay(false);
        this.fTimer.start();
    }

    public int getFirstCommandNr() {
        return this.fFirstCommandNr;
    }

}

