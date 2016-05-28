/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.util.UtilClientGraphics;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.TeamResult;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.util.StringTool;
import com.fumbbl.rng.MouseEntropySource;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class ScoreBarComponent
extends JPanel
implements MouseMotionListener {
    public static final int WIDTH = 782;
    public static final int HEIGHT = 32;
    private static final String _TURN = "Turn";
    private static final Font _SCORE_FONT = new Font("Sans Serif", 1, 24);
    private static final Font _TURN_NUMBER_FONT = new Font("Sans Serif", 1, 22);
    private static final Font _TURN_TEXT_FONT = new Font("Sans Serif", 1, 14);
    private static final Font _SPECTATOR_FONT = new Font("Sans Serif", 1, 14);
    private Rectangle _WEATHER_LOCATION = new Rectangle(681, 0, 100, 32);
    private Rectangle _SPECTATOR_LOCATION = new Rectangle(536, 0, 130, 32);
    private FantasyFootballClient fClient;
    private BufferedImage fImage;
    private int fTurnHome;
    private int fTurnAway;
    private int fHalf;
    private int fScoreHome;
    private int fScoreAway;
    private int fSpectators;
    private Weather fWeather;
    private boolean fRefreshNecessary;

    public ScoreBarComponent(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fImage = new BufferedImage(782, 32, 2);
        this.setLayout(null);
        Dimension size = new Dimension(782, 32);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        ToolTipManager.sharedInstance().registerComponent(this);
        this.fRefreshNecessary = true;
        this.addMouseMotionListener(this);
    }

    private void drawBackground() {
        Graphics2D g2d = this.fImage.createGraphics();
        IconCache iconCache = this.getClient().getUserInterface().getIconCache();
        BufferedImage background = iconCache.getIconByProperty("scorebar.background");
        g2d.drawImage(background, 0, 0, null);
        g2d.dispose();
    }

    private void drawScore() {
        Graphics2D g2d = this.fImage.createGraphics();
        String scoreHome = Integer.toString(this.fScoreHome);
        String scoreAway = Integer.toString(this.fScoreAway);
        g2d.setFont(_SCORE_FONT);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D boundsHome = fontMetrics.getStringBounds(scoreHome, g2d);
        int x = (782 - (int)boundsHome.getWidth()) / 2 - 40;
        int y = (32 + fontMetrics.getHeight()) / 2 - fontMetrics.getDescent() - 1;
        UtilClientGraphics.drawShadowedText(g2d, scoreHome, x, y);
        Rectangle2D boundsAway = fontMetrics.getStringBounds(scoreAway, g2d);
        x = (782 - (int)boundsAway.getWidth()) / 2 + 40;
        UtilClientGraphics.drawShadowedText(g2d, scoreAway, x, y);
        g2d.dispose();
    }

    private void drawTurn() {
        Graphics2D g2d = this.fImage.createGraphics();
        Game game = this.getClient().getGame();
        g2d.setFont(_TURN_TEXT_FONT);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = 4;
        int y = (32 + metrics.getHeight()) / 2 - metrics.getDescent();
        UtilClientGraphics.drawShadowedText(g2d, "Turn", x, y);
        Rectangle2D bounds = metrics.getStringBounds("Turn", g2d);
        x = (int)((double)x + (bounds.getWidth() + 10.0));
        String turn = "" + game.getTurnDataHome().getTurnNr() + " / " + game.getTurnDataAway().getTurnNr();
        g2d.setFont(_TURN_NUMBER_FONT);
        metrics = g2d.getFontMetrics();
        y = (32 + metrics.getHeight()) / 2 - metrics.getDescent() - 1;
        UtilClientGraphics.drawShadowedText(g2d, turn, x, y);
        bounds = metrics.getStringBounds(turn, g2d);
        x = (int)((double)x + (bounds.getWidth() + 10.0));
        String half = game.getHalf() > 2 ? "of Overtime" : (game.getHalf() > 1 ? "of 2nd half" : "of 1st half");
        g2d.setFont(_TURN_TEXT_FONT);
        metrics = g2d.getFontMetrics();
        y = (32 + metrics.getHeight()) / 2 - metrics.getDescent();
        UtilClientGraphics.drawShadowedText(g2d, half, x, y);
        g2d.dispose();
    }

    private void drawSpectators() {
        if (this.fSpectators > 0) {
            Graphics2D g2d = this.fImage.createGraphics();
            IconCache iconCache = this.getClient().getUserInterface().getIconCache();
            BufferedImage spectatorsImage = iconCache.getIconByProperty("scorebar.spectators");
            g2d.drawImage(spectatorsImage, this._SPECTATOR_LOCATION.x, this._SPECTATOR_LOCATION.y, null);
            g2d.setFont(_SPECTATOR_FONT);
            String spectatorString = Integer.toString(this.fSpectators);
            UtilClientGraphics.drawShadowedText(g2d, spectatorString, this._SPECTATOR_LOCATION.x + 108, 21);
            g2d.dispose();
        }
    }

    private void drawWeather() {
        if (this.fWeather != null) {
            String weatherIconProperty = null;
            switch (this.fWeather) {
                case BLIZZARD: {
                    weatherIconProperty = "weather.blizzard";
                    break;
                }
                case INTRO: {
                    weatherIconProperty = "weather.intro";
                    break;
                }
                case NICE: {
                    weatherIconProperty = "weather.nice";
                    break;
                }
                case POURING_RAIN: {
                    weatherIconProperty = "weather.rain";
                    break;
                }
                case SWELTERING_HEAT: {
                    weatherIconProperty = "weather.heat";
                    break;
                }
                case VERY_SUNNY: {
                    weatherIconProperty = "weather.sunny";
                }
            }
            if (StringTool.isProvided(weatherIconProperty)) {
                IconCache iconCache = this.getClient().getUserInterface().getIconCache();
                BufferedImage weatherIcon = iconCache.getIconByProperty(weatherIconProperty);
                Graphics2D g2d = this.fImage.createGraphics();
                g2d.drawImage(weatherIcon, this._WEATHER_LOCATION.x, this._WEATHER_LOCATION.y, null);
                g2d.dispose();
            }
        }
    }

    public void init() {
        this.fTurnHome = 0;
        this.fTurnAway = 0;
        this.fScoreHome = 0;
        this.fScoreAway = 0;
        this.fSpectators = 0;
        this.fWeather = null;
        this.fRefreshNecessary = true;
        this.refresh();
    }

    public void refresh() {
        Game game = this.getClient().getGame();
        if (game.getHalf() > 0) {
            ClientData clientData = this.getClient().getClientData();
            if (!this.fRefreshNecessary) {
                boolean bl = this.fRefreshNecessary = this.fTurnHome != game.getTurnDataHome().getTurnNr() || this.fTurnAway != game.getTurnDataAway().getTurnNr() || this.fHalf != game.getHalf();
            }
            if (!this.fRefreshNecessary) {
                boolean bl = this.fRefreshNecessary = this.fScoreHome != game.getGameResult().getTeamResultHome().getScore() || this.fTurnAway != game.getGameResult().getTeamResultAway().getScore();
            }
            if (!this.fRefreshNecessary) {
                boolean bl = this.fRefreshNecessary = this.fSpectators != clientData.getSpectators();
            }
            if (!this.fRefreshNecessary) {
                boolean bl = this.fRefreshNecessary = this.fWeather != game.getFieldModel().getWeather();
            }
            if (this.fRefreshNecessary) {
                this.fTurnHome = game.getTurnDataHome().getTurnNr();
                this.fTurnAway = game.getTurnDataAway().getTurnNr();
                this.fHalf = game.getHalf();
                this.fScoreHome = game.getGameResult().getTeamResultHome().getScore();
                this.fScoreAway = game.getGameResult().getTeamResultAway().getScore();
                this.fSpectators = clientData.getSpectators();
                this.fWeather = game.getFieldModel().getWeather();
                this.drawBackground();
                this.drawTurn();
                this.drawScore();
                this.drawSpectators();
                this.drawWeather();
                this.repaint();
                this.fRefreshNecessary = false;
            }
        } else {
            this.drawBackground();
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    @Override
    public String getToolTipText(MouseEvent pMouseEvent) {
        String toolTip = null;
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        if (this.fWeather != null && this._WEATHER_LOCATION.contains(pMouseEvent.getPoint())) {
            StringBuilder weatherInfo = new StringBuilder();
            weatherInfo.append("<html><b>").append(fieldModel.getWeather().getName()).append("</b><br>").append(fieldModel.getWeather().getDescription()).append("</html>");
            toolTip = weatherInfo.toString();
        }
        if (this.fSpectators > 0 && this._SPECTATOR_LOCATION.contains(pMouseEvent.getPoint())) {
            StringBuilder spectatorInfo = new StringBuilder();
            spectatorInfo.append("<html>").append(this.fSpectators);
            spectatorInfo.append(this.fSpectators == 1 ? " spectator is watching the game." : " spectators are watching the game.");
            spectatorInfo.append("</html>");
            toolTip = spectatorInfo.toString();
        }
        return toolTip;
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
    }

}

