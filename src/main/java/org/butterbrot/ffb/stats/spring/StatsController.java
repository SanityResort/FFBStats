package org.butterbrot.ffb.stats.spring;

import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import org.butterbrot.ffb.stats.CommandHandler;
import org.butterbrot.ffb.stats.StatsCollection;
import org.butterbrot.ffb.stats.StatsCollector;
import org.butterbrot.ffb.stats.StatsCommandSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "connection")
public class StatsController {

    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    private String server;
    private int port;
    private boolean compression;

    @RequestMapping(value = "/stats/{gameId}")
    @ResponseBody
    public String stats(@PathVariable(value = "gameId") String gameId)  {
        logger.info("Creating stats for game: {}", gameId);

        final List<ServerCommand> replayCommands = new ArrayList<>();
        WebSocketClientFactory fWebSocketClientFactory;
        StatsCollector collector = new StatsCollector(replayCommands);
        CommandHandler statsHandler = new CommandHandler(collector);
        fWebSocketClientFactory = new WebSocketClientFactory();
        final StatsCommandSocket fCommandSocket = new StatsCommandSocket(Long.valueOf(gameId), compression, statsHandler);

        try {
            fWebSocketClientFactory.start();
            URI uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null);
            WebSocketClient fWebSocketClient = fWebSocketClientFactory.newWebSocketClient();
            fWebSocketClient.open(uri, fCommandSocket).get();

            logger.info("Url: " + uri);
        } catch (Exception e) {
            if (fWebSocketClientFactory.isRunning()) {
                try {
                    fWebSocketClientFactory.stop();
                } catch (Exception e1) {
                    logger.error("Could not stop factory for clean up", e1);
                }
            }
            throw new IllegalStateException("Could not start websocket", e);
        }
        logger.info("Starting wait");
        synchronized (replayCommands)  {
            try {
                replayCommands.wait();
            } catch (InterruptedException e) {
                //
            }
        }

        logger.info("Was notified");
        try {
            fWebSocketClientFactory.stop();
            fCommandSocket.awaitClose(1, TimeUnit.SECONDS);
        } catch (Exception pAnyException) {
            pAnyException.printStackTrace();
        }
        logger.info("Cleaned up websockets");

        StatsCollection stats = collector.evaluate();
        return stats.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(StatsController.class, args);
    }

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
