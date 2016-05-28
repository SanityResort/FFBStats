/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogPilingOnParameter
implements IDialogParameter {
    private String fPlayerId;
    private boolean fReRollInjury;

    public DialogPilingOnParameter() {
    }

    public DialogPilingOnParameter(String pPlayerId, boolean pReRollInjury) {
        this.fPlayerId = pPlayerId;
        this.fReRollInjury = pReRollInjury;
    }

    @Override
    public DialogId getId() {
        return DialogId.PILING_ON;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isReRollInjury() {
        return this.fReRollInjury;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogPilingOnParameter(this.getPlayerId(), this.isReRollInjury());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.RE_ROLL_INJURY.addTo(jsonObject, this.fReRollInjury);
        return jsonObject;
    }

    @Override
    public DialogPilingOnParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fReRollInjury = IJsonOption.RE_ROLL_INJURY.getFrom(jsonObject);
        return this;
    }
}

