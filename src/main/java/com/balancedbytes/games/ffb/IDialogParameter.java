/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public interface IDialogParameter
extends IJsonSerializable {
    public DialogId getId();

    public IDialogParameter transform();

    @Override
    public IDialogParameter initFrom(JsonValue var1);

    @Override
    public JsonObject toJsonValue();
}

