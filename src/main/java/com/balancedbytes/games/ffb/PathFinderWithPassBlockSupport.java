/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPassing;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

public class PathFinderWithPassBlockSupport {
    private static PathFindState globalState;
    private static PathFindState normalState;
    private static PathFindState leapState;
    private static PathFindContext normalMoveContext;
    private static PathFindContext passBlockContext;

    private static FieldCoordinate[] reconstructPath(PathFindNode end) {
        LinkedList<FieldCoordinate> list = new LinkedList<FieldCoordinate>();
        FieldCoordinate[] result = new FieldCoordinate[end.distance];
        while (end.parent != null) {
            list.addFirst(end.coord);
            end = end.parent;
        }
        return list.toArray(result);
    }

    public static FieldCoordinate[] getShortestPath(Game pGame, FieldCoordinate pEndCoord) {
        if (pGame == null) {
            return null;
        }
        ActingPlayer actingPlayer = pGame.getActingPlayer();
        if (actingPlayer == null || actingPlayer.getPlayer() == null) {
            return null;
        }
        Team movingTeam = actingPlayer.getPlayer().getTeam();
        int maxDistance = UtilCards.getPlayerMovement(pGame, actingPlayer.getPlayer()) - actingPlayer.getCurrentMove();
        HashSet<FieldCoordinate> pEndCoords = new HashSet<FieldCoordinate>(1);
        pEndCoords.add(pEndCoord);
        FieldCoordinate start = pGame.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
        return PathFinderWithPassBlockSupport.getShortestPath(pGame, start, pEndCoords, maxDistance, movingTeam, normalMoveContext, false);
    }

    private static FieldCoordinate[] getShortestPath(Game pGame, FieldCoordinate start, Set<FieldCoordinate> pEndCoords, int maxDistance, Team movingTeam, PathFindContext context, boolean canLeap) {
        FieldCoordinate[] players;
        if (pGame == null) {
            return null;
        }
        FieldModel fieldModel = pGame.getFieldModel();
        if (start == null || pEndCoords == null || !PathFinderWithPassBlockSupport.isOnField(pGame, start) || !PathFinderWithPassBlockSupport.isOnField(pGame, pEndCoords)) {
            return null;
        }
        FieldCoordinateBounds endzoneBounds = movingTeam == pGame.getTeamHome() ? FieldCoordinateBounds.ENDZONE_AWAY : FieldCoordinateBounds.ENDZONE_HOME;
        PriorityQueue<PathFindNode> openSet = new PriorityQueue<PathFindNode>();
        PathFindData data = new PathFindData();
        HashSet<PathFindNode> closedSet = new HashSet<PathFindNode>();
        PathFindNode current = new PathFindNode(normalState, start, 0, false, pEndCoords, null);
        data.setNode(start, current);
        openSet.add(current);
        FieldCoordinate ballCoord = fieldModel.getBallCoordinate();
        if (PathFinderWithPassBlockSupport.isOnField(pGame, ballCoord) && context.blockTacklezones) {
            data.setNode(ballCoord, new PathFindNode(normalState, ballCoord, 1000, true, pEndCoords, null));
        }
        boolean hasBall = start.equals(ballCoord);
        for (FieldCoordinate pCoord : players = fieldModel.getPlayerCoordinates()) {
            Player p;
            FieldCoordinate[] tz;
            if (!PathFinderWithPassBlockSupport.isOnField(pGame, pCoord)) continue;
            PathFindNode blockedNode = data.blockNode(pCoord);
            closedSet.add(blockedNode);
            if (!context.blockTacklezones || (p = fieldModel.getPlayer(pCoord)).getTeam() == movingTeam || !fieldModel.getPlayerState(p).hasTacklezones()) continue;
            if (pCoord.isAdjacent(start)) {
                return null;
            }
            for (FieldCoordinate tzCoord : tz = fieldModel.findAdjacentCoordinates(pCoord, FieldCoordinateBounds.FIELD, 1, false)) {
                if (data.isProcessed(normalState, tzCoord.getX(), tzCoord.getY())) continue;
                data.setNode(tzCoord, new PathFindNode(normalState, tzCoord, 1000, true, pEndCoords, null));
            }
        }
        while (openSet.size() > 0) {
            FieldCoordinate[] neighbours;
            current = (PathFindNode)openSet.poll();
            if (current.distance > maxDistance) {
                return null;
            }
            if (pEndCoords.contains(current.coord)) {
                return PathFinderWithPassBlockSupport.reconstructPath(current);
            }
            closedSet.add(current);
            boolean isInEndzone = endzoneBounds.isInBounds(current.coord);
            int searchDistance = canLeap && current.state != leapState && context.allowLeap && maxDistance - current.distance > 1 ? 2 : 1;
            for (FieldCoordinate neighbourCoord : neighbours = fieldModel.findAdjacentCoordinates(current.coord, FieldCoordinateBounds.FIELD, searchDistance, false)) {
                PathFindNode neighbour;
                PathFindState neighbourState;
                int distance = current.coord.distanceInSteps(neighbourCoord);
                if (distance > 1 && (maxDistance - current.distance - distance < 0 || current.state == leapState || !context.allowLeap || !canLeap) || (neighbour = data.getNeighbour(neighbourState = distance == 1 ? current.state : leapState, neighbourCoord)) != null && (closedSet.contains(neighbour) || neighbour.tz && !pEndCoords.contains(neighbourCoord)) || !context.allowExitEndzoneWithBall && hasBall && isInEndzone && !endzoneBounds.isInBounds(neighbourCoord)) continue;
                if (neighbour == null) {
                    neighbour = new PathFindNode(neighbourState, neighbourCoord, current.distance + distance, false, pEndCoords, current);
                    data.setNode(neighbourCoord, neighbour);
                    openSet.add(neighbour);
                    continue;
                }
                if (current.distance + distance >= neighbour.distance) continue;
                openSet.remove(neighbour);
                neighbour.setSource(current, distance);
                openSet.add(neighbour);
            }
        }
        return null;
    }

