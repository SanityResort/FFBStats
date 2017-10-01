/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.util;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class UtilClientActionKeys {
    public static FieldCoordinate findMoveCoordinate(FantasyFootballClient pClient, FieldCoordinate pStartCoordinate, ActionKey pActionKey) {
        FieldCoordinate moveCoordinate = null;
        Direction moveDirection = UtilClientActionKeys.findMoveDirection(pClient, pActionKey);
        if (moveDirection != null) {
            switch (moveDirection) {
                case NORTH: {
                    moveCoordinate = pStartCoordinate.add(0, -1);
                    break;
                }
                case NORTHEAST: {
                    moveCoordinate = pStartCoordinate.add(1, -1);
                    break;
                }
                case EAST: {
                    moveCoordinate = pStartCoordinate.add(1, 0);
                    break;
                }
                case SOUTHEAST: {
                    moveCoordinate = pStartCoordinate.add(1, 1);
                    break;
                }
                case SOUTH: {
                    moveCoordinate = pStartCoordinate.add(0, 1);
                    break;
                }
                case SOUTHWEST: {
                    moveCoordinate = pStartCoordinate.add(-1, 1);
                    break;
                }
                case WEST: {
                    moveCoordinate = pStartCoordinate.add(-1, 0);
                    break;
                }
                case NORTHWEST: {
                    moveCoordinate = pStartCoordinate.add(-1, -1);
                }
            }
        }
        return moveCoordinate;
    }

    public static Direction findMoveDirection(FantasyFootballClient pClient, ActionKey pActionKey) {
        Direction moveDirection = null;
        switch (pActionKey) {
            case PLAYER_MOVE_NORTH: {
                moveDirection = Direction.NORTH;
                break;
            }
            case PLAYER_MOVE_NORTHEAST: {
                moveDirection = Direction.NORTHEAST;
                break;
            }
            case PLAYER_MOVE_EAST: {
                moveDirection = Direction.EAST;
                break;
            }
            case PLAYER_MOVE_SOUTHEAST: {
                moveDirection = Direction.SOUTHEAST;
                break;
            }
            case PLAYER_MOVE_SOUTH: {
                moveDirection = Direction.SOUTH;
                break;
            }
            case PLAYER_MOVE_SOUTHWEST: {
                moveDirection = Direction.SOUTHWEST;
                break;
            }
            case PLAYER_MOVE_WEST: {
                moveDirection = Direction.WEST;
                break;
            }
            case PLAYER_MOVE_NORTHWEST: {
                moveDirection = Direction.NORTHWEST;
                break;
            }
        }
        return moveDirection;
    }

    public static Player cyclePlayer(Game pGame, Player pStartPlayer, boolean pRight) {
        Player nextPlayer = null;
        if (pStartPlayer != null) {
            FieldCoordinate startPlayerPosition = pGame.getFieldModel().getPlayerCoordinate(pStartPlayer);
            if (pRight) {
                for (int y = 0; nextPlayer == null && y < 15 - startPlayerPosition.getY(); ++y) {
                    int x;
                    for (x = 0; nextPlayer == null && x < 26 - startPlayerPosition.getX() - 2; ++x) {
                        if (x == 0 && y == 0) continue;
                        nextPlayer = UtilClientActionKeys.findSelectableHomePlayer(pGame, startPlayerPosition.add(x, y));
                    }
                    if (y <= 0) continue;
                    for (x = -1; nextPlayer == null && x > 1 - startPlayerPosition.getX(); --x) {
                        nextPlayer = UtilClientActionKeys.findSelectableHomePlayer(pGame, startPlayerPosition.add(x, y));
                    }
                }
            } else {
                for (int y = 0; nextPlayer == null && y > - startPlayerPosition.getY(); --y) {
                    int x;
                    for (x = 0; nextPlayer == null && x > 1 - startPlayerPosition.getX(); --x) {
                        if (x == 0 && y == 0) continue;
                        nextPlayer = UtilClientActionKeys.findSelectableHomePlayer(pGame, startPlayerPosition.add(x, y));
                    }
                    if (y >= 0) continue;
                    for (x = 1; nextPlayer == null && x < 26 - startPlayerPosition.getX() - 2; ++x) {
                        nextPlayer = UtilClientActionKeys.findSelectableHomePlayer(pGame, startPlayerPosition.add(x, y));
                    }
                }
            }
        } else {
            Player[] players = pGame.getTeamHome().getPlayers();
            for (int i = 0; i < players.length; ++i) {
                PlayerState playerState = pGame.getFieldModel().getPlayerState(players[i]);
                if (!playerState.isActive()) continue;
                if (nextPlayer == null) {
                    nextPlayer = players[i];
                    continue;
                }
                FieldCoordinate playerCoordinate = pGame.getFieldModel().getPlayerCoordinate(players[i]);
                FieldCoordinate nextPlayerCoordinate = pGame.getFieldModel().getPlayerCoordinate(nextPlayer);
                if (pRight) {
                    if (playerCoordinate.getX() <= nextPlayerCoordinate.getX()) continue;
                    nextPlayer = players[i];
                    continue;
                }
                if (playerCoordinate.getX() >= nextPlayerCoordinate.getX()) continue;
                nextPlayer = players[i];
            }
        }
        if (nextPlayer == null) {
            nextPlayer = pStartPlayer;
        }
        return nextPlayer;
    }

    private static Player findSelectableHomePlayer(Game pGame, FieldCoordinate pCoordinate) {
        PlayerState playerState;
        Player player = pGame.getFieldModel().getPlayer(pCoordinate);
        if (player != null && pGame.getTeamHome().hasPlayer(player) && (playerState = pGame.getFieldModel().getPlayerState(player)).isActive()) {
            return player;
        }
        return null;
    }

}

