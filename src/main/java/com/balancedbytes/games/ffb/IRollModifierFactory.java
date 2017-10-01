/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.IRollModifier;

public interface IRollModifierFactory
extends INamedObjectFactory {
    @Override
    public IRollModifier forName(String var1);
}

