package org.butterbrot.ffb.stats.web;

import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import org.butterbrot.ffb.stats.NoSuchReplayException;
import org.butterbrot.ffb.stats.evaluation.stats.StatsCollector;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.butterbrot.ffb.stats.communication.CommandHandler;
import org.butterbrot.ffb.stats.communication.StatsCommandSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.InetAddress;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "connection")
@Service
public class StatsProvider {

    private static final Logger logger = LoggerFactory.getLogger(StatsProvider.class);

    private String server;
    private int port;
    private boolean compression;

    @PostConstruct
    public void logProperties() {
        logger.info("StatsProvider connects to {}:{} with compression set to {}", server, port, compression);
    }

    public StatsCollection stats(String replayId, boolean activateInputLog, String inputPathTemplate) throws NoSuchReplayException {

        logger.info("Creating stats for game: {}", replayId);

        StatsCollector collector = new StatsCollector();
        CommandHandler statsHandler = new CommandHandler(collector);
        StatsCommandSocket commandSocket = new StatsCommandSocket(Long.valueOf(replayId), compression, statsHandler);
        Session session;
        try {
            URI uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null);
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxSessionIdleTimeout(Integer.MAX_VALUE);
            container.setDefaultMaxTextMessageBufferSize(65536);
            session = container.connectToServer(commandSocket, uri);

        } catch (Exception e) {
            logger.error("Could not start websocket", e);
            throw new IllegalStateException("Could not start websocket", e);
        }

        List<ServerCommand> replayCommands = collector.getReplayCommands();

        synchronized (replayCommands) {
            try {
                replayCommands.wait(10000);
            } catch (InterruptedException e) {
                //
            }
        }

        try {
            session.close();
            commandSocket.awaitClose(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Could not stop websocket factory", e);
        }

        if (replayCommands.isEmpty()) {
            throw new NoSuchReplayException();
        }

        if (activateInputLog) {
            statsHandler.logInputFile(String.format(inputPathTemplate, replayId));
        }

        return collector.evaluate(replayId);
    }

    // keep those for property injection
    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
    }


}
