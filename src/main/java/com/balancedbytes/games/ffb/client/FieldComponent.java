/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.BloodSpot;
import com.balancedbytes.games.ffb.DiceDecoration;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldMarker;
import com.balancedbytes.games.ffb.MoveSquare;
import com.balancedbytes.games.ffb.PlayerMarker;
import com.balancedbytes.games.ffb.PushbackSquare;
import com.balancedbytes.games.ffb.RangeRuler;
import com.balancedbytes.games.ffb.TrackNumber;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.layer.FieldLayerBloodspots;
import com.balancedbytes.games.ffb.client.layer.FieldLayerMarker;
import com.balancedbytes.games.ffb.client.layer.FieldLayerOverPlayers;
import com.balancedbytes.games.ffb.client.layer.FieldLayerPitch;
import com.balancedbytes.games.ffb.client.layer.FieldLayerPlayers;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeGrid;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeRuler;
import com.balancedbytes.games.ffb.client.layer.FieldLayerTeamLogo;
import com.balancedbytes.games.ffb.client.layer.FieldLayerUnderPlayers;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.change.IModelChangeObserver;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.fumbbl.rng.MouseEntropySource;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.event.MouseInputListener;

public class FieldComponent
extends JPanel
implements IModelChangeObserver,
MouseInputListener {
    private FantasyFootballClient fClient;
    private FieldLayerPitch fLayerField;
    private FieldLayerTeamLogo fLayerTeamLogo;
    private FieldLayerBloodspots fLayerBloodspots;
    private FieldLayerRangeGrid fLayerRangeGrid;
    private FieldLayerMarker fLayerMarker;
    private FieldLayerUnderPlayers fLayerUnderPlayers;
    private FieldLayerPlayers fLayerPlayers;
    private FieldLayerOverPlayers fLayerOverPlayers;
    private FieldLayerRangeRuler fLayerRangeRuler;
    private BufferedImage fImage;
    private FieldCoordinate fBallCoordinate;
    private FieldCoordinate fBombCoordinate;
    private Map<String, FieldCoordinate> fCoordinateByPlayerId;

    public FieldComponent(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fLayerField = new FieldLayerPitch(pClient);
        this.fLayerTeamLogo = new FieldLayerTeamLogo(pClient);
        this.fLayerBloodspots = new FieldLayerBloodspots(pClient);
        this.fLayerRangeGrid = new FieldLayerRangeGrid(pClient);
        this.fLayerMarker = new FieldLayerMarker(pClient);
        this.fLayerUnderPlayers = new FieldLayerUnderPlayers(pClient);
        this.fLayerPlayers = new FieldLayerPlayers(pClient);
        this.fLayerOverPlayers = new FieldLayerOverPlayers(pClient);
        this.fLayerRangeRuler = new FieldLayerRangeRuler(pClient);
        this.fCoordinateByPlayerId = new HashMap<String, FieldCoordinate>();
        this.fImage = new BufferedImage(782, 452, 2);
        Dimension size = new Dimension(782, 452);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        ToolTipManager.sharedInstance().registerComponent(this);
        this.refresh();
    }

    public FieldLayerPitch getLayerField() {
        return this.fLayerField;
    }

    public FieldLayerTeamLogo getLayerTeamLogo() {
        return this.fLayerTeamLogo;
    }

    public FieldLayerBloodspots getLayerBloodspots() {
        return this.fLayerBloodspots;
    }

    public FieldLayerRangeGrid getLayerRangeGrid() {
        return this.fLayerRangeGrid;
    }

    public FieldLayerMarker getLayerMarker() {
        return this.fLayerMarker;
    }

    public FieldLayerUnderPlayers getLayerUnderPlayers() {
        return this.fLayerUnderPlayers;
    }

    public FieldLayerPlayers getLayerPlayers() {
        return this.fLayerPlayers;
    }

    public FieldLayerOverPlayers getLayerOverPlayers() {
        return this.fLayerOverPlayers;
    }

    public FieldLayerRangeRuler getLayerRangeRuler() {
        return this.fLayerRangeRuler;
    }

    public void refresh() {
        Rectangle updatedArea = this.combineRectangles(new Rectangle[]{this.getLayerField().fetchUpdatedArea(), this.getLayerTeamLogo().fetchUpdatedArea(), this.getLayerBloodspots().fetchUpdatedArea(), this.getLayerRangeGrid().fetchUpdatedArea(), this.getLayerMarker().fetchUpdatedArea(), this.getLayerUnderPlayers().fetchUpdatedArea(), this.getLayerPlayers().fetchUpdatedArea(), this.getLayerOverPlayers().fetchUpdatedArea(), this.getLayerRangeRuler().fetchUpdatedArea()});
        if (updatedArea != null) {
            this.refresh(updatedArea);
        }
    }

    public void refresh(Rectangle pUpdatedArea) {
        Graphics2D g2d = this.fImage.createGraphics();
        if (pUpdatedArea != null) {
            g2d.setClip(pUpdatedArea.x, pUpdatedArea.y, pUpdatedArea.width, pUpdatedArea.height);
        }
        g2d.drawImage(this.getLayerField().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerTeamLogo().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerBloodspots().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerRangeGrid().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerMarker().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerUnderPlayers().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerPlayers().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerOverPlayers().getImage(), 0, 0, null);
        g2d.drawImage(this.getLayerRangeRuler().getImage(), 0, 0, null);
        g2d.dispose();
        if (pUpdatedArea != null) {
            this.repaint(pUpdatedArea);
        } else {
            this.repaint();
        }
    }

    @Override
    public void update(ModelChange pModelChange) {
        if (pModelChange == null || pModelChange.getChangeId() == null) {
            return;
        }
        Game game = this.getClient().getGame();
        FieldModel fieldModel = game.getFieldModel();
        switch (pModelChange.getChangeId()) {
            case FIELD_MODEL_ADD_BLOOD_SPOT: {
                this.getLayerBloodspots().drawBloodspot((BloodSpot)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_ADD_DICE_DECORATION: {
                this.getLayerOverPlayers().drawDiceDecoration((DiceDecoration)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_ADD_FIELD_MARKER: {
                this.getLayerMarker().drawFieldMarker((FieldMarker)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_ADD_MOVE_SQUARE: {
                this.getLayerOverPlayers().drawMoveSquare((MoveSquare)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_ADD_PLAYER_MARKER: {
                this.getLayerPlayers().updatePlayerMarker((PlayerMarker)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_ADD_PUSHBACK_SQUARE: {
                this.getLayerOverPlayers().drawPushbackSquare((PushbackSquare)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_ADD_TRACK_NUMBER: {
                this.getLayerUnderPlayers().drawTrackNumber((TrackNumber)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_REMOVE_DICE_DECORATION: {
                this.getLayerOverPlayers().removeDiceDecoration((DiceDecoration)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_REMOVE_FIELD_MARKER: {
                this.getLayerMarker().removeFieldMarker((FieldMarker)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_REMOVE_MOVE_SQUARE: {
                this.getLayerOverPlayers().removeMoveSquare((MoveSquare)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_REMOVE_PLAYER_MARKER: {
                this.getLayerPlayers().updatePlayerMarker((PlayerMarker)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_REMOVE_PUSHBACK_SQUARE: {
                this.getLayerOverPlayers().removePushbackSquare((PushbackSquare)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_REMOVE_TRACK_NUMBER: {
                this.getLayerUnderPlayers().removeTrackNumber((TrackNumber)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_SET_BALL_COORDINATE: {
                FieldCoordinate ballCoordinate;
                if (this.fBallCoordinate != null) {
                    this.getLayerPlayers().updateBallAndPlayers(this.fBallCoordinate, false);
                }
                if ((ballCoordinate = (FieldCoordinate)pModelChange.getValue()) != null) {
                    this.getLayerPlayers().updateBallAndPlayers(ballCoordinate, false);
                }
                this.fBallCoordinate = ballCoordinate;
                break;
            }
            case FIELD_MODEL_SET_BALL_MOVING: {
                this.getLayerPlayers().updateBallAndPlayers(fieldModel.getBallCoordinate(), false);
                break;
            }
            case FIELD_MODEL_SET_BOMB_COORDINATE: {
                FieldCoordinate bombCoordinate;
                if (this.fBombCoordinate != null) {
                    this.getLayerPlayers().updateBallAndPlayers(this.fBombCoordinate, false);
                }
                if ((bombCoordinate = (FieldCoordinate)pModelChange.getValue()) != null) {
                    this.getLayerPlayers().updateBallAndPlayers(bombCoordinate, false);
                }
                this.fBombCoordinate = bombCoordinate;
                break;
            }
            case FIELD_MODEL_SET_BOMB_MOVING: {
                this.getLayerPlayers().updateBallAndPlayers(fieldModel.getBombCoordinate(), false);
                break;
            }
            case FIELD_MODEL_SET_PLAYER_COORDINATE: {
                FieldCoordinate playerCoordinate;
                FieldCoordinate oldPlayerCoordinate = this.fCoordinateByPlayerId.get(pModelChange.getKey());
                if (oldPlayerCoordinate != null) {
                    this.getLayerPlayers().updateBallAndPlayers(oldPlayerCoordinate, true);
                }
                if ((playerCoordinate = (FieldCoordinate)pModelChange.getValue()) != null) {
                    this.getLayerPlayers().updateBallAndPlayers(playerCoordinate, true);
                }
                this.fCoordinateByPlayerId.put(pModelChange.getKey(), playerCoordinate);
                break;
            }
            case FIELD_MODEL_SET_PLAYER_STATE: {
                Player player = game.getPlayerById(pModelChange.getKey());
                this.getLayerPlayers().updateBallAndPlayers(fieldModel.getPlayerCoordinate(player), true);
                break;
            }
            case FIELD_MODEL_SET_RANGE_RULER: {
                this.getLayerRangeRuler().drawRangeRuler((RangeRuler)pModelChange.getValue());
                break;
            }
            case FIELD_MODEL_SET_WEATHER: {
                this.getLayerField().drawWeather((Weather)pModelChange.getValue());
                break;
            }
        }
    }

    public void init() {
        Game game = this.getClient().getGame();
        game.addObserver(this);
        this.initPlayerCoordinates();
        this.getLayerField().init();
        this.getLayerTeamLogo().init();
        this.getLayerBloodspots().init();
        this.getLayerRangeGrid().init();
        this.getLayerMarker().init();
        this.getLayerUnderPlayers().init();
        this.getLayerPlayers().init();
        this.getLayerOverPlayers().init();
        this.getLayerRangeRuler().init();
        this.refresh();
    }

    private void initPlayerCoordinates() {
        Game game = this.getClient().getGame();
        for (Player player : game.getPlayers()) {
            this.fCoordinateByPlayerId.put(player.getId(), game.getFieldModel().getPlayerCoordinate(player));
        }
    }

    private Rectangle combineRectangles(Rectangle[] pRectangles) {
        Rectangle result = null;
        for (int i = 0; i < pRectangles.length; ++i) {
            if (pRectangles[i] == null) continue;
            if (result != null) {
                result.add(pRectangles[i]);
                continue;
            }
            result = pRectangles[i];
        }
        return result;
    }

    @Override
    protected void paintComponent(Graphics pGraphics) {
        pGraphics.drawImage(this.fImage, 0, 0, null);
    }

    @Override
    public void mouseMoved(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mouseMoved(pMouseEvent);
        }
    }

    @Override
    public void mouseDragged(MouseEvent pMouseEvent) {
        this.getClient().getUserInterface().getMouseEntropySource().reportMousePosition(pMouseEvent);
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mouseDragged(pMouseEvent);
        }
    }

    @Override
    public void mouseClicked(MouseEvent pMouseEvent) {
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mouseClicked(pMouseEvent);
        }
    }

    @Override
    public void mouseEntered(MouseEvent pMouseEvent) {
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mouseEntered(pMouseEvent);
        }
    }

    @Override
    public void mouseExited(MouseEvent pMouseEvent) {
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mouseExited(pMouseEvent);
        }
    }

    @Override
    public void mousePressed(MouseEvent pMouseEvent) {
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mousePressed(pMouseEvent);
        }
    }

    @Override
    public void mouseReleased(MouseEvent pMouseEvent) {
        ClientState uiState = this.getClient().getClientState();
        if (uiState != null) {
            uiState.mouseReleased(pMouseEvent);
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public BufferedImage getImage() {
        return this.fImage;
    }

}

