/*
 * Decompiled with CFR 0_114.
 */
package com.fumbbl.rng;

public interface EntropySource {
    public boolean hasEnoughEntropy();

    public byte getEntropy();
}

