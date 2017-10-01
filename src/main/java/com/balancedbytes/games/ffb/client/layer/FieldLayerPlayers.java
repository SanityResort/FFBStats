/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.layer;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PlayerMarker;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class FieldLayerPlayers
extends FieldLayer {
    public FieldLayerPlayers(FantasyFootballClient pClient) {
        super(pClient);
    }

    public void refresh(FieldCoordinateBounds pBounds) {
        if (pBounds != null) {
            for (FieldCoordinate fieldCoordinate : pBounds.fieldCoordinates()) {
                this.updateBallAndPlayers(fieldCoordinate, false);
            }
        }
    }

    public void updateBallAndPlayers(FieldCoordinate pCoordinate, boolean pPlayerOverBall) {
        if (pCoordinate != null && !pCoordinate.isBoxCoordinate()) {
            Game game = this.getClient().getGame();
            int x = 15 + pCoordinate.getX() * 30 - 20;
            int y = 15 + pCoordinate.getY() * 30 - 20;
            this.clear(x, y, 40, 40, true);
            Graphics2D g2d = this.getImage().createGraphics();
            g2d.setClip(x, y, 40, 40);
            FieldCoordinate[] adjacentCoordinates = game.getFieldModel().findAdjacentCoordinates(pCoordinate, FieldCoordinateBounds.FIELD, 1, true);
            for (int i = 0; i < adjacentCoordinates.length; ++i) {
                if (pPlayerOverBall) {
                    this.drawBall(g2d, adjacentCoordinates[i]);
                    this.drawPlayer(g2d, adjacentCoordinates[i]);
                    this.drawBomb(g2d, adjacentCoordinates[i]);
                    continue;
                }
                this.drawPlayer(g2d, adjacentCoordinates[i]);
                this.drawBall(g2d, adjacentCoordinates[i]);
                this.drawBomb(g2d, adjacentCoordinates[i]);
            }
        }
    }

    private void drawPlayer(Graphics2D pG2d, FieldCoordinate pCoordinate) {
        Player player;
        PlayerIconFactory playerIconFactory;
        BufferedImage icon;
        if (pCoordinate != null && (player = this.getClient().getGame().getFieldModel().getPlayer(pCoordinate)) != null && (icon = (playerIconFactory = this.getClient().getUserInterface().getPlayerIconFactory()).getIcon(this.getClient(), player)) != null) {
            pG2d.drawImage(icon, this.findCenteredIconUpperLeftX(icon, pCoordinate), this.findCenteredIconUpperLeftY(icon, pCoordinate), null);
        }
    }

    private void drawBall(Graphics2D pG2d, FieldCoordinate pCoordinate) {
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (pCoordinate.equals(fieldModel.getBallCoordinate()) && fieldModel.isBallMoving()) {
            IconCache iconCache = userInterface.getIconCache();
            BufferedImage ballIcon = iconCache.getIconByProperty("game.ball");
            if (!fieldModel.isBallInPlay()) {
                ballIcon = PlayerIconFactory.fadeIcon(ballIcon);
            }
            pG2d.drawImage(ballIcon, this.findCenteredIconUpperLeftX(ballIcon, pCoordinate), this.findCenteredIconUpperLeftY(ballIcon, pCoordinate), null);
        }
    }

    private void drawBomb(Graphics2D pG2d, FieldCoordinate pCoordinate) {
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        UserInterface userInterface = this.getClient().getUserInterface();
        if (pCoordinate.equals(fieldModel.getBombCoordinate()) && fieldModel.isBombMoving()) {
            IconCache iconCache = userInterface.getIconCache();
            BufferedImage bombIcon = iconCache.getIconByProperty("game.bomb");
            pG2d.drawImage(bombIcon, this.findCenteredIconUpperLeftX(bombIcon, pCoordinate), this.findCenteredIconUpperLeftY(bombIcon, pCoordinate), null);
        }
    }

    public void updatePlayerMarker(PlayerMarker pPlayerMarker) {
        if (pPlayerMarker == null) {
            return;
        }
        Game game = this.getClient().getGame();
        Player player = game.getPlayerById(pPlayerMarker.getPlayerId());
        if (player == null) {
            return;
        }
        FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(player);
        this.updateBallAndPlayers(playerCoordinate, true);
    }

    @Override
    public void init() {
        this.clear(true);
        FieldModel fieldModel = this.getClient().getGame().getFieldModel();
        if (fieldModel != null) {
            FieldCoordinate[] playerCoordinates = fieldModel.getPlayerCoordinates();
            for (int i = 0; i < playerCoordinates.length; ++i) {
                this.updateBallAndPlayers(playerCoordinates[i], true);
            }
            this.updateBallAndPlayers(fieldModel.getBallCoordinate(), false);
        }
    }
}

