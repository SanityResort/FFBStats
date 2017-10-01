/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.PassingDistanceFactory;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UtilPassing {
    public static double RULER_WIDTH = 1.74;
    private static final String[] _THROWING_RANGE_TABLE = new String[]{"T Q Q Q S S S L L L L B B B", "Q Q Q Q S S S L L L L B B B", "Q Q Q S S S S L L L L B B  ", "Q Q S S S S S L L L B B B  ", "S S S S S S L L L L B B B  ", "S S S S S L L L L B B B    ", "S S S S L L L L L B B B    ", "L L L L L L L L B B B      ", "L L L L L L L B B B B      ", "L L L L L B B B B B        ", "L L L B B B B B B          ", "B B B B B B B              ", "B B B B B                  ", "B B                        "};
    private static final PassingDistance[][] _PASSING_DISTANCES_TABLE = new PassingDistance[14][14];

    public static PassingDistance findPassingDistance(Game pGame, FieldCoordinate pFromCoordinate, FieldCoordinate pToCoordinate, boolean pThrowTeamMate) {
        PassingDistance passingDistance = null;
        if (pFromCoordinate != null && pToCoordinate != null) {
            int deltaY = Math.abs(pToCoordinate.getY() - pFromCoordinate.getY());
            int deltaX = Math.abs(pToCoordinate.getX() - pFromCoordinate.getX());
            if (deltaY < 14 && deltaX < 14) {
                passingDistance = _PASSING_DISTANCES_TABLE[deltaY][deltaX];
            }
            if (!(!pThrowTeamMate && Weather.BLIZZARD != pGame.getFieldModel().getWeather() || passingDistance != PassingDistance.LONG_BOMB && passingDistance != PassingDistance.LONG_PASS)) {
                passingDistance = null;
            }
        }
        return passingDistance;
    }

    public static Player[] findInterceptors(Game pGame, Player pThrower, FieldCoordinate pTargetCoordinate) {
        ArrayList<Player> interceptors = new ArrayList<Player>();
        if (pTargetCoordinate != null && pThrower != null) {
            FieldCoordinate throwerCoordinate = pGame.getFieldModel().getPlayerCoordinate(pThrower);
            Team otherTeam = pGame.getTeamHome().hasPlayer(pThrower) ? pGame.getTeamAway() : pGame.getTeamHome();
            Player[] otherPlayers = otherTeam.getPlayers();
            for (int i = 0; i < otherPlayers.length; ++i) {
                PlayerState interceptorState = pGame.getFieldModel().getPlayerState(otherPlayers[i]);
                FieldCoordinate interceptorCoordinate = pGame.getFieldModel().getPlayerCoordinate(otherPlayers[i]);
                if (interceptorCoordinate == null || interceptorState == null || !interceptorState.hasTacklezones() || UtilCards.hasSkill(pGame, otherPlayers[i], Skill.NO_HANDS) || !UtilPassing.canIntercept(throwerCoordinate, pTargetCoordinate, interceptorCoordinate)) continue;
                interceptors.add(otherPlayers[i]);
            }
        }
        return interceptors.toArray(new Player[interceptors.size()]);
    }

    private static boolean canIntercept(FieldCoordinate pThrowerCoordinate, FieldCoordinate pTargetCoordinate, FieldCoordinate pIinterceptorCoordinate) {
        int receiverX = pTargetCoordinate.getX() - pThrowerCoordinate.getX();
        int receiverY = pTargetCoordinate.getY() - pThrowerCoordinate.getY();
        int interceptorX = pIinterceptorCoordinate.getX() - pThrowerCoordinate.getX();
        int interceptorY = pIinterceptorCoordinate.getY() - pThrowerCoordinate.getY();
        int a = (receiverX - interceptorX) * (receiverX - interceptorX) + (receiverY - interceptorY) * (receiverY - interceptorY);
        int b = interceptorX * interceptorX + interceptorY * interceptorY;
        int c = receiverX * receiverX + receiverY * receiverY;
        double d1 = Math.abs((double)receiverY * ((double)interceptorX + 0.5) - (double)receiverX * ((double)interceptorY + 0.5));
        double d2 = Math.abs((double)receiverY * ((double)interceptorX + 0.5) - (double)receiverX * ((double)interceptorY - 0.5));
        double d3 = Math.abs((double)receiverY * ((double)interceptorX - 0.5) - (double)receiverX * ((double)interceptorY + 0.5));
        double d4 = Math.abs((double)receiverY * ((double)interceptorX - 0.5) - (double)receiverX * ((double)interceptorY - 0.5));
        return c > a && c > b && RULER_WIDTH > 2.0 * Math.min(Math.min(Math.min(d1, d2), d3), d4) / Math.sqrt(c);
    }

    public static Set<FieldCoordinate> findValidPassBlockEndCoordinates(Game pGame) {
        FieldCoordinate[] neighbours;
        HashSet<FieldCoordinate> validCoordinates = new HashSet<FieldCoordinate>();
        if (pGame == null || pGame.getThrower() == null || pGame.getPassCoordinate() == null) {
            return validCoordinates;
        }
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        for (FieldCoordinate c : neighbours = pGame.getFieldModel().findAdjacentCoordinates(pGame.getFieldModel().getPlayerCoordinate(pGame.getThrower()), FieldCoordinateBounds.FIELD, 1, false)) {
            Player playerInTz = pGame.getFieldModel().getPlayer(c);
            if (playerInTz != null && playerInTz != actingPlayer.getPlayer()) continue;
            validCoordinates.add(c);
        }
        Player targetPlayer = pGame.getFieldModel().getPlayer(pGame.getPassCoordinate());
        if (PlayerAction.HAIL_MARY_PASS == pGame.getThrowerAction()) {
            if (targetPlayer != null) {
                validCoordinates.add(pGame.getPassCoordinate());
            }
        } else {
            validCoordinates.addAll(UtilPassing.findInterceptCoordinates(pGame));
            if (targetPlayer != null) {
                for (FieldCoordinate c : neighbours = pGame.getFieldModel().findAdjacentCoordinates(pGame.getPassCoordinate(), FieldCoordinateBounds.FIELD, 1, false)) {
                    Player playerInTz = pGame.getFieldModel().getPlayer(c);
                    if (playerInTz != null && playerInTz != actingPlayer.getPlayer()) continue;
                    validCoordinates.add(c);
                }
            } else {
                validCoordinates.add(pGame.getPassCoordinate());
            }
        }
        return validCoordinates;
    }

    private static Set<FieldCoordinate> findInterceptCoordinates(Game pGame) {
        FieldCoordinate[] playerCoordinates;
        FieldModel fieldModel = pGame.getFieldModel();
        HashSet<FieldCoordinate> eligibleCoordinates = new HashSet<FieldCoordinate>();
        HashSet<FieldCoordinate> closedSet = new HashSet<FieldCoordinate>();
        ArrayList<FieldCoordinate> openSet = new ArrayList<FieldCoordinate>();
        FieldCoordinate throwerCoord = fieldModel.getPlayerCoordinate(pGame.getThrower());
        openSet.add(throwerCoord);
        while (!openSet.isEmpty()) {
            FieldCoordinate currentCoordinate = (FieldCoordinate)openSet.remove(0);
            if (closedSet.contains(currentCoordinate)) continue;
            if (currentCoordinate.equals(throwerCoord) || UtilPassing.canIntercept(throwerCoord, pGame.getPassCoordinate(), currentCoordinate)) {
                FieldCoordinate[] adjacentCoordinates;
                eligibleCoordinates.add(currentCoordinate);
                for (FieldCoordinate c : adjacentCoordinates = fieldModel.findAdjacentCoordinates(currentCoordinate, FieldCoordinateBounds.FIELD, 1, false)) {
                    if (closedSet.contains(c)) continue;
                    openSet.add(c);
                }
            }
            closedSet.add(currentCoordinate);
        }
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        FieldCoordinate actingPlayerPosition = fieldModel.getPlayerCoordinate(actingPlayer.getPlayer());
        for (FieldCoordinate pCoord : playerCoordinates = fieldModel.getPlayerCoordinates()) {
            if (pCoord.equals(actingPlayerPosition)) continue;
            eligibleCoordinates.remove(pCoord);
        }
        return eligibleCoordinates;
    }

    static {
        PassingDistanceFactory passingDistanceFactory = new PassingDistanceFactory();
        for (int y = 0; y < 14; ++y) {
            for (int x = 0; x < 14; ++x) {
                UtilPassing._PASSING_DISTANCES_TABLE[y][x] = passingDistanceFactory.forShortcut(_THROWING_RANGE_TABLE[y].charAt(x * 2));
            }
        }
    }
}

