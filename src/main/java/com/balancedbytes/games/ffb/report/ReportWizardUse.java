/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportWizardUse
implements IReport {
    private String fTeamId;
    private SpecialEffect fWizardSpell;

    public ReportWizardUse() {
    }

    public ReportWizardUse(String pTeamId, SpecialEffect pWizardSpell) {
        this.fTeamId = pTeamId;
        this.fWizardSpell = pWizardSpell;
    }

    @Override
    public ReportId getId() {
        return ReportId.WIZARD_USE;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public SpecialEffect getWizardSpell() {
        return this.fWizardSpell;
    }

    @Override
    public IReport transform() {
        return new ReportWizardUse(this.getTeamId(), this.getWizardSpell());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.WIZARD_SPELL.addTo(jsonObject, this.fWizardSpell);
        return jsonObject;
    }

    @Override
    public ReportWizardUse initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fWizardSpell = (SpecialEffect)IJsonOption.WIZARD_SPELL.getFrom(jsonObject);
        return this;
    }
}

