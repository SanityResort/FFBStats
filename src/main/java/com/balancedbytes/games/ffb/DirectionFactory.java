/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.util.ArrayTool;

public class DirectionFactory
implements INamedObjectFactory {
    @Override
    public Direction forName(String pName) {
        for (Direction direction : Direction.values()) {
            if (!direction.getName().equalsIgnoreCase(pName)) continue;
            return direction;
        }
        return null;
    }

    public Direction forRoll(int pRoll) {
        switch (pRoll) {
            case 1: {
                return Direction.NORTH;
            }
            case 2: {
                return Direction.NORTHEAST;
            }
            case 3: {
                return Direction.EAST;
            }
            case 4: {
                return Direction.SOUTHEAST;
            }
            case 5: {
                return Direction.SOUTH;
            }
            case 6: {
                return Direction.SOUTHWEST;
            }
            case 7: {
                return Direction.WEST;
            }
            case 8: {
                return Direction.NORTHWEST;
            }
        }
        return null;
    }

    public Direction transform(Direction pDirection) {
        if (pDirection == null) {
            return null;
        }
        switch (pDirection) {
            case NORTHEAST: {
                return Direction.NORTHWEST;
            }
            case EAST: {
                return Direction.WEST;
            }
            case SOUTHEAST: {
                return Direction.SOUTHWEST;
            }
            case SOUTHWEST: {
                return Direction.SOUTHEAST;
            }
            case WEST: {
                return Direction.EAST;
            }
            case NORTHWEST: {
                return Direction.NORTHEAST;
            }
        }
        return pDirection;
    }

    public Direction[] transform(Direction[] pDirections) {
        Direction[] transformedDirections = new Direction[]{};
        if (ArrayTool.isProvided(pDirections)) {
            transformedDirections = new Direction[pDirections.length];
            for (int i = 0; i < transformedDirections.length; ++i) {
                transformedDirections[i] = this.transform(pDirections[i]);
            }
        }
        return transformedDirections;
    }

}

