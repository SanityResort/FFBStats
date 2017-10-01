/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportFanFactorRoll
implements IReport {
    private int[] fFanFactorRollHome;
    private int fFanFactorModifierHome;
    private int[] fFanFactorRollAway;
    private int fFanFactorModifierAway;

    public ReportFanFactorRoll() {
    }

    public ReportFanFactorRoll(int[] pFanFactorRollHome, int pFanFactorModifierHome, int[] pFanFactorRollAway, int pFanFactorModifierAway) {
        this.fFanFactorRollHome = pFanFactorRollHome;
        this.fFanFactorModifierHome = pFanFactorModifierHome;
        this.fFanFactorRollAway = pFanFactorRollAway;
        this.fFanFactorModifierAway = pFanFactorModifierAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.FAN_FACTOR_ROLL;
    }

    public int[] getFanFactorRollHome() {
        return this.fFanFactorRollHome;
    }

    public int getFanFactorModifierHome() {
        return this.fFanFactorModifierHome;
    }

    public int[] getFanFactorRollAway() {
        return this.fFanFactorRollAway;
    }

    public int getFanFactorModifierAway() {
        return this.fFanFactorModifierAway;
    }

    @Override
    public IReport transform() {
        return new ReportFanFactorRoll(this.getFanFactorRollAway(), this.getFanFactorModifierAway(), this.getFanFactorRollHome(), this.getFanFactorModifierHome());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.FAN_FACTOR_ROLL_HOME.addTo(jsonObject, this.fFanFactorRollHome);
        IJsonOption.FAN_FACTOR_MODIFIER_HOME.addTo(jsonObject, this.fFanFactorModifierHome);
        IJsonOption.FAN_FACTOR_ROLL_AWAY.addTo(jsonObject, this.fFanFactorRollAway);
        IJsonOption.FAN_FACTOR_MODIFIER_AWAY.addTo(jsonObject, this.fFanFactorModifierAway);
        return jsonObject;
    }

    @Override
    public ReportFanFactorRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fFanFactorRollHome = IJsonOption.FAN_FACTOR_ROLL_HOME.getFrom(jsonObject);
        this.fFanFactorModifierHome = IJsonOption.FAN_FACTOR_MODIFIER_HOME.getFrom(jsonObject);
        this.fFanFactorRollAway = IJsonOption.FAN_FACTOR_ROLL_AWAY.getFrom(jsonObject);
        this.fFanFactorModifierAway = IJsonOption.FAN_FACTOR_MODIFIER_AWAY.getFrom(jsonObject);
        return this;
    }
}

