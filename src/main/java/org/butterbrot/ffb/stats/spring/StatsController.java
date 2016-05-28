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
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.jetty.JettyWebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.InetAddress;
import java.net.URI;
import java.util.Collections;

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
    public String stats(@PathVariable(value = "gameId") String gameId) throws Exception {
        logger.info("Creating stats for game: {}", gameId);

/*        WebSocketTransport webSocketTransport = new WebSocketTransport(new JettyWebSocketClient());

        URI uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null);

        SockJsClient webSocketClient = new SockJsClient(Collections.<Transport>singletonList(webSocketTransport));
        webSocketClient.doHandshake(new ReplayMessageHandler(), new WebSocketHttpHeaders(),  uri);
*/
        JettyWebSocketClient transport = new JettyWebSocketClient();
        //WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        //stompClient.start();
        String uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null).toASCIIString();
       // stompClient.connect(uri, new ReplayMessageHandler());
        transport.start();
        transport.doHandshake(new ReplayMessageHandler(), uri);
        logger.info("Url: " + uri);

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
