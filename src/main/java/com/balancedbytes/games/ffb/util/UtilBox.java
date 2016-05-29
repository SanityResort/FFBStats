/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UtilBox {
    public static PlayerState findPlayerStateForCoordinate(FieldCoordinate pCoordinate) {
        if (pCoordinate != null && pCoordinate.isBoxCoordinate()) {
            switch (pCoordinate.getX()) {
                case -1: 
                case 30: {
                    return new PlayerState(9);
                }
                case -2: 
                case 31: {
                    return new PlayerState(5);
                }
                case -3: 
                case 32: {
                    return new PlayerState(6);
                }
                case -4: 
                case 33: {
                    return new PlayerState(7);
                }
                case -5: 
                case 34: {
                    return new PlayerState(8);
                }
                case -7: 
                case 36: {
                    return new PlayerState(10);
                }
                case -6: 
                case 35: {
                    return new PlayerState(13);
                }
            }
        }
        return null;
    }

    public static void putPlayerIntoBox(Game pGame, Player pPlayer) {
        if (pGame != null && pPlayer != null) {
            int boxX = 0;
            boolean homePlayer = pGame.getTeamHome().hasPlayer(pPlayer);
            PlayerState playerState = pGame.getFieldModel().getPlayerState(pPlayer);
            switch (playerState.getBase()) {
                case 9: 
                case 14: {
                    boxX = homePlayer ? -1 : 30;
                    break;
                }
                case 5: {
                    boxX = homePlayer ? -2 : 31;
                    break;
                }
                case 6: {
                    boxX = homePlayer ? -3 : 32;
                    break;
                }
                case 7: {
                    boxX = homePlayer ? -4 : 33;
                    break;
                }
                case 8: {
                    boxX = homePlayer ? -5 : 34;
                    break;
                }
                case 13: {
                    boxX = homePlayer ? -6 : 35;
                    break;
                }
                case 10: {
                    boxX = homePlayer ? -7 : 36;
                }
            }
            if (boxX != 0) {
                int y = 0;
                FieldCoordinate freeCoordinate = new FieldCoordinate(boxX, y);
                while (pGame.getFieldModel().getPlayer(freeCoordinate) != null) {
                    freeCoordinate = new FieldCoordinate(boxX, ++y);
                }
            }
        }
    }

    public static void refreshBoxes(Game pGame) {
        UtilBox.refreshBox(pGame, -1);
        UtilBox.refreshBox(pGame, 30);
        UtilBox.refreshBox(pGame, -2);
        UtilBox.refreshBox(pGame, 31);
        UtilBox.refreshBox(pGame, -3);
        UtilBox.refreshBox(pGame, 32);
        UtilBox.refreshBox(pGame, -4);
        UtilBox.refreshBox(pGame, 33);
        UtilBox.refreshBox(pGame, -5);
        UtilBox.refreshBox(pGame, 34);
        UtilBox.refreshBox(pGame, -6);
        UtilBox.refreshBox(pGame, 35);
        UtilBox.refreshBox(pGame, -7);
        UtilBox.refreshBox(pGame, 36);
    }

    private static void refreshBox(Game pGame, int pBoxX) {
        ArrayList<FieldCoordinate> coordinates = new ArrayList<FieldCoordinate>();
        for (FieldCoordinate coordinate : pGame.getFieldModel().getPlayerCoordinates()) {
            if (coordinate.getX() != pBoxX) continue;
            coordinates.add(coordinate);
        }
        Collections.sort(coordinates, new Comparator<FieldCoordinate>(){

            @Override
            public int compare(FieldCoordinate pO1, FieldCoordinate pO2) {
                return pO1.getY() - pO2.getY();
            }
        });

    }

}

