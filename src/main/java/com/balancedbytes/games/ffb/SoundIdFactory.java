/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.SoundId;

public class SoundIdFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public SoundId forName(String pName) {
        for (SoundId sound : SoundId.values()) {
            if (!sound.getName().equalsIgnoreCase(pName)) continue;
            return sound;
        }
        return null;
    }

    @Override
    public SoundId forId(int pId) {
        for (SoundId sound : SoundId.values()) {
            if (sound.getId() != pId) continue;
            return sound;
        }
        return null;
    }
}

