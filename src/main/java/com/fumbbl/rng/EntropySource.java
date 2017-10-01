/*
 * Decompiled with CFR 0_122.
 */
package com.fumbbl.rng;

public interface EntropySource {
    public boolean hasEnoughEntropy();

    public byte getEntropy();
}

