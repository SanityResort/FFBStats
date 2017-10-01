/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerFactory;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.net.ClientPingTask;
import com.balancedbytes.games.ffb.client.net.CommandEndpoint;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.state.ClientStateFactory;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.IConnectionListener;
import com.balancedbytes.games.ffb.util.StringTool;

import javax.swing.*;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
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
implements IConnectionListener
 {
    public static final String CLIENT_VERSION = "1.3.0";
    public static final String SERVER_VERSION = "1.3.0";
    private Game fGame;
    private UserInterface fUserInterface;
    private ClientCommunication fCommunication;
    private Thread fCommunicationThread;
    private Timer fPingTimer;
    private ClientPingTask fClientPingTask;
    private Properties fProperties;
    private ClientState fState;
    private ClientStateFactory fStateFactory;
    private ClientCommandHandlerFactory fCommandHandlerFactory;
    private boolean fConnectionEstablished;
    private ActionKeyBindings fActionKeyBindings;
    private ClientParameters fParameters;
    private ClientMode fMode;
    private Session fSession;
    private CommandEndpoint fCommandEndpoint;
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
        this.fUserInterface = new UserInterface(this);
        this.fUserInterface.refreshSideBars();
        this.fCommandEndpoint = new CommandEndpoint(this);
        this.fCommunication = new ClientCommunication(this);
        this.fCommunicationThread = new Thread(this.fCommunication);
        this.fCommunicationThread.start();
        this.fPingTimer = new Timer(true);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
        try {
            URI uri = new URI("ws", null, this.getServerHost().getCanonicalHostName(), this.getServerPort(), "/command", null, null);
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxSessionIdleTimeout(Integer.MAX_VALUE);
            container.setDefaultMaxTextMessageBufferSize(65536);
            this.fCommandEndpoint = new CommandEndpoint(this);
            this.fSession = container.connectToServer(this.fCommandEndpoint, uri);
            connectionEstablished = this.fSession != null;
        }
        catch (Exception pAnyException) {
            pAnyException.printStackTrace();
        }
        String pingIntervalProperty = this.getProperty("client.ping.interval");
        if (StringTool.isProvided(pingIntervalProperty) && ClientMode.REPLAY != this.getMode()) {
            int pingInterval = Integer.parseInt(pingIntervalProperty);
            this.fClientPingTask = new ClientPingTask(this);
            this.fPingTimer.schedule((TimerTask)this.fClientPingTask, 0, (long)pingInterval);
        }
        this.getUserInterface().getStatusReport().reportConnectionEstablished(connectionEstablished);
        this.updateClientState();
    }

    public void exitClient() {
        this.fPingTimer = null;
        try {
            this.fSession.close();
            this.fCommandEndpoint.awaitClose(10, TimeUnit.SECONDS);
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

    public ActionKeyBindings getActionKeyBindings() {
        return this.fActionKeyBindings;
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

    public CommandEndpoint getCommandEndpoint() {
        return this.fCommandEndpoint;
    }
}

