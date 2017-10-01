/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class BlockResultFactory
implements INamedObjectFactory {
    @Override
    public BlockResult forName(String pName) {
        for (BlockResult result : BlockResult.values()) {
            if (!result.getName().equalsIgnoreCase(pName)) continue;
            return result;
        }
        return null;
    }

    public BlockResult forRoll(int pRoll) {
        switch (pRoll) {
            case 1: {
                return BlockResult.SKULL;
            }
            case 2: {
                return BlockResult.BOTH_DOWN;
            }
            case 5: {
                return BlockResult.POW_PUSHBACK;
            }
            case 6: {
                return BlockResult.POW;
            }
        }
        return BlockResult.PUSHBACK;
    }
}

