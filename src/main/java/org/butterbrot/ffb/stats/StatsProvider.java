package org.butterbrot.ffb.stats;

import org.butterbrot.ffb.stats.collections.StatsCollection;
import org.butterbrot.ffb.stats.communication.CommandHandler;
import org.butterbrot.ffb.stats.communication.StatsCommandSocket;
import org.butterbrot.ffb.stats.model.GameDistribution;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommand;

import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "connection")
@Service
public class StatsProvider {

    private static final Logger logger = LoggerFactory.getLogger(StatsProvider.class);

    private String server;
    private int port;
    private boolean compression;

    public GameDistribution stats(String replayId) throws NoSuchReplayException {

        logger.info("Creating stats for game: {}", replayId);

        List<ServerCommand> replayCommands = new ArrayList<>();
        StatsCollector collector = new StatsCollector(replayCommands);
        CommandHandler statsHandler = new CommandHandler(collector);
        WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
        StatsCommandSocket commandSocket = new StatsCommandSocket(Long.valueOf(replayId), compression, statsHandler);

        try {
            webSocketClientFactory.start();
            URI uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null);
            WebSocketClient fWebSocketClient = webSocketClientFactory.newWebSocketClient();
            fWebSocketClient.open(uri, commandSocket).get();

        } catch (Exception e) {
            if (webSocketClientFactory.isRunning()) {
                try {
                    webSocketClientFactory.stop();
                } catch (Exception e1) {
                    logger.error("Could not stop factory for clean up", e1);
                }
            }
            logger.error("Could not start websocket", e);
            throw new IllegalStateException("Could not start websocket", e);
        }
        synchronized (replayCommands) {
            try {
                replayCommands.wait(10000);
            } catch (InterruptedException e) {
                //
            }
        }

        try {
            webSocketClientFactory.stop();
            commandSocket.awaitClose(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Could not stop websocket factory", e);
        }

        if (replayCommands.isEmpty()) {
            throw new NoSuchReplayException();
        }

        StatsCollection stats = collector.evaluate();
        return new GameDistribution(stats);

    }

    // keep those for property injection
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
    }


}
