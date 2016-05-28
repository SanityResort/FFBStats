package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FfbStatsClient {
    private CommandHandler statsHandler;
    private Properties fProperties;
//    private WebSocketClientFactory fWebSocketClientFactory;
    private StatsCommandSocket fCommandSocket;


    public FfbStatsClient(String gameId) throws IOException {
        this.loadProperties();
  //      this.fWebSocketClientFactory = new WebSocketClientFactory();
        this.statsHandler = new CommandHandler(this);
        this.fCommandSocket = new StatsCommandSocket(Long.valueOf(gameId), Boolean.parseBoolean(getProperty("client.command.compression")), statsHandler);
    }

    public void startClient() {

        try {
    //        this.fWebSocketClientFactory.start();
            URI uri = new URI("ws", null, this.getServerHost().getCanonicalHostName(), this.getServerPort(), "/command", null, null);
      //      WebSocketClient fWebSocketClient = this.fWebSocketClientFactory.newWebSocketClient();
       //     fWebSocketClient.open(uri, this.fCommandSocket).get();
        }
        catch (Exception pAnyException) {
            pAnyException.printStackTrace();
        }
    }

    public void downloadDone(){
        try {
        //    this.fWebSocketClientFactory.stop();
            this.fCommandSocket.awaitClose(1, TimeUnit.SECONDS);
        }
        catch (Exception pAnyException) {
            pAnyException.printStackTrace();
        }
    }

    public CommandHandler getStatsHandler() {
        return statsHandler;
    }

    public String getProperty(String pProperty) {
        return this.fProperties.getProperty(pProperty);
    }

    public static void main(String[] args) {
        try {

            FfbStatsClient client = new FfbStatsClient(args[0]);
            client.startClient();
        }
        catch (Exception all) {
            all.printStackTrace(System.err);
        }
    }

    public void loadProperties() throws IOException {
        this.fProperties = new Properties();
        InputStream propertyInputStream = this.getClass().getResourceAsStream("/client.ini");
        this.fProperties.load(propertyInputStream);
        propertyInputStream.close();
    }

    public int getServerPort() {
        return Integer.parseInt(this.getProperty("server.port"));
    }

    public InetAddress getServerHost() throws UnknownHostException {
        return InetAddress.getByName(this.getProperty("server.host"));
    }
}

