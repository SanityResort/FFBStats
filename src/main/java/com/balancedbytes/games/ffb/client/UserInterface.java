/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.StringTool;
import com.fumbbl.rng.MouseEntropySource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;

public class UserInterface
extends JFrame
implements WindowListener{
    public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    private FantasyFootballClient fClient;
    private FieldComponent fFieldComponent;
    private StatusReport fStatusReport;
    private IconCache fIconCache;
    private JDesktopPane fDesktop;
    private GameTitle fGameTitle;
    private PlayerIconFactory fPlayerIconFactory;
    private MouseEntropySource fMouseEntropySource;

    public UserInterface(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fIconCache = new IconCache(this.getClient());
        this.fIconCache.init();
        this.setGameTitle(new GameTitle());
        this.fFieldComponent = new FieldComponent(this.getClient());
        this.fPlayerIconFactory = new PlayerIconFactory();
        this.fStatusReport = new StatusReport(this.getClient());
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, 0));
        fieldPanel.add(this.fFieldComponent);
        JPanel logChatPanel = new JPanel();
        logChatPanel.setLayout(new BoxLayout(logChatPanel, 0));
        logChatPanel.add(Box.createHorizontalStrut(2));
        logChatPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, 1));
        panelCenter.add(fieldPanel);
        panelCenter.add(logChatPanel);
        JPanel panelHome = new JPanel();
        panelHome.setLayout(new BoxLayout(panelHome, 1));
        JPanel panelAway = new JPanel();
        panelAway.setLayout(new BoxLayout(panelAway, 1));
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
    }

    public FieldComponent getFieldComponent() {
        return this.fFieldComponent;
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public IconCache getIconCache() {
        return this.fIconCache;
    }

    public GameTitle getGameTitle() {
        return this.fGameTitle;
    }

    public void setGameTitle(GameTitle pGameTitle) {
        this.fGameTitle = pGameTitle;
        this.refreshTitle();
    }

    public void refreshTitle() {
        this.setTitle(this.fGameTitle != null ? this.fGameTitle.toString() : "FantasyFootball");
    }

    public void refreshSideBars() {
    }

    public void refresh() {
        this.refreshSideBars();
        this.getFieldComponent().refresh();
    }

    public void init() {
        this.getFieldComponent().init();
        Game game = this.getClient().getGame();
        GameTitle gameTitle = new GameTitle();
        gameTitle.setClientMode(this.getClient().getMode());
        gameTitle.setHomeCoach(game.getTeamHome().getCoach());
        gameTitle.setAwayCoach(game.getTeamAway().getCoach());
        gameTitle.setGameTime(game.getGameTime());
        gameTitle.setTurnTime(game.getTurnTime());
        this.setGameTitle(gameTitle);
        String volumeSetting = this.getClient().getProperty("setting.sound.volume");
        int volume = StringTool.isProvided(volumeSetting) ? Integer.parseInt(volumeSetting) : 50;
    }

    public JDesktopPane getDesktop() {
        return this.fDesktop;
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

    public void invokeLater(Runnable pRunnable) {
        SwingUtilities.invokeLater(pRunnable);
    }

    public MouseEntropySource getMouseEntropySource() {
        return this.fMouseEntropySource;
    }

    @Override
    public void windowClosing(WindowEvent pE) {
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

}