    private static boolean isOnField(Game pGame, FieldCoordinate pCoordinate) {
        return pGame.getTurnMode() == TurnMode.KICKOFF_RETURN ? FieldCoordinateBounds.HALF_HOME.isInBounds(pCoordinate) : FieldCoordinateBounds.FIELD.isInBounds(pCoordinate);
    }

    private static boolean isOnField(Game pGame, Set<FieldCoordinate> pCoordinates) {
        boolean result = true;
        for (FieldCoordinate coord : pCoordinates) {
            result = result && PathFinderWithPassBlockSupport.isOnField(pGame, coord);
        }
        return result;
    }

    public static FieldCoordinate[] allowPassBlockMove(Game pGame, Player passBlocker, FieldCoordinate startPosition, int distance, boolean canLeap) {
        if (!UtilCards.hasSkill(pGame, passBlocker, Skill.PASS_BLOCK)) {
            return new FieldCoordinate[0];
        }
        Set<FieldCoordinate> validEndCoordinates = UtilPassing.findValidPassBlockEndCoordinates(pGame);
        FieldCoordinate[] path = PathFinderWithPassBlockSupport.getShortestPath(pGame, startPosition, validEndCoordinates, distance, passBlocker.getTeam(), passBlockContext, canLeap);
        return path;
    }

    static {
        passBlockContext = new PathFindContext();
        PathFinderWithPassBlockSupport.passBlockContext.blockTacklezones = false;
        PathFinderWithPassBlockSupport.passBlockContext.allowLeap = true;
        PathFinderWithPassBlockSupport.passBlockContext.allowExitEndzoneWithBall = true;
        normalMoveContext = new PathFindContext();
        PathFinderWithPassBlockSupport.normalMoveContext.blockTacklezones = true;
        PathFinderWithPassBlockSupport.normalMoveContext.allowLeap = false;
        PathFinderWithPassBlockSupport.normalMoveContext.allowExitEndzoneWithBall = false;
        globalState = new PathFindState();
        normalState = new PathFindState();
        leapState = new PathFindState();
    }

    private static class PathFindData {
        private Hashtable<PathFindState, PathFindNode[][]> nodes = new Hashtable();

        public PathFindData() {
            this.nodes.put(normalState, new PathFindNode[26][15]);
            this.nodes.put(leapState, new PathFindNode[26][15]);
        }

        public PathFindNode blockNode(FieldCoordinate coordinate) {
            PathFindNode blockedNode = new PathFindNode(globalState, coordinate, 1000, false, null, null);
            for (PathFindState state : this.nodes.keySet()) {
                this.nodes.get((Object)state)[coordinate.getX()][coordinate.getY()] = blockedNode;
            }
            return blockedNode;
        }

        public boolean isProcessed(PathFindState state, int x, int y) {
            return this.nodes.get(state)[x][y] != null;
        }

        public void setNode(FieldCoordinate coord, PathFindNode node) {
            this.nodes.get((Object)node.state)[coord.getX()][coord.getY()] = node;
        }

        public PathFindNode getNeighbour(PathFindState state, FieldCoordinate neighbour) {
            return this.nodes.get(state)[neighbour.getX()][neighbour.getY()];
        }
    }

    private static class PathFindState {
        private PathFindState() {
        }
    }

    private static class PathFindNode
    implements Comparable<PathFindNode> {
        public PathFindState state;
        public int distance;
        private int estimate = -1;
        public boolean tz;
        public FieldCoordinate coord;
        public Set<FieldCoordinate> target;
        public PathFindNode parent;

        public PathFindNode(PathFindState state, FieldCoordinate coord, int distance, boolean tz, Set<FieldCoordinate> target, PathFindNode parent) {
            this.state = state;
            this.coord = coord;
            this.parent = parent;
            this.target = target;
            this.tz = tz;
            this.distance = distance;
            this.estimate = 1000;
            if (target != null) {
                for (FieldCoordinate t : target) {
                    this.estimate = Math.min(this.estimate, coord.distanceInSteps(t));
                }
            }
        }

        public int getWeight() {
            return this.distance + this.estimate;
        }

        private int getNonDiagonalWeight() {
            int bestWeight = 10000;
            for (FieldCoordinate t : this.target) {
                int weight = Math.abs(this.coord.getX() - t.getX()) + Math.abs(this.coord.getY() - t.getY());
                if (weight >= bestWeight) continue;
                bestWeight = weight;
            }
            return this.distance + bestWeight;
        }

        @Override
        public int compareTo(PathFindNode other) {
            int otherWeight;
            int thisWeight = this.getWeight();
            if (thisWeight == (otherWeight = other.getWeight())) {
                thisWeight = this.getNonDiagonalWeight();
                otherWeight = other.getNonDiagonalWeight();
            }
            return thisWeight - otherWeight;
        }

        public void setSource(PathFindNode source, int length) {
            this.setSource(source, length, this.state);
        }

        public void setSource(PathFindNode source, int length, PathFindState newState) {
            this.distance = source.distance + length;
            this.parent = source;
            this.state = newState;
        }
    }

    private static class PathFindContext {
        public boolean blockTacklezones = true;
        public boolean allowLeap = false;
        public boolean allowExitEndzoneWithBall = false;

        private PathFindContext() {
        }
    }

}

