/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.json;

public class MissingKeyException
extends RuntimeException {
    public MissingKeyException(String pMessage) {
        super(pMessage);
    }

    public MissingKeyException(Throwable pCause) {
        super(pCause);
    }

    public MissingKeyException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}

