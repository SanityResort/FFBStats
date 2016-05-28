/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.BlockResult;
import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class BlockResultFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public BlockResult forName(String pName) {
        for (BlockResult result : BlockResult.values()) {
            if (!result.getName().equalsIgnoreCase(pName)) continue;
            return result;
        }
        return null;
    }

    @Override
    public BlockResult forId(int pId) {
        for (BlockResult result : BlockResult.values()) {
            if (result.getId() != pId) continue;
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

