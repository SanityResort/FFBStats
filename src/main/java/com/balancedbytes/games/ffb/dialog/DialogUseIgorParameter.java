/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogUseIgorParameter
implements IDialogParameter {
    private String fPlayerId;

    public DialogUseIgorParameter() {
    }

    public DialogUseIgorParameter(String pPlayerId) {
        this.fPlayerId = pPlayerId;
    }

    @Override
    public DialogId getId() {
        return DialogId.USE_IGOR;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogUseIgorParameter(this.getPlayerId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        return jsonObject;
    }

    @Override
    public DialogUseIgorParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        return this;
    }
}

