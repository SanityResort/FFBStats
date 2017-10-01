/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogInterceptionParameter
implements IDialogParameter {
    private String fThrowerId;

    public DialogInterceptionParameter() {
    }

    public DialogInterceptionParameter(String pPlayerId) {
        this.fThrowerId = pPlayerId;
    }

    @Override
    public DialogId getId() {
        return DialogId.INTERCEPTION;
    }

    public String getThrowerId() {
        return this.fThrowerId;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogInterceptionParameter(this.getThrowerId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.THROWER_ID.addTo(jsonObject, this.fThrowerId);
        return jsonObject;
    }

    @Override
    public DialogInterceptionParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fThrowerId = IJsonOption.THROWER_ID.getFrom(jsonObject);
        return this;
    }
}

