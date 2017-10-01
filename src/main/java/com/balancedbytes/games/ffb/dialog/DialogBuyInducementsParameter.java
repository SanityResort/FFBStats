/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogBuyInducementsParameter
implements IDialogParameter {
    private String fTeamId;
    private int fAvailableGold;
    private boolean fWizardAvailable;

    public DialogBuyInducementsParameter() {
    }

    public DialogBuyInducementsParameter(String pTeamId, int pAvailableGold, boolean wizardAvailable) {
        this.fTeamId = pTeamId;
        this.fAvailableGold = pAvailableGold;
        this.fWizardAvailable = wizardAvailable;
    }

    @Override
    public DialogId getId() {
        return DialogId.BUY_INDUCEMENTS;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getAvailableGold() {
        return this.fAvailableGold;
    }

    public boolean isWizardAvailable() {
        return this.fWizardAvailable;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogBuyInducementsParameter(this.getTeamId(), this.getAvailableGold(), this.isWizardAvailable());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.AVAILABLE_GOLD.addTo(jsonObject, this.fAvailableGold);
        IJsonOption.WIZARD_AVAILABLE.addTo(jsonObject, this.fWizardAvailable);
        return jsonObject;
    }

    @Override
    public DialogBuyInducementsParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fAvailableGold = IJsonOption.AVAILABLE_GOLD.getFrom(jsonObject);
        Boolean wizardAvailable = IJsonOption.WIZARD_AVAILABLE.getFrom(jsonObject);
        this.fWizardAvailable = wizardAvailable != null ? wizardAvailable : false;
        return this;
    }
}

