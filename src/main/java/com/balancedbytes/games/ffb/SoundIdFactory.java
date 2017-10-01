/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

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

