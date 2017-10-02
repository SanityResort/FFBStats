package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.client.net.CommandEndpoint;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.google.gson.Gson;
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

import javax.annotation.PostConstruct;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
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

    @PostConstruct
    public void logProperties(){
        logger.info("StatsProvider connects to {}:{} with compression set to {}", server, port, compression);
    }

    public StatsCollection stats(String replayId) throws NoSuchReplayException {

        logger.info("Creating stats for game: {}", replayId);

        List<ServerCommand> replayCommands = new ArrayList<>();
        StatsCollector collector = new StatsCollector(replayCommands);
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

        return collector.evaluate(replayId);
    }

    public GameDistribution distribution(String replayId) throws NoSuchReplayException {
        StatsCollection stats = stats(replayId);
        System.out.println( new Gson().toJson(stats));
        return new GameDistribution(stats, replayId);
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
