/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.ClientParameters;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.client.ui.LogComponent;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandJoin;
import com.balancedbytes.games.ffb.util.ArrayTool;

public class ClientCommandHandlerJoin
extends ClientCommandHandler {
    protected ClientCommandHandlerJoin(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_JOIN;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        Object[] players;
        if (pMode == ClientCommandHandlerMode.QUEUING) {
            return true;
        }
        ServerCommandJoin joinCommand = (ServerCommandJoin)pNetCommand;
        UserInterface userInterface = this.getClient().getUserInterface();
        if (ClientMode.PLAYER == joinCommand.getClientMode()) {
            this.getClient().getClientData().setTurnTimerStopped(false);
        }
        if (ArrayTool.isProvided(players = joinCommand.getPlayerNames()) && players.length > 1) {
            Object homeCoach = null;
            Object awayCoach = null;
            if (players[1].equals(this.getClient().getParameters().getCoach())) {
                homeCoach = players[1];
                awayCoach = players[0];
            } else {
                homeCoach = players[0];
                awayCoach = players[1];
            }
            GameTitle gameTitle = userInterface.getGameTitle();
            gameTitle.setClientMode(this.getClient().getMode());
            gameTitle.setHomeCoach((String)homeCoach);
            gameTitle.setAwayCoach((String)awayCoach);
            this.updateGameTitle(gameTitle);
        }
        this.getClient().getClientData().setSpectators(joinCommand.getSpectators());
        if (pMode != ClientCommandHandlerMode.REPLAYING) {
            if (ClientMode.PLAYER == joinCommand.getClientMode()) {
                userInterface.getLog().markCommandBegin(joinCommand.getCommandNr());
                userInterface.getStatusReport().reportJoin(joinCommand);
                userInterface.getLog().markCommandEnd(joinCommand.getCommandNr());
            }
            this.refreshSideBars();
        }
        return true;
    }
}

