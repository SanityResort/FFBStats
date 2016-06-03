package org.butterbrot.ffb.stats;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.butterbrot.ffb.stats.collections.StatsCollection;
import org.butterbrot.ffb.stats.communication.CommandHandler;
import org.butterbrot.ffb.stats.communication.StatsCommandSocket;
import org.butterbrot.ffb.stats.model.GameDistribution;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import refactored.com.balancedbytes.games.ffb.net.commands.ServerCommand;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class StatsController {

    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    private String server;
    private int port;
    private boolean compression;

    private Cache<String, GameDistribution> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterAccess(7, TimeUnit.DAYS).build();

    @Resource
    private StatsProvider provider;

    @RequestMapping(value = "/stats/{replayId}")
    public String stats(@PathVariable(value = "replayId") final String replayId, Model model) {
        model.addAttribute("replayId", replayId);

        try {
            GameDistribution gameDistribution = cache.get(replayId, new Callable<GameDistribution>() {
                @Override
                public GameDistribution call() throws Exception {
                    return provider.stats(replayId);
                }
            });

            model.addAttribute("game", gameDistribution);
            return "stats";
        } catch (ExecutionException e) {

            if (e.getCause() instanceof NoSuchReplayException) {
                return "notfound";
            }
            logger.error("Could not load replay", e.getCause());
            return "error";

        } catch (UncheckedExecutionException e) {
            logger.error("Could not load replay", e.getCause());
            return "error";
        }
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    // for local testing
    public static void main(String[] args) {
        SpringApplication.run(StatsController.class, args);
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
