/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;

public interface IRollModifier
extends IEnumWithId,
IEnumWithName {
    public int getModifier();

    public boolean isModifierIncluded();
}

