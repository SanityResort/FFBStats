package org.butterbrot.ffb.stats.communication;

import refactored.com.balancedbytes.games.ffb.net.INetCommandHandler;
import refactored.com.balancedbytes.games.ffb.net.NetCommand;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommand;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;
import org.butterbrot.ffb.stats.StatsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class CommandHandler implements INetCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    private StatsCollector statsCollector;

    public CommandHandler(StatsCollector statsCollector) {
        this.statsCollector = statsCollector;
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {
        List<ServerCommand> replayCommands = statsCollector.getReplayCommands();
        switch (pNetCommand.getId()) {
            case SERVER_GAME_STATE:
                statsCollector.setAwayTeam(((ServerCommandGameState) pNetCommand).getGame().getTeamAway());
                statsCollector.setHomeTeam(((ServerCommandGameState) pNetCommand).getGame().getTeamHome());
                break;
            case SERVER_REPLAY:
                ServerCommandReplay replayCommand = (ServerCommandReplay) pNetCommand;
                replayCommands.addAll(Arrays.asList(replayCommand.getReplayCommands()));
                if (replayCommands.size() == replayCommand.getTotalNrOfCommands()) {
                    stop();
                }
                break;
            default:
                logger.warn("Received unexpected command: {}", pNetCommand.getId());
        }

    }

    public void stop(){
        List<ServerCommand> replayCommands = statsCollector.getReplayCommands();
        synchronized (replayCommands) {
            replayCommands.notify();
        }
    }

}

