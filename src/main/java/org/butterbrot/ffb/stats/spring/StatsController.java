package org.butterbrot.ffb.stats.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String stats(@PathVariable(value = "gameId") String gameId) {
        logger.info("Creating stats for game: {}", gameId);
        return "gameId:" + gameId + "\nserver: " + server + "\nport: " + port + "\ncompression: " + compression;
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
