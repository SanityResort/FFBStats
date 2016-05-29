package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.net.INetCommandHandler;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler
implements
INetCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

//    private FfbStatsClient client;
    private StatsCollector statsCollector;
    private Thread statCollectorThread;
    private final List<ServerCommand> replayCommands = new ArrayList<>();

    public CommandHandler(/*FfbStatsClient client*/) {
  //      this.client = client;
        statsCollector = new StatsCollector(replayCommands);
        statCollectorThread = new Thread(statsCollector);
        statCollectorThread.start();
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {

        logger.info("Handling command: " + pNetCommand.getId());
        switch (pNetCommand.getId()) {
            case SERVER_GAME_STATE:
                statsCollector.setAwayTeam(((ServerCommandGameState) pNetCommand).getGame().getTeamAway());
                statsCollector.setHomeTeam(((ServerCommandGameState) pNetCommand).getGame().getTeamHome());
                break;
            case SERVER_REPLAY:
                ServerCommandReplay replayCommand = (ServerCommandReplay) pNetCommand;
                replayCommands.addAll(Arrays.asList(replayCommand.getReplayCommands()));
                if (replayCommands.size() == replayCommand.getTotalNrOfCommands()) {
    //                client.downloadDone();
                    synchronized (replayCommands) {
                        replayCommands.notify();
                    }
                }
                break;
            default:
                System.out.println("Received unexpected command: " + pNetCommand.getId());
        }

    }


}

