/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandRemovePlayer;

public class ClientCommandHandlerRemovePlayer
extends ClientCommandHandler {
    protected ClientCommandHandlerRemovePlayer(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_REMOVE_PLAYER;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        ServerCommandRemovePlayer removePlayerCommand = (ServerCommandRemovePlayer)pNetCommand;
        Game game = this.getClient().getGame();
        GameResult gameResult = game.getGameResult();
        Player player = game.getPlayerById(removePlayerCommand.getPlayerId());
        game.getFieldModel().remove(player);
        game.getFieldModel().setPlayerState(player, null);
        if (game.getTeamHome().hasPlayer(player)) {
            game.getTeamHome().removePlayer(player);
            gameResult.getTeamResultHome().removePlayerResult(player);
        }
        if (game.getTeamAway().hasPlayer(player)) {
            game.getTeamAway().removePlayer(player);
            gameResult.getTeamResultAway().removePlayerResult(player);
        }
        if (pMode == ClientCommandHandlerMode.PLAYING) {
            this.refreshGameMenuBar();
            this.refreshFieldComponent();
            this.refreshSideBars();
        }
        return true;
    }
}

