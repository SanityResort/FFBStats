/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogPettyCashParameter
implements IDialogParameter {
    private String fTeamId;
    private int fTreasury;
    private int fTeamValue;
    private int fOpponentTeamValue;

    public DialogPettyCashParameter() {
    }

    public DialogPettyCashParameter(String pTeamId, int pTeamValue, int pTreasury, int pOpponentTeamValue) {
        this();
        this.fTeamId = pTeamId;
        this.fTeamValue = pTeamValue;
        this.fTreasury = pTreasury;
        this.fOpponentTeamValue = pOpponentTeamValue;
    }

    @Override
    public DialogId getId() {
        return DialogId.PETTY_CASH;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getTeamValue() {
        return this.fTeamValue;
    }

    public int getTreasury() {
        return this.fTreasury;
    }

    public int getOpponentTeamValue() {
        return this.fOpponentTeamValue;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogPettyCashParameter(this.getTeamId(), this.getTeamValue(), this.getTreasury(), this.getOpponentTeamValue());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.TEAM_VALUE.addTo(jsonObject, this.fTeamValue);
        IJsonOption.TREASURY.addTo(jsonObject, this.fTreasury);
        IJsonOption.OPPONENT_TEAM_VALUE.addTo(jsonObject, this.fOpponentTeamValue);
        return jsonObject;
    }

    @Override
    public DialogPettyCashParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fTeamValue = IJsonOption.TEAM_VALUE.getFrom(jsonObject);
        this.fTreasury = IJsonOption.TREASURY.getFrom(jsonObject);
        this.fOpponentTeamValue = IJsonOption.OPPONENT_TEAM_VALUE.getFrom(jsonObject);
        return this;
    }
}

