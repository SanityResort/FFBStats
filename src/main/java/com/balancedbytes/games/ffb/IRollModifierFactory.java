/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.IRollModifier;

public interface IRollModifierFactory
extends IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public IRollModifier forId(int var1);

    @Override
    public IRollModifier forName(String var1);
}

