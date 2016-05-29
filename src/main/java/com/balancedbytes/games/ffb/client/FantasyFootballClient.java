/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.websocket.WebSocket
 *  org.eclipse.jetty.websocket.WebSocket$Connection
 *  org.eclipse.jetty.websocket.WebSocketClient
 *  org.eclipse.jetty.websocket.WebSocketClientFactory
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.dialog.DialogAboutHandler;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerFactory;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.net.ClientPingTask;
import com.balancedbytes.games.ffb.client.net.CommandSocket;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.state.ClientStateFactory;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.IConnectionListener;
import com.balancedbytes.games.ffb.util.StringTool;
import org.eclipse.jetty.websocket.WebSocketClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class FantasyFootballClient
implements IConnectionListener,
IDialogCloseListener {
    public static final String CLIENT_VERSION = "1.2.5";
    public static final String SERVER_VERSION = "1.2.5";
    private Game fGame;
    private UserInterface fUserInterface;
    private ClientCommunication fCommunication;
    private Thread fCommunicationThread;
    private Timer fPingTimer;
    private Timer fTurnTimer;
    private TurnTimerTask fTurnTimerTask;
    private ClientPingTask fClientPingTask;
    private Properties fProperties;
    private ClientState fState;
    private ClientStateFactory fStateFactory;
    private ClientCommandHandlerFactory fCommandHandlerFactory;
    private boolean fConnectionEstablished;
    private ActionKeyBindings fActionKeyBindings;
    private ClientReplayer fReplayer;
    private ClientParameters fParameters;
    private ClientMode fMode;
//    private WebSocketClientFactory fWebSocketClientFactory;
    private WebSocketClient fWebSocketClient;
    private CommandSocket fCommandSocket;
    private transient ClientData fClientData;

    public FantasyFootballClient(ClientParameters pParameters) throws IOException {
        this.fParameters = pParameters;
        this.setMode(this.fParameters.getMode());
        this.fClientData = new ClientData();
        this.loadProperties();
        this.fActionKeyBindings = new ActionKeyBindings(this);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        }
        catch (Exception all) {
            all.printStackTrace();
        }
        this.setGame(new Game());
        this.fStateFactory = new ClientStateFactory(this);
        this.fCommandHandlerFactory = new ClientCommandHandlerFactory(this);
        this.fReplayer = new ClientReplayer(this);
        this.fUserInterface = new UserInterface(this);
        this.fUserInterface.refreshSideBars();
        this.fUserInterface.getScoreBar().refresh();
  //      this.fWebSocketClientFactory = new WebSocketClientFactory();
        this.fCommandSocket = new CommandSocket(this);
        this.fCommunication = new ClientCommunication(this);
        this.fCommunicationThread = new Thread(this.fCommunication);
        this.fCommunicationThread.start();
        this.fPingTimer = new Timer(true);
        this.fTurnTimer = new Timer(true);
    }

    public UserInterface getUserInterface() {
        return this.fUserInterface;
    }

    public Game getGame() {
        return this.fGame;
    }

    public void setGame(Game pGame) {
        this.fGame = pGame;
        this.getClientData().clear();
    }

    public ClientCommunication getCommunication() {
        return this.fCommunication;
    }

    @Override
    public void connectionEstablished(boolean pSuccessful) {
        this.fConnectionEstablished = pSuccessful;
        FantasyFootballClient fantasyFootballClient = this;
        synchronized (fantasyFootballClient) {
            this.notify();
        }
    }

    public void showUserInterface() throws IOException {
        this.getUserInterface().getFieldComponent().getLayerField().drawWeather(Weather.INTRO);
        this.getUserInterface().getFieldComponent().refresh();
        this.getUserInterface().setVisible(true);
        DialogAboutHandler aboutDialogHandler = new DialogAboutHandler(this);
        aboutDialogHandler.showDialog();
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        pDialog.hideDialog();
        this.startClient();
    }

    public void startClient() {
        this.getUserInterface().getStatusReport().reportVersion();
        try {
            this.getUserInterface().getStatusReport().reportConnecting(this.getServerHost(), this.getServerPort());
        }
        catch (UnknownHostException pUnknownHostException) {
            throw new FantasyFootballException(pUnknownHostException);
        }
        boolean connectionEstablished = false;
    /*    try {
            this.fWebSocketClientFactory.start();
            URI uri = new URI("ws", null, this.getServerHost().getCanonicalHostName(), this.getServerPort(), "/command", null, null);
            this.fWebSocketClient = this.fWebSocketClientFactory.newWebSocketClient();
            WebSocket.Connection connection = (WebSocket.Connection)this.fWebSocketClient.open(uri, (WebSocket)this.fCommandSocket).get();
            connectionEstablished = connection != null;
        }
        catch (Exception pAnyException) {
            pAnyException.printStackTrace();
        }
      */  this.getUserInterface().getStatusReport().reportConnectionEstablished(connectionEstablished);
        if (ClientMode.REPLAY != this.getMode()) {
            this.fTurnTimerTask = new TurnTimerTask(this);
            this.fTurnTimer.scheduleAtFixedRate((TimerTask)this.fTurnTimerTask, 0, 1000);
        }
        this.updateClientState();
    }

    public void stopClient() {
        this.fPingTimer = null;
        this.fTurnTimer = null;
        try {
         //   this.fWebSocketClientFactory.stop();
            this.fCommandSocket.awaitClose(10, TimeUnit.SECONDS);
        }
        catch (Exception pAnyException) {
            pAnyException.printStackTrace();
        }
        this.getCommunication().stop();
        this.getUserInterface().setVisible(false);
        System.exit(0);
    }

    public String getProperty(String pProperty) {
        return this.fProperties.getProperty(pProperty);
    }

    public void setProperty(String pProperty, String pValue) {
        if (this.fProperties == null || pProperty == null || pValue == null) {
            return;
        }
        this.fProperties.setProperty(pProperty, pValue);
        if ("client.ping.interval".equals(pProperty) && StringTool.isProvided(pValue)) {
            int pingInterval = Integer.parseInt(pValue);
            String pingMaxDelayProperty = this.getProperty("client.ping.maxDelay");
            int pingMaxDelay = StringTool.isProvided(pingMaxDelayProperty) ? Integer.parseInt(pingMaxDelayProperty) : 0;
            this.fClientPingTask = new ClientPingTask(this, pingMaxDelay);
            this.fPingTimer.schedule((TimerTask)this.fClientPingTask, 0, (long)pingInterval);
        }
    }

    public ClientState updateClientState() {
        ClientState newState = this.fStateFactory.getStateForGame();
        if (newState != null && newState != this.fState) {
            if (this.fState != null) {
                this.fState.leaveState();
            }
            this.fState = newState;
            if (Boolean.parseBoolean(this.getProperty("client.debug.state"))) {
                this.getCommunication().sendDebugClientState(this.fState.getId());
            }
            this.getUserInterface().getGameMenuBar().changeState(this.fState.getId());
            this.fState.enterState();
        }
        return this.fState;
    }

    public ClientState getClientState() {
        return this.fState;
    }

    public ClientCommandHandlerFactory getCommandHandlerFactory() {
        return this.fCommandHandlerFactory;
    }

    public ClientPingTask getClientPingTask() {
        return this.fClientPingTask;
    }

    public boolean isConnectionEstablished() {
        return this.fConnectionEstablished;
    }

    public static void main(String[] args) {
        try {
            ClientParameters parameters = new ClientParameters();
            parameters.initFrom(args);
            if (!parameters.validate()) {
                System.out.println("java -jar FantasyFootballClient.jar -player -coach <coach>\njava -jar FantasyFootballClient.jar -player -coach <coach> -gameId <gameId>\njava -jar FantasyFootballClient.jar -player -coach <coach> -gameId <gameId> -teamHome <teamName> -teamAway <teamName>\njava -jar FantasyFootballClient.jar -player -coach <coach> -teamId <teamId> -teamName <teamName>\njava -jar FantasyFootballClient.jar -spectator -coach <coach>\njava -jar FantasyFootballClient.jar -spectator -coach <coach> -gameId <gameId>\njava -jar FantasyFootballClient.jar -replay -gameId <gameId>");
                return;
            }
            FantasyFootballClient client = new FantasyFootballClient(parameters);
            client.showUserInterface();
        }
        catch (Exception all) {
            all.printStackTrace(System.err);
        }
    }

    public ActionKeyBindings getActionKeyBindings() {
        return this.fActionKeyBindings;
    }

    public ClientReplayer getReplayer() {
        return this.fReplayer;
    }

    public void loadProperties() throws IOException {
        this.fProperties = new Properties();
        InputStream propertyInputStream = this.getClass().getResourceAsStream("/client.ini");
        this.fProperties.load(propertyInputStream);
        propertyInputStream.close();
    }

    public ClientData getClientData() {
        return this.fClientData;
    }

    public ClientParameters getParameters() {
        return this.fParameters;
    }

    public int getServerPort() {
        if (this.getParameters().getPort() > 0) {
            return this.getParameters().getPort();
        }
        return Integer.parseInt(this.getProperty("server.port"));
    }

    public InetAddress getServerHost() throws UnknownHostException {
        return InetAddress.getByName(this.getProperty("server.host"));
    }

    public ClientMode getMode() {
        return this.fMode;
    }

    public void setMode(ClientMode pMode) {
        this.fMode = pMode;
    }

    public CommandSocket getCommandSocket() {
        return this.fCommandSocket;
    }
}

