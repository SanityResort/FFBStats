/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.util;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;

import java.awt.event.MouseEvent;

public class UtilClientPlayerDrag {
    public static FieldCoordinate getFieldCoordinate(FantasyFootballClient pClient, MouseEvent pMouseEvent, boolean pBoxMode) {
        FieldCoordinate coordinate = null;
        if (pBoxMode) {
            coordinate = UtilClientPlayerDrag.getBoxFieldCoordinate(pClient, pMouseEvent.getX(), pMouseEvent.getY());
            if (coordinate == null && pMouseEvent.getX() >= 116) {
                coordinate = UtilClientPlayerDrag.getFieldFieldCoordinate(pClient, pMouseEvent.getX() - 116, pMouseEvent.getY());
            }
        } else {
            coordinate = UtilClientPlayerDrag.getFieldFieldCoordinate(pClient, pMouseEvent.getX(), pMouseEvent.getY());
            if (coordinate == null && pMouseEvent.getX() < 0) {
                coordinate = UtilClientPlayerDrag.getBoxFieldCoordinate(pClient, 116 + pMouseEvent.getX(), pMouseEvent.getY());
            }
        }
        return coordinate;
    }

    private static FieldCoordinate getFieldFieldCoordinate(FantasyFootballClient pClient, int pMouseX, int pMouseY) {
        if (pMouseX >= 0 && pMouseX < 782 && pMouseY >= 0 && pMouseY < 452) {
            return new FieldCoordinate(pMouseX / 30, pMouseY / 30);
        }
        return null;
    }

    private static FieldCoordinate getBoxFieldCoordinate(FantasyFootballClient pClient, int pMouseX, int pMouseY) {
        int boxTitleOffset;
        int y;
        return null;
    }

    public static void mousePressed(FantasyFootballClient pClient, MouseEvent pMouseEvent, boolean pBoxMode) {
        FieldCoordinate coordinate = UtilClientPlayerDrag.getFieldCoordinate(pClient, pMouseEvent, pBoxMode);
        UtilClientPlayerDrag.initPlayerDragging(pClient, coordinate, pBoxMode);
    }

    private static void initPlayerDragging(FantasyFootballClient pClient, FieldCoordinate pCoordinate, boolean pBoxMode) {
        boolean initDragAllowed;
        Game game = pClient.getGame();
        ClientData clientData = pClient.getClientData();
        UserInterface userInterface = pClient.getUserInterface();
        Player player = game.getFieldModel().getPlayer(pCoordinate);
        PlayerState playerState = game.getFieldModel().getPlayerState(player);
        boolean bl = initDragAllowed = ClientMode.PLAYER == pClient.getMode() && player != null && game.getTeamHome().hasPlayer(player) && pClient.getClientState().isInitDragAllowed(pCoordinate);
        if (initDragAllowed) {
            if (pBoxMode) {
                initDragAllowed = playerState.getBase() == 1 && playerState.isActive() || playerState.getBase() == 9 || playerState.getBase() == 15;
            } else {
                boolean bl2 = initDragAllowed = playerState.getBase() == 1 && playerState.isActive() || playerState.getBase() == 15;
            }
        }
        if (initDragAllowed) {
            if (player != null) {
                clientData.setSelectedPlayer(player);
                clientData.setDragStartPosition(pCoordinate);
                clientData.setDragEndPosition(pCoordinate);
                game.getFieldModel().setPlayerState(player, playerState.changeBase(15));
                if (pBoxMode) {
                } else {
                    pClient.getClientState().hideSelectSquare();
                    userInterface.getFieldComponent().refresh();
                }
            }
        } else {
            clientData.setDragStartPosition(null);
        }
    }

    public static void mouseDragged(FantasyFootballClient pClient, MouseEvent pMouseEvent, boolean pBoxMode) {
        Game game = pClient.getGame();
        ClientData clientData = pClient.getClientData();
        UserInterface userInterface = pClient.getUserInterface();
        FieldCoordinate coordinate = UtilClientPlayerDrag.getFieldCoordinate(pClient, pMouseEvent, pBoxMode);
        if (coordinate != null && pClient.getClientState().isDragAllowed(coordinate)) {
            if (clientData.getDragStartPosition() == null) {
                UtilClientPlayerDrag.initPlayerDragging(pClient, coordinate, pBoxMode);
            } else if (!coordinate.equals(clientData.getDragEndPosition())) {
                game.getFieldModel().setPlayerCoordinate(clientData.getSelectedPlayer(), coordinate);
                clientData.setDragEndPosition(coordinate);
                userInterface.getFieldComponent().refresh();
            }
        }
    }

    public static void mouseReleased(FantasyFootballClient pClient, MouseEvent pMouseEvent, boolean pBoxMode) {
        Game game = pClient.getGame();
        ClientData clientData = pClient.getClientData();
        if (clientData.getSelectedPlayer() != null && clientData.getDragStartPosition() != null && clientData.getDragEndPosition() != null) {
            if (pClient.getClientState().isDropAllowed(clientData.getDragEndPosition())) {
                pClient.getCommunication().sendSetupPlayer(clientData.getSelectedPlayer(), clientData.getDragEndPosition());
            } else {
                game.getFieldModel().setPlayerCoordinate(clientData.getSelectedPlayer(), clientData.getDragStartPosition());
            }
        }
        UtilClientPlayerDrag.resetDragging(pClient);
        clientData.clear();
    }

    public static void resetDragging(FantasyFootballClient pClient) {
        Game game = pClient.getGame();
        for (Player player : game.getPlayers()) {
            FieldCoordinate playerCoordinate;
            PlayerState playerState = game.getFieldModel().getPlayerState(player);
            if (playerState.getBase() != 15 || (playerCoordinate = game.getFieldModel().getPlayerCoordinate(player)) == null) continue;
            if (playerCoordinate.isBoxCoordinate()) {
                game.getFieldModel().setPlayerState(player, playerState.changeBase(9));
                continue;
            }
            game.getFieldModel().setPlayerState(player, playerState.changeBase(1).changeActive(true));
        }
        UserInterface userInterface = pClient.getUserInterface();
        userInterface.getFieldComponent().refresh();
    }
}

