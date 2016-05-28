/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogReceiveChoiceParameter
implements IDialogParameter {
    private String fChoosingTeamId;

    public DialogReceiveChoiceParameter() {
    }

    public DialogReceiveChoiceParameter(String pChoosingTeamId) {
        this.fChoosingTeamId = pChoosingTeamId;
    }

    @Override
    public DialogId getId() {
        return DialogId.RECEIVE_CHOICE;
    }

    public String getChoosingTeamId() {
        return this.fChoosingTeamId;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogReceiveChoiceParameter(this.getChoosingTeamId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.CHOOSING_TEAM_ID.addTo(jsonObject, this.fChoosingTeamId);
        return jsonObject;
    }

    @Override
    public DialogReceiveChoiceParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fChoosingTeamId = IJsonOption.CHOOSING_TEAM_ID.getFrom(jsonObject);
        return this;
    }
}

