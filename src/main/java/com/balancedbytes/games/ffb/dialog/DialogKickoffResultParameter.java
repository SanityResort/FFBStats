/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.KickoffResult;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogKickoffResultParameter
implements IDialogParameter {
    private KickoffResult fKickoffResult;

    public DialogKickoffResultParameter() {
    }

    public DialogKickoffResultParameter(KickoffResult pKickoffResult) {
        this.fKickoffResult = pKickoffResult;
    }

    @Override
    public DialogId getId() {
        return DialogId.KICKOFF_RESULT;
    }

    public KickoffResult getKickoffResult() {
        return this.fKickoffResult;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogKickoffResultParameter(this.getKickoffResult());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.KICKOFF_RESULT.addTo(jsonObject, this.fKickoffResult);
        return jsonObject;
    }

    @Override
    public DialogKickoffResultParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fKickoffResult = (KickoffResult)IJsonOption.KICKOFF_RESULT.getFrom(jsonObject);
        return this;
    }
}

