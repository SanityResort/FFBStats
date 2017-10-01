/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.MoveSquare;
import com.balancedbytes.games.ffb.PushbackSquare;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class FieldLayerOverPlayers
extends FieldLayer {
    public static final Color COLOR_MOVE_SQUARE = new Color(1.0f, 1.0f, 0.0f, 0.3f);
    public static final Color COLOR_TARGET_NUMBER = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color COLOR_FIREBALL_AREA = new Color(1.0f, 0.0f, 0.0f, 0.4f);
    public static final Color COLOR_FIREBALL_AREA_FADED = new Color(1.0f, 0.0f, 0.0f, 0.2f);
    private FieldCoordinate fThrownPlayerCoordinate;
    private FieldCoordinate fMarkerCoordinate;

    public FieldLayerOverPlayers(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void removeThrownPlayer() {
        if (this.fThrownPlayerCoordinate != null) {
            this.clear(this.fThrownPlayerCoordinate, true);
            this.fThrownPlayerCoordinate = null;
        }
    }

    public void drawThrownPlayer(Game pGame, Player pThrownPlayer, FieldCoordinate pCoordinate, boolean pWithBall) {
        if (pCoordinate != null && pThrownPlayer != null) {
            this.clear(pCoordinate, true);
            this.fThrownPlayerCoordinate = pCoordinate;
            Graphics2D g2d = this.getImage().createGraphics();
            PlayerIconFactory playerIconFactory = this.getClient().getUserInterface().getPlayerIconFactory();
            boolean homePlayer = pGame.getTeamHome().hasPlayer(pThrownPlayer);
            BufferedImage icon = playerIconFactory.getBasicIcon(this.getClient(), pThrownPlayer, homePlayer, false, pWithBall, false);
            if (icon != null) {
                g2d.drawImage(icon, this.findCenteredIconUpperLeftX(icon, pCoordinate), this.findCenteredIconUpperLeftY(icon, pCoordinate), null);
            }
            g2d.dispose();
        }
    }

    public void drawPushbackSquare(PushbackSquare pPushbackSquare) {
        if (pPushbackSquare != null) {
            this.clear(pPushbackSquare.getCoordinate(), true);
            IconCache iconCache = this.getClient().getUserInterface().getIconCache();
            BufferedImage pushbackIcon = iconCache.getIcon(pPushbackSquare);
            this.draw(pushbackIcon, pPushbackSquare.getCoordinate(), 1.0f);
        }
    }

    public void removePushbackSquare(PushbackSquare pPushbackSquare) {
        if (pPushbackSquare != null) {
            this.clear(pPushbackSquare.getCoordinate(), true);
        }
    }

    public void drawDiceDecoration(DiceDecoration pDiceDecoration) {
        this.drawDiceDecoration(pDiceDecoration, true);
    }

    private void drawDiceDecoration(DiceDecoration pDiceDecoration, boolean pClearBeforeDraw) {
        if (pDiceDecoration != null) {
            if (pClearBeforeDraw) {
                this.clear(pDiceDecoration.getCoordinate(), true);
                MoveSquare moveSquare = this.getClient().getGame().getFieldModel().getMoveSquare(pDiceDecoration.getCoordinate());
                if (moveSquare != null) {
                    this.drawMoveSquare(moveSquare, false);
                }
            }
            IconCache iconCache = this.getClient().getUserInterface().getIconCache();
            BufferedImage decorationIcon = iconCache.getIcon(pDiceDecoration);
            this.draw(decorationIcon, pDiceDecoration.getCoordinate(), 1.0f);
        }
    }

    public void removeDiceDecoration(DiceDecoration pDiceDecoration) {
        if (pDiceDecoration != null) {
            this.clear(pDiceDecoration.getCoordinate(), true);
        }
    }

    public void drawMoveSquare(MoveSquare pMoveSquare) {
        this.drawMoveSquare(pMoveSquare, true);
    }

    private void drawMoveSquare(MoveSquare pMoveSquare, boolean pClearBeforeDraw) {
        if (pMoveSquare != null && ClientMode.PLAYER == this.getClient().getMode() && this.getClient().getGame().isHomePlaying()) {
            DiceDecoration diceDecoration;
            if (pClearBeforeDraw) {
                this.clear(pMoveSquare.getCoordinate(), true);
            }
            int x = pMoveSquare.getCoordinate().getX() * 30 + 2;
            int y = pMoveSquare.getCoordinate().getY() * 30 + 2;
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setPaint(COLOR_MOVE_SQUARE);
            Rectangle bounds = new Rectangle(x, y, 26, 26);
            g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2d.setColor(COLOR_TARGET_NUMBER);
            if (pMoveSquare.getMinimumRollGoForIt() > 0 && pMoveSquare.getMinimumRollDodge() > 0) {
                StringBuilder numberGoForIt = new StringBuilder();
                if (pMoveSquare.getMinimumRollGoForIt() < 6) {
                    numberGoForIt.append(pMoveSquare.getMinimumRollGoForIt()).append("+");
                } else {
                    numberGoForIt.append(6);
                }
                g2d.setFont(new Font("Sans Serif", 0, 10));
                FontMetrics metrics = g2d.getFontMetrics();
                Rectangle2D numberBounds = metrics.getStringBounds(numberGoForIt.toString(), g2d);
                x = 15 + pMoveSquare.getCoordinate().getX() * 30 - (int)(numberBounds.getWidth() / 2.0) - 5;
                y = 15 + pMoveSquare.getCoordinate().getY() * 30 + (int)(numberBounds.getHeight() / 2.0) - 9;
                g2d.drawString(numberGoForIt.toString(), x, y);
                x = 15 + pMoveSquare.getCoordinate().getX() * 30 - 10;
                y = 15 + pMoveSquare.getCoordinate().getY() * 30 + 10;
                g2d.drawLine(x, y, x + 20, y - 20);
                StringBuilder numberDodge = new StringBuilder();
                numberDodge.append(pMoveSquare.getMinimumRollDodge()).append("+");
                g2d.setFont(new Font("Sans Serif", 0, 10));
                metrics = g2d.getFontMetrics();
                numberBounds = metrics.getStringBounds(numberDodge.toString(), g2d);
                x = 15 + pMoveSquare.getCoordinate().getX() * 30 - (int)(numberBounds.getWidth() / 2.0) + 7;
                y = 15 + pMoveSquare.getCoordinate().getY() * 30 + (int)(numberBounds.getHeight() / 2.0) + 5;
                g2d.drawString(numberDodge.toString(), x, y);
            } else {
                int minimumRoll = Math.max(pMoveSquare.getMinimumRollGoForIt(), pMoveSquare.getMinimumRollDodge());
                if (minimumRoll > 0) {
                    StringBuilder number = new StringBuilder();
                    if (minimumRoll < 6) {
                        number.append(minimumRoll).append("+");
                    } else {
                        number.append(6);
                    }
                    g2d.setFont(new Font("Sans Serif", 0, 11));
                    FontMetrics metrics = g2d.getFontMetrics();
                    Rectangle2D numberBounds = metrics.getStringBounds(number.toString(), g2d);
                    x = 15 + pMoveSquare.getCoordinate().getX() * 30 - (int)(numberBounds.getWidth() / 2.0) + 1;
                    y = 15 + pMoveSquare.getCoordinate().getY() * 30 + (int)(numberBounds.getHeight() / 2.0) - 2;
                    g2d.drawString(number.toString(), x, y);
                }
            }
            g2d.dispose();
            if (pClearBeforeDraw && (diceDecoration = this.getClient().getGame().getFieldModel().getDiceDecoration(pMoveSquare.getCoordinate())) != null) {
                this.drawDiceDecoration(diceDecoration, false);
            }
        }
    }

    public void removeMoveSquare(MoveSquare pMoveSquare) {
        if (pMoveSquare != null) {
            this.clear(pMoveSquare.getCoordinate(), true);
        }
    }

    public boolean drawLightningMarker(FieldCoordinate pMarkerCoordinate, boolean pFaded) {
        if (pMarkerCoordinate != null && !pMarkerCoordinate.equals(this.fMarkerCoordinate)) {
            this.fMarkerCoordinate = pMarkerCoordinate;
            this.clear(this.fMarkerCoordinate, true);
            int x = this.fMarkerCoordinate.getX() * 30;
            int y = this.fMarkerCoordinate.getY() * 30;
            Graphics2D g2d = this.getImage().createGraphics();
            IconCache iconCache = this.getClient().getUserInterface().getIconCache();
            BufferedImage lightningIcon = iconCache.getIconByProperty("game.lightning.small");
            if (pFaded) {
                g2d.setComposite(AlphaComposite.getInstance(3, 0.5f));
            }
            g2d.drawImage(lightningIcon, x, y, null);
            g2d.dispose();
            return true;
        }
        return false;
    }

    public boolean clearLightningMarker() {
        if (this.fMarkerCoordinate != null) {
            this.clear(this.fMarkerCoordinate, true);
            this.fMarkerCoordinate = null;
            return true;
        }
        return false;
    }

    public boolean drawFireballMarker(FieldCoordinate pMarkerCoordinate, boolean pFaded) {
        if (pMarkerCoordinate != null && !pMarkerCoordinate.equals(this.fMarkerCoordinate)) {
            FieldCoordinate[] markedSquares;
            this.fMarkerCoordinate = pMarkerCoordinate;
            this.clear(this.fMarkerCoordinate, true);
            int x = this.fMarkerCoordinate.getX() * 30;
            int y = this.fMarkerCoordinate.getY() * 30;
            Graphics2D g2d = this.getImage().createGraphics();
            IconCache iconCache = this.getClient().getUserInterface().getIconCache();
            BufferedImage lightningIcon = iconCache.getIconByProperty("game.fireball.small");
            if (pFaded) {
                g2d.setComposite(AlphaComposite.getInstance(3, 0.6f));
            }
            g2d.drawImage(lightningIcon, x, y, null);
            Game game = this.getClient().getGame();
            for (FieldCoordinate markedSquare : markedSquares = game.getFieldModel().findAdjacentCoordinates(this.fMarkerCoordinate, FieldCoordinateBounds.FIELD, 1, false)) {
                if (pFaded) {
                    this.markSquare(markedSquare, COLOR_FIREBALL_AREA_FADED);
                    continue;
                }
                this.markSquare(markedSquare, COLOR_FIREBALL_AREA);
            }
            g2d.dispose();
            return true;
        }
        return false;
    }

    public boolean clearFireballMarker() {
        if (this.fMarkerCoordinate != null) {
            FieldCoordinate[] markedSquares;
            Game game = this.getClient().getGame();
            for (FieldCoordinate markedSquare : markedSquares = game.getFieldModel().findAdjacentCoordinates(this.fMarkerCoordinate, FieldCoordinateBounds.FIELD, 1, true)) {
                this.clear(markedSquare, true);
            }
            this.fMarkerCoordinate = null;
            return true;
        }
        return false;
    }

    private void markSquare(FieldCoordinate pCoordinate, Color pColor) {
        if (pCoordinate != null) {
            this.clear(pCoordinate, true);
            int x = pCoordinate.getX() * 30;
            int y = pCoordinate.getY() * 30;
            Rectangle bounds = new Rectangle(x + 1, y + 1, 28, 28);
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setPaint(pColor);
            g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2d.dispose();
        }
    }

    @Override
    public void init() {
        this.clear(true);
        Game game = this.getClient().getGame();
        FieldModel fieldModel = game.getFieldModel();
        if (fieldModel != null) {
            for (PushbackSquare pushbackSquare : fieldModel.getPushbackSquares()) {
                this.drawPushbackSquare(pushbackSquare);
            }
        }
    }
}

