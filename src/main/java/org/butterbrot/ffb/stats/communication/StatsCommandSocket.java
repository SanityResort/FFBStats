package org.butterbrot.ffb.stats.communication;

import com.fumbbl.ffb.json.LZString;
import com.fumbbl.ffb.net.NetCommand;
import com.fumbbl.ffb.net.NetCommandFactory;
import com.fumbbl.ffb.net.commands.ClientCommandReplay;
import com.fumbbl.ffb.util.StringTool;
import com.eclipsesource.json.JsonValue;
import org.butterbrot.ffb.stats.conversion.EvaluationFactorySource;
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
    private final EvaluationFactorySource applicationFactorySource;

    private Session fSession;

    private final NetCommandFactory fNetCommandFactory;
    private final boolean fCommandCompression;
    private final CountDownLatch fCloseLatch;
    private final CommandHandler statsHandler;
    private final long replayId;

    public StatsCommandSocket(long replayId, boolean compression, CommandHandler statsHandler) {
        this.replayId = replayId;
        this.statsHandler = statsHandler;
        applicationFactorySource = new EvaluationFactorySource();
        this.fNetCommandFactory = new NetCommandFactory(applicationFactorySource);
        this.fCloseLatch = new CountDownLatch(1);
        this.fCommandCompression = compression;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig ignoredEndpointConfig) {
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
            if (!StringTool.isProvided(pTextMessage) || this.isClosed()) {
                return;
            }
            JsonValue jsonValue = JsonValue.readFrom(this.fCommandCompression ? LZString.decompressFromUTF16(pTextMessage) : pTextMessage);

            NetCommand netCommand = this.fNetCommandFactory.forJsonValue(applicationFactorySource, jsonValue);
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
    public void onClose(Session ignoredSession, CloseReason ignoredCloseReason) {
        this.fCloseLatch.countDown();
    }

    public void awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        //noinspection ResultOfMethodCallIgnored
        this.fCloseLatch.await(duration, unit);
    }

    private void send(ClientCommandReplay pCommand) throws IOException {
        if (pCommand == null || this.isClosed()) {
            return;
        }
        JsonValue jsonValue = pCommand.toJsonValue();
        if (jsonValue == null) {
            return;
        }
        String textMessage = jsonValue.toString();
        if (this.fCommandCompression) {
            textMessage = LZString.compressToUTF16(textMessage);
        }
        if (!StringTool.isProvided(textMessage)) {
            return;
        }
        this.fSession.getAsyncRemote().sendText(textMessage);
    }

    private boolean isClosed() {
        return this.fSession == null || !this.fSession.isOpen();
    }
}

