/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.SoundId;

public class SoundIdFactory
implements INamedObjectFactory {
    @Override
    public SoundId forName(String pName) {
        for (SoundId sound : SoundId.values()) {
            if (!sound.getName().equalsIgnoreCase(pName)) continue;
            return sound;
        }
        return null;
    }
}

