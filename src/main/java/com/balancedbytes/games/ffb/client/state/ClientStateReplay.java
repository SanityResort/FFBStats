/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientParameters;
import com.balancedbytes.games.ffb.client.ClientReplayer;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IProgressListener;
import com.balancedbytes.games.ffb.client.ReplayControl;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogProgressBar;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;
import java.util.ArrayList;
import java.util.List;

public class ClientStateReplay
extends ClientState
implements IDialogCloseListener,
IProgressListener {
    private DialogProgressBar fDialogProgress;
    private List<ServerCommand> fReplayList;

    protected ClientStateReplay(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.REPLAY;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        this.setClickable(false);
        ClientReplayer replayer = this.getClient().getReplayer();
        if (ClientMode.REPLAY == this.getClient().getMode()) {
            replayer.start();
            this.getClient().getCommunication().sendReplay(this.getClient().getParameters().getGameId(), 0);
        } else if (this.fReplayList == null) {
            this.fReplayList = new ArrayList<ServerCommand>();
            this.showProgressDialog();
            this.getClient().getCommunication().sendReplay(0, replayer.getFirstCommandNr());
        } else {
            replayer.positionOnLastCommand();
            replayer.getReplayControl().setActive(true);
        }
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
        ClientReplayer replayer = this.getClient().getReplayer();
        switch (pNetCommand.getId()) {
            case SERVER_REPLAY: {
                ServerCommandReplay replayCommand = (ServerCommandReplay)pNetCommand;
                this.initProgress(0, replayCommand.getTotalNrOfCommands());
                for (ServerCommand command : replayCommand.getReplayCommands()) {
                    this.fReplayList.add(command);
                    this.updateProgress(this.fReplayList.size(), "Received Step %d of %d.");
                }
                if (this.fReplayList.size() < replayCommand.getTotalNrOfCommands()) break;
                this.fDialogProgress.hideDialog();
                if (ClientMode.REPLAY == this.getClient().getMode()) {
                    this.getClient().getCommunication().sendCloseSession();
                }
                ServerCommand[] replayCommands = this.fReplayList.toArray(new ServerCommand[this.fReplayList.size()]);
                this.fDialogProgress = new DialogProgressBar(this.getClient(), "Initializing Replay");
                this.fDialogProgress.showDialog(this);
                replayer.init(replayCommands, this);
                this.fDialogProgress.hideDialog();
                if (ClientMode.REPLAY == this.getClient().getMode()) {
                    replayer.positionOnFirstCommand();
                } else {
                    replayer.positionOnLastCommand();
                }
                replayer.getReplayControl().setActive(true);
                break;
            }
            case SERVER_GAME_STATE: {
                if (ClientMode.REPLAY != this.getClient().getMode()) break;
                this.fReplayList = new ArrayList<ServerCommand>();
                this.showProgressDialog();
                break;
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
    }

    @Override
    public void updateProgress(int pProgress) {
        this.updateProgress(pProgress, "Initialized Frame %d of %d.");
    }

    private void updateProgress(int pProgress, String pFormat) {
        String message = String.format(pFormat, pProgress, this.fDialogProgress.getMaximum());
        this.fDialogProgress.updateProgress(pProgress, message);
    }

    @Override
    public void initProgress(int pMinimum, int pMaximum) {
        this.fDialogProgress.setMinimum(pMinimum);
        this.fDialogProgress.setMaximum(pMaximum);
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        if (ClientMode.SPECTATOR == this.getClient().getMode() && pActionKey == ActionKey.MENU_REPLAY) {
            actionHandled = true;
            this.getClient().getReplayer().stop();
            this.getClient().updateClientState();
            this.getClient().getUserInterface().getGameMenuBar().refresh();
        }
        return actionHandled;
    }

    private void showProgressDialog() {
        this.fDialogProgress = new DialogProgressBar(this.getClient(), "Receiving Replay");
        this.fDialogProgress.showDialog(this);
    }

}

