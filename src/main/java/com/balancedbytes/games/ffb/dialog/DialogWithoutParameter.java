/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.eclipsesource.json.JsonObject;

public abstract class DialogWithoutParameter
implements IDialogParameter {
    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        return jsonObject;
    }
}

