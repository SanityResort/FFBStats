/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.net;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.json.LZString;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandPong;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonValue;

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
public class CommandEndpoint {
    private FantasyFootballClient fClient;
    private NetCommandFactory fNetCommandFactory;
    private boolean fCommandCompression;
    private Session fSession;
    private final CountDownLatch fCloseLatch;

    public CommandEndpoint(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fNetCommandFactory = new NetCommandFactory();
        this.fCloseLatch = new CountDownLatch(1);
        String commandCompressionProperty = null;
        if (this.fClient != null) {
            commandCompressionProperty = this.fClient.getProperty("client.command.compression");
        }
        this.fCommandCompression = false;
        if (StringTool.isProvided(commandCompressionProperty)) {
            this.fCommandCompression = Boolean.parseBoolean(commandCompressionProperty);
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.fSession = session;
    }

    @OnMessage
    public void onMessage(String pTextMessage) {
        if (!StringTool.isProvided(pTextMessage) || !this.isOpen()) {
            return;
        }
        JsonValue jsonValue = JsonValue.readFrom(this.fCommandCompression ? LZString.decompressFromUTF16(pTextMessage) : pTextMessage);
        this.handleNetCommand(this.fNetCommandFactory.forJsonValue(jsonValue));
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.fClient.getUserInterface().getStatusReport().reportSocketClosed();
        this.fCloseLatch.countDown();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.fCloseLatch.await(duration, unit);
    }

    public boolean send(NetCommand pCommand) throws IOException {
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

    public boolean isOpen() {
        return this.fSession != null && this.fSession.isOpen();
    }

    private void handleNetCommand(NetCommand netCommand) {
        if (netCommand == null) {
            return;
        }
        if (NetCommandId.SERVER_PONG == netCommand.getId()) {
            ServerCommandPong pongCommand = (ServerCommandPong)netCommand;
            if (pongCommand.getTimestamp() > 0) {
                long received = System.currentTimeMillis();
                GameTitle gameTitle = new GameTitle();
                gameTitle.setPingTime(received - pongCommand.getTimestamp());
            }
        } else {
            this.fClient.getCommunication().handleCommand(netCommand);
        }
    }
}

