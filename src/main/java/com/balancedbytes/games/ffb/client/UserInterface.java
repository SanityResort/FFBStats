/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.dialog.DialogLeaveGame;
import com.balancedbytes.games.ffb.client.dialog.DialogManager;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.sound.SoundEngine;
import com.balancedbytes.games.ffb.client.ui.ChatComponent;
import com.balancedbytes.games.ffb.client.ui.GameMenuBar;
import com.balancedbytes.games.ffb.client.ui.LogComponent;
import com.balancedbytes.games.ffb.client.ui.ScoreBarComponent;
import com.balancedbytes.games.ffb.client.ui.SideBarComponent;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.StringTool;
import com.fumbbl.rng.MouseEntropySource;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class UserInterface
extends JFrame
implements WindowListener,
IDialogCloseListener {
    public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    private FantasyFootballClient fClient;
    private FieldComponent fFieldComponent;
    private StatusReport fStatusReport;
    private SideBarComponent fSideBarHome;
    private SideBarComponent fSideBarAway;
    private IconCache fIconCache;
    private SoundEngine fSoundEngine;
    private ScoreBarComponent fScoreBar;
    private LogComponent fLog;
    private ChatComponent fChat;
    private DialogManager fDialogManager;
    private JDesktopPane fDesktop;
    private GameTitle fGameTitle;
    private PlayerIconFactory fPlayerIconFactory;
    private MouseEntropySource fMouseEntropySource;

    public UserInterface(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fIconCache = new IconCache(this.getClient());
        this.fIconCache.init();
        this.fSoundEngine = new SoundEngine(this.getClient());
        this.fSoundEngine.init();
        this.fDialogManager = new DialogManager(this.getClient());
        this.setGameMenuBar(new GameMenuBar(this.getClient()));
        this.setGameTitle(new GameTitle());
        this.fFieldComponent = new FieldComponent(this.getClient());
        this.fPlayerIconFactory = new PlayerIconFactory();
        this.fStatusReport = new StatusReport(this.getClient());
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, 0));
        fieldPanel.add(this.fFieldComponent);
        this.fLog = new LogComponent(this.getClient());
        this.fChat = new ChatComponent(this.getClient());
        JPanel logChatPanel = new JPanel();
        logChatPanel.setLayout(new BoxLayout(logChatPanel, 0));
        logChatPanel.add(this.getLog());
        logChatPanel.add(Box.createHorizontalStrut(2));
        logChatPanel.add(this.getChat());
        logChatPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.fScoreBar = new ScoreBarComponent(this.getClient());
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, 1));
        panelCenter.add(fieldPanel);
        panelCenter.add(this.getScoreBar());
        panelCenter.add(logChatPanel);
        this.fSideBarHome = new SideBarComponent(this.getClient(), true);
        JPanel panelHome = new JPanel();
        panelHome.setLayout(new BoxLayout(panelHome, 1));
        panelHome.add(this.fSideBarHome);
        this.fSideBarAway = new SideBarComponent(this.getClient(), false);
        JPanel panelAway = new JPanel();
        panelAway.setLayout(new BoxLayout(panelAway, 1));
        panelAway.add(this.fSideBarAway);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(new BoxLayout(panelContent, 0));
        panelContent.add(panelHome);
        panelContent.add(panelCenter);
        panelContent.add(panelAway);
        this.fDesktop = new JDesktopPane();
        panelContent.setSize(panelContent.getPreferredSize());
        this.fDesktop.add((Component)panelContent, -1);
        this.fDesktop.setPreferredSize(panelContent.getPreferredSize());
        this.fMouseEntropySource = new MouseEntropySource(this);
        this.getContentPane().add((Component)this.fDesktop, "Center");
        this.pack();
        this.setDefaultCloseOperation(0);
        this.addWindowListener(this);
        this.setResizable(false);
        this.getChat().requestChatInputFocus();
    }

    public FieldComponent getFieldComponent() {
        return this.fFieldComponent;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public SideBarComponent getSideBarHome() {
        return this.fSideBarHome;
    }

    public SideBarComponent getSideBarAway() {
        return this.fSideBarAway;
    }

    public IconCache getIconCache() {
        return this.fIconCache;
    }

    public SoundEngine getSoundEngine() {
        return this.fSoundEngine;
    }

    public LogComponent getLog() {
        return this.fLog;
    }

    public ChatComponent getChat() {
        return this.fChat;
    }

    public DialogManager getDialogManager() {
        return this.fDialogManager;
    }

    public GameTitle getGameTitle() {
        UserInterface userInterface = this;
        synchronized (userInterface) {
            return this.fGameTitle;
        }
    }

    public void setGameTitle(GameTitle pGameTitle) {
        UserInterface userInterface = this;
        synchronized (userInterface) {
            this.fGameTitle = pGameTitle;
            this.setTitle(this.fGameTitle.toString());
        }
    }

    public void refreshSideBars() {
        this.getSideBarHome().refresh();
        this.getSideBarAway().refresh();
        this.getScoreBar().refresh();
    }

    public void refresh() {
        this.refreshSideBars();
        this.getFieldComponent().refresh();
        this.getGameMenuBar().refresh();
    }

    public void init() {
        this.getSideBarHome().init();
        this.getSideBarAway().init();
        this.getScoreBar().init();
        this.getFieldComponent().init();
        this.getGameMenuBar().init();
        Game game = this.getClient().getGame();
        GameTitle gameTitle = new GameTitle(this.getGameTitle());
        gameTitle.setClientMode(this.getClient().getMode());
        gameTitle.setHomeCoach(game.getTeamHome().getCoach());
        gameTitle.setAwayCoach(game.getTeamAway().getCoach());
        gameTitle.setGameTime(game.getGameTime());
        gameTitle.setTurnTime(game.getTurnTime());
        this.setGameTitle(gameTitle);
        String volumeSetting = this.getClient().getProperty("setting.sound.volume");
        int volume = StringTool.isProvided(volumeSetting) ? Integer.parseInt(volumeSetting) : 50;
        this.getClient().getUserInterface().getSoundEngine().setVolume(volume);
    }

    public JDesktopPane getDesktop() {
        return this.fDesktop;
    }

    public GameMenuBar getGameMenuBar() {
        return (GameMenuBar)this.getJMenuBar();
    }

    public void setGameMenuBar(GameMenuBar pGameMenuBar) {
        this.setJMenuBar(pGameMenuBar);
    }

    public ScoreBarComponent getScoreBar() {
        return this.fScoreBar;
    }

    public PlayerIconFactory getPlayerIconFactory() {
        return this.fPlayerIconFactory;
    }

    public StatusReport getStatusReport() {
        return this.fStatusReport;
    }

    public void invokeAndWait(Runnable pRunnable) {
        try {
            SwingUtilities.invokeAndWait(pRunnable);
        }
        catch (InterruptedException e) {
            throw new FantasyFootballException(e);
        }
        catch (InvocationTargetException e) {
            throw new FantasyFootballException(e);
        }
    }

    public MouseEntropySource getMouseEntropySource() {
        return this.fMouseEntropySource;
    }

    @Override
    public void windowClosing(WindowEvent pE) {
        DialogLeaveGame leaveGameQuestion = new DialogLeaveGame(this.getClient());
        leaveGameQuestion.showDialog(this);
    }

    @Override
    public void windowActivated(WindowEvent pE) {
    }

    @Override
    public void windowClosed(WindowEvent pE) {
    }

    @Override
    public void windowDeactivated(WindowEvent pE) {
    }

    @Override
    public void windowDeiconified(WindowEvent pE) {
    }

    @Override
    public void windowIconified(WindowEvent pE) {
    }

    @Override
    public void windowOpened(WindowEvent pE) {
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        pDialog.hideDialog();
        if (DialogId.LEAVE_GAME == pDialog.getId() && ((DialogLeaveGame)pDialog).isChoiceYes()) {
            System.exit(0);
        }
    }
}

