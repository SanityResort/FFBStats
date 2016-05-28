/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.util.ArrayTool;

public class DirectionFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public Direction forName(String pName) {
        for (Direction direction : Direction.values()) {
            if (!direction.getName().equalsIgnoreCase(pName)) continue;
            return direction;
        }
        return null;
    }

    @Override
    public Direction forId(int pId) {
        for (Direction direction : Direction.values()) {
            if (direction.getId() != pId) continue;
            return direction;
        }
        return null;
    }

    public Direction transform(Direction pDirection) {
        if (pDirection == null) {
            return null;
        }
        return this.forId(pDirection.getTransformedValue());
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

