package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.*;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.INetCommandHandler;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.commands.*;
import com.sun.deploy.util.ArrayUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class CommandHandler
implements
INetCommandHandler {

    private FfbStatsClient client;
    private StatsCollector statsCollector;
    private Thread statCollectorThread;
    private final List<ServerCommand> replayCommands = new ArrayList<>();

    public CommandHandler(FfbStatsClient client) {
        this.client = client;
        statsCollector = new StatsCollector(replayCommands);
        statCollectorThread = new Thread(statsCollector);
        statCollectorThread.start();
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {

        switch (pNetCommand.getId()) {
            case SERVER_GAME_STATE:
                statsCollector.setAwayTeam(((ServerCommandGameState) pNetCommand).getGame().getTeamAway());
                statsCollector.setHomeTeam(((ServerCommandGameState) pNetCommand).getGame().getTeamHome());
                break;
            case SERVER_REPLAY:
                ServerCommandReplay replayCommand = (ServerCommandReplay) pNetCommand;
                replayCommands.addAll(Arrays.asList(replayCommand.getReplayCommands()));
                if (replayCommands.size() == replayCommand.getTotalNrOfCommands()) {
                    client.downloadDone();
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

