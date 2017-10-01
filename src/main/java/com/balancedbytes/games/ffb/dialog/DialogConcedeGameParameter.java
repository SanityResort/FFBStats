/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogConcedeGameParameter
extends DialogWithoutParameter {
    @Override
    public DialogId getId() {
        return DialogId.CONCEDE_GAME;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogConcedeGameParameter();
    }

    @Override
    public DialogConcedeGameParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        return this;
    }
}

