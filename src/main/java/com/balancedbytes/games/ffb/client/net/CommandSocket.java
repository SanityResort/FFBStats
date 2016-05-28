/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.websocket.WebSocket
 *  org.eclipse.jetty.websocket.WebSocket$Connection
 *  org.eclipse.jetty.websocket.WebSocket$OnTextMessage
 */
package com.balancedbytes.games.ffb.client.net;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.net.ClientPingTask;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandPing;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonValue;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
//import org.eclipse.jetty.websocket.WebSocket;

public class CommandSocket
//implements WebSocket.OnTextMessage
{
    private FantasyFootballClient fClient;
    private NetCommandFactory fNetCommandFactory;
//    private WebSocket.Connection fConnection;
    private boolean fCommandCompression;
    private final CountDownLatch fCloseLatch;

    public CommandSocket(FantasyFootballClient pClient) {
        String commandCompression;
        this.fClient = pClient;
        this.fNetCommandFactory = new NetCommandFactory();
        this.fCloseLatch = new CountDownLatch(1);
        String string = commandCompression = this.fClient != null ? this.fClient.getProperty("client.command.compression") : null;
        if (StringTool.isProvided(commandCompression)) {
            this.fCommandCompression = Boolean.parseBoolean(commandCompression);
        }
    }

  /*  public void onOpen(WebSocket.Connection pConnection) {
        this.fConnection = pConnection;
        System.out.printf("Got connect: %s%n", new Object[]{this.fConnection});
        this.fConnection.setMaxIdleTime(Integer.MAX_VALUE);
        this.fConnection.setMaxTextMessageSize(65536);
    }
*/
    public void onMessage(String pTextMessage) {
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
        if (NetCommandId.SERVER_PING == netCommand.getId()) {
            ServerCommandPing pingCommand = (ServerCommandPing)netCommand;
            pingCommand.setReceived(System.currentTimeMillis());
            this.fClient.getClientPingTask().setLastPingReceived(pingCommand.getReceived());
        }
        this.fClient.getCommunication().handleCommand(netCommand);
    }

    public void onClose(int pCloseCode, String pCloseReason) {
        System.out.printf("Connection closed: %d - %s%n", pCloseCode, pCloseReason);
        this.fClient.getUserInterface().getStatusReport().reportSocketClosed();
  //      this.fConnection = null;
        this.fCloseLatch.countDown();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.fCloseLatch.await(duration, unit);
    }

    public boolean send(NetCommand pCommand) throws IOException {
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
    //    this.fConnection.sendMessage(textMessage);
        return true;
    }

    public boolean isOpen() {
     return true;//   return this.fConnection != null && this.fConnection.isOpen();
    }
}

