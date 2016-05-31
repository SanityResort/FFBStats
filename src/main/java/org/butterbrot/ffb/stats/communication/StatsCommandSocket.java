package org.butterbrot.ffb.stats.communication;

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
    private final long replayId;

    public StatsCommandSocket(long replayId, boolean compression, CommandHandler statsHandler) {
        this.replayId = replayId;
        this.statsHandler = statsHandler;
        this.fNetCommandFactory = new NetCommandFactory();
        this.fCloseLatch = new CountDownLatch(1);
        this.fCommandCompression = compression;
    }

    public void onOpen(Connection pConnection) {
        this.fConnection = pConnection;
        this.fConnection.setMaxIdleTime(Integer.MAX_VALUE);
        this.fConnection.setMaxTextMessageSize(65536);
        try {
            send(new ClientCommandReplay(replayId, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(String pTextMessage) {
        try {
            JsonValue jsonValue;
            if (!StringTool.isProvided(pTextMessage) || !this.isOpen()) {
                return;
            }
            try {
                jsonValue = UtilJson.inflateFromBase64(pTextMessage);
            } catch (IOException pIoException) {
                jsonValue = null;
            }
            NetCommand netCommand = this.fNetCommandFactory.forJsonValue(jsonValue);
            if (netCommand == null) {
                return;
            }
            statsHandler.handleCommand(netCommand);
        } catch (Exception e) {
            logger.error("Exception: ", e);
            throw e;
        }
    }

    public void onClose(int pCloseCode, String pCloseReason) {
        this.fConnection = null;
        this.fCloseLatch.countDown();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.fCloseLatch.await(duration, unit);
    }

    private boolean send(ClientCommandReplay pCommand) throws IOException {
        if (pCommand == null || !this.isOpen()) {
            return false;
        }
        String textMessage = null;
        if (this.fCommandCompression) {
            try {
                textMessage = UtilJson.deflateToBase64(pCommand.toJsonValue());
            } catch (IOException ex) {
                logger.error("Could not compress payload", ex);
            }
        } else {
            JsonValue jsonValue = pCommand.toJsonValue();
            if (jsonValue != null) {
                textMessage = jsonValue.toString();
            }
        }
        if (!StringTool.isProvided(textMessage)) {
            return false;
        }
        this.fConnection.sendMessage(textMessage);
        return true;
    }

    private boolean isOpen() {
        return this.fConnection != null && this.fConnection.isOpen();
    }
}

