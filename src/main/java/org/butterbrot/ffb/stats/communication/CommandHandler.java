package org.butterbrot.ffb.stats.communication;

import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.INetCommandHandler;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.butterbrot.ffb.stats.evaluation.StatsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import static org.butterbrot.ffb.stats.Constants.*;

public class CommandHandler implements INetCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    private StatsCollector statsCollector;
    private Game game;

    public CommandHandler(StatsCollector statsCollector) {
        this.statsCollector = statsCollector;
    }

    @Override
    public void handleCommand(NetCommand pNetCommand) {


        List<ServerCommand> replayCommands = statsCollector.getReplayCommands();
        switch (pNetCommand.getId()) {
            case SERVER_GAME_STATE:
                game = ((ServerCommandGameState) pNetCommand).getGame();
                statsCollector.setAwayTeam(game.getTeamAway());
                statsCollector.setHomeTeam(game.getTeamHome());
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

    public void stop() {

        List<ServerCommand> replayCommands = statsCollector.getReplayCommands();

        synchronized (replayCommands) {
            replayCommands.notify();
        }

    }

    public void logInputFile(String inputPath) {
        JsonObject root = new JsonObject();
        root.add(FIELD_GAME, game.toJsonValue());
        JsonObject gameLog = new JsonObject();
        JsonArray array = new JsonArray();
        for (ServerCommand command : statsCollector.getReplayCommands()) {
            array.add(command.toJsonValue());
        }
        gameLog.add(FIELD_COMMAND_ARRAY, array);
        root.add(FIELD_GAME_LOG, gameLog);

        try (OutputStream fileOutputStream = new FileOutputStream(new File(inputPath));
             OutputStream gzipStream = new GZIPOutputStream(fileOutputStream);
             Writer writer = new OutputStreamWriter(gzipStream);) {
            root.writeTo(writer);
        } catch (IOException e) {
            logger.error("Could not write ");
        }
    }
}

