/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.json.IJsonWriteable;

public interface IGameOption
extends IJsonWriteable {

    public GameOptionId getId();

    public String getValueAsString();

    public IGameOption setValue(String var1);

    public boolean isChanged();

    public String getDisplayMessage();
}

