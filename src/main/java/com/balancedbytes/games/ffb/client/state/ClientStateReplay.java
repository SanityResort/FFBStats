/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IProgressListener;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.ServerStatus;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;
import com.balancedbytes.games.ffb.net.commands.ServerCommandStatus;

import java.util.ArrayList;
import java.util.List;

public class ClientStateReplay
extends ClientState
implements
IProgressListener {
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
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
        switch (pNetCommand.getId()) {
            case SERVER_REPLAY: {
                ServerCommandReplay replayCommand = (ServerCommandReplay)pNetCommand;
                this.initProgress(0, replayCommand.getTotalNrOfCommands());
                for (ServerCommand command : replayCommand.getReplayCommands()) {
                    this.fReplayList.add(command);
                    this.updateProgress(this.fReplayList.size(), "Received Step %d of %d.");
                }
                if (this.fReplayList.size() < replayCommand.getTotalNrOfCommands()) break;
                if (ClientMode.REPLAY == this.getClient().getMode()) {
                    this.getClient().getCommunication().sendCloseSession();
                }
                ServerCommand[] replayCommands = this.fReplayList.toArray(new ServerCommand[this.fReplayList.size()]);
                break;
            }
            case SERVER_GAME_STATE: {
                if (ClientMode.REPLAY != this.getClient().getMode()) break;
                this.fReplayList = new ArrayList<ServerCommand>();
                this.showProgressDialog();
                break;
            }
            case SERVER_STATUS: {
                ServerCommandStatus statusCommand = (ServerCommandStatus)pNetCommand;
                if (ServerStatus.REPLAY_UNAVAILABLE != statusCommand.getServerStatus() || ClientMode.REPLAY != this.getClient().getMode()) break;
                this.getClient().getUserInterface().getStatusReport().reportStatus(statusCommand.getServerStatus());
                this.getClient().getCommunication().sendCloseSession();
                break;
            }
        }
    }


    @Override
    public void updateProgress(int pProgress) {
        this.updateProgress(pProgress, "Initialized Frame %d of %d.");
    }

    private void updateProgress(int pProgress, String pFormat) {
    }

    @Override
    public void initProgress(int pMinimum, int pMaximum) {
    }

    @Override
    public boolean actionKeyPressed(ActionKey pActionKey) {
        boolean actionHandled = false;
        if (ClientMode.SPECTATOR == this.getClient().getMode() && pActionKey == ActionKey.MENU_REPLAY) {
            actionHandled = true;
            this.getClient().updateClientState();
        }
        return actionHandled;
    }

    private void showProgressDialog() {
    }

}

