package org.butterbrot.ffb.stats.communication;

import com.balancedbytes.games.ffb.json.LZString;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.commands.ClientCommandReplay;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonValue;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class StatsCommandSocket
{
    private static final Logger logger = LoggerFactory.getLogger(StatsCommandSocket.class);

    private Session fSession;

    private NetCommandFactory fNetCommandFactory;
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

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.fSession = session;
        try {
            send(new ClientCommandReplay(replayId, 0));
        } catch (IOException e) {
            logger.error("Sending command failed", e);
        }
    }

    @OnMessage
    public void onMessage(String pTextMessage) {
        try {
            if (!StringTool.isProvided(pTextMessage) || !this.isOpen()) {
                return;
            }
            JsonValue jsonValue = JsonValue.readFrom(this.fCommandCompression ? LZString.decompressFromUTF16(pTextMessage) : pTextMessage);

            NetCommand netCommand = this.fNetCommandFactory.forJsonValue(jsonValue);
            if (netCommand == null) {
                return;
            }
            statsHandler.handleCommand(netCommand);
        } catch (Exception e) {
            logger.error("Exception: ", e);
            statsHandler.stop();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.fCloseLatch.countDown();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.fCloseLatch.await(duration, unit);
    }

    private boolean send(ClientCommandReplay pCommand) throws IOException {
        if (pCommand == null || !this.isOpen()) {
            return false;
        }
        JsonValue jsonValue = pCommand.toJsonValue();
        if (jsonValue == null) {
            return false;
        }
        String textMessage = jsonValue.toString();
        if (this.fCommandCompression) {
            textMessage = LZString.compressToUTF16(textMessage);
        }
        if (!StringTool.isProvided(textMessage)) {
            return false;
        }
        this.fSession.getAsyncRemote().sendText(textMessage);
        return true;
    }

    private boolean isOpen() {
        return this.fSession != null && this.fSession.isOpen();
    }
}

