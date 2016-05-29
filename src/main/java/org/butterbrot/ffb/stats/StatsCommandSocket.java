package org.butterbrot.ffb.stats;

import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.commands.ClientCommandReplay;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonValue;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class StatsCommandSocket
implements WebSocket.OnTextMessage
{
    private static final Logger logger = LoggerFactory.getLogger(StatsCommandSocket.class);

    private NetCommandFactory fNetCommandFactory;
    private Connection fConnection;
    private boolean fCommandCompression;
    private final CountDownLatch fCloseLatch;
    private final CommandHandler statsHandler;
    private final long gameId;

    public StatsCommandSocket(long gameId, boolean compression, CommandHandler statsHandler) {
        this.gameId = gameId;
        this.statsHandler = statsHandler;
        this.fNetCommandFactory = new NetCommandFactory();
        this.fCloseLatch = new CountDownLatch(1);
        this.fCommandCompression = compression;
    }

    public void onOpen(Connection pConnection) {
        this.fConnection = pConnection;
        logger.info("Got connect: {}" , this.fConnection);
        this.fConnection.setMaxIdleTime(Integer.MAX_VALUE);
        this.fConnection.setMaxTextMessageSize(65536);
        try {
            send(new ClientCommandReplay(gameId, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(String pTextMessage) {
        logger.info("Called onmessage");
        JsonValue jsonValue;
        if (!StringTool.isProvided(pTextMessage) || !this.isOpen()) {
            return;
        }
        try {
            jsonValue = UtilJson.inflateFromBase64(pTextMessage);
        }
        catch (IOException pIoException) {
            jsonValue = null;
        }
        NetCommand netCommand = this.fNetCommandFactory.forJsonValue(jsonValue);
        if (netCommand == null) {
            return;
        }
        statsHandler.handleCommand(netCommand);
    }

    public void onClose(int pCloseCode, String pCloseReason) {
        logger.info("Connection closed: {} - {}", pCloseCode, pCloseReason);
        this.fConnection = null;
        this.fCloseLatch.countDown();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        logger.info("Called awaitclose");
        return this.fCloseLatch.await(duration, unit);
    }

    public boolean send(NetCommand pCommand) throws IOException {
        logger.info("Called send");
        if (pCommand == null || !this.isOpen()) {
            return false;
        }
        String textMessage = null;
        if (this.fCommandCompression) {
            try {
                textMessage = UtilJson.deflateToBase64(pCommand.toJsonValue());
            }
            catch (IOException var3_3) {}
        } else {
            JsonValue jsonValue = pCommand.toJsonValue();
            if (jsonValue != null) {
                textMessage = jsonValue.toString();
            }
        }
        if (!StringTool.isProvided(textMessage)) {
            return false;
        }
        logger.info("Calling sendMessage");
        this.fConnection.sendMessage(textMessage);
        return true;
    }

    public boolean isOpen() {
        return this.fConnection != null && this.fConnection.isOpen();
    }
}

