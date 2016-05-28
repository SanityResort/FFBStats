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
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogWinningsReRollParameter
implements IDialogParameter {
    private String fTeamId;
    private int fOldRoll;

    public DialogWinningsReRollParameter() {
    }

    public DialogWinningsReRollParameter(String pTeamId, int pOldRoll) {
        this.fTeamId = pTeamId;
        this.fOldRoll = pOldRoll;
    }

    @Override
    public DialogId getId() {
        return DialogId.WINNINGS_RE_ROLL;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getOldRoll() {
        return this.fOldRoll;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogWinningsReRollParameter(this.getTeamId(), this.getOldRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.OLD_ROLL.addTo(jsonObject, this.fOldRoll);
        return jsonObject;
    }

    @Override
    public DialogWinningsReRollParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fOldRoll = IJsonOption.OLD_ROLL.getFrom(jsonObject);
        return this;
    }
}

