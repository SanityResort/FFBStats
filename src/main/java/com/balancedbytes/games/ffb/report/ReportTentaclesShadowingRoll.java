/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportTentaclesShadowingRoll
implements IReport {
    private Skill fSkill;
    private String fDefenderId;
    private int[] fRoll;
    private boolean fSuccessful;
    private int fMinimumRoll;
    private boolean fReRolled;

    public ReportTentaclesShadowingRoll() {
    }

    public ReportTentaclesShadowingRoll(Skill pSkill, String pDefenderId, int[] pRoll, boolean pSuccessful, int pMinimumRoll, boolean pReRolled) {
        this.fSkill = pSkill;
        this.fDefenderId = pDefenderId;
        this.fRoll = pRoll;
        this.fSuccessful = pSuccessful;
        this.fMinimumRoll = pMinimumRoll;
        this.fReRolled = pReRolled;
    }

    @Override
    public ReportId getId() {
        return ReportId.TENTACLES_SHADOWING_ROLL;
    }

    public Skill getSkill() {
        return this.fSkill;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public int[] getRoll() {
        return this.fRoll;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    public boolean isReRolled() {
        return this.fReRolled;
    }

    @Override
    public IReport transform() {
        return new ReportTentaclesShadowingRoll(this.getSkill(), this.getDefenderId(), this.getRoll(), this.isSuccessful(), this.getMinimumRoll(), this.isReRolled());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.SKILL.addTo(jsonObject, this.fSkill);
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        IJsonOption.TENTACLE_ROLL.addTo(jsonObject, this.fRoll);
        IJsonOption.SUCCESSFUL.addTo(jsonObject, this.fSuccessful);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        IJsonOption.RE_ROLLED.addTo(jsonObject, this.fReRolled);
        return jsonObject;
    }

    @Override
    public ReportTentaclesShadowingRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSkill = (Skill)IJsonOption.SKILL.getFrom(jsonObject);
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.TENTACLE_ROLL.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fReRolled = IJsonOption.RE_ROLLED.getFrom(jsonObject);
        return this;
    }
}

