package org.butterbrot.ffb.stats.spring;

import org.butterbrot.ffb.stats.CommandHandler;
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
  /*      JettyWebSocketClient transport = new JettyWebSocketClient();
        //WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        //stompClient.start();
        String uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null).toASCIIString();
       // stompClient.connect(uri, new ReplayMessageHandler());
        transport.start();
        transport.doHandshake(new ReplayMessageHandler(compression), uri);
    */
        WebSocketClientFactory fWebSocketClientFactory;
        CommandHandler statsHandler = new CommandHandler();
        fWebSocketClientFactory = new WebSocketClientFactory();
        final StatsCommandSocket fCommandSocket = new StatsCommandSocket(Long.valueOf(gameId), compression, statsHandler);

        fWebSocketClientFactory.start();
        URI uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null);
              WebSocketClient fWebSocketClient = fWebSocketClientFactory.newWebSocketClient();
             fWebSocketClient.open(uri, fCommandSocket).get();
  //      String uri = new URI("ws", null, InetAddress.getByName(server).getCanonicalHostName(), port, "/command", null, null).toASCIIString();
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
