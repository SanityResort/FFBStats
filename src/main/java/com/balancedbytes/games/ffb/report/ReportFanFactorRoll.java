package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportFanFactorRoll implements IReport {
    private int[] fFanFactorRollHome;
    private int[] fFanFactorRollAway;

    ReportFanFactorRoll() {
    }

    private ReportFanFactorRoll(int[] pFanFactorRollHome, int[] pFanFactorRollAway) {
        this.fFanFactorRollHome = pFanFactorRollHome;
        this.fFanFactorRollAway = pFanFactorRollAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.FAN_FACTOR_ROLL;
    }

    public int[] getFanFactorRollHome() {
        return this.fFanFactorRollHome;
    }

    public int[] getFanFactorRollAway() {
        return this.fFanFactorRollAway;
    }

    @Override
    public IReport transform() {
        return new ReportFanFactorRoll(this.getFanFactorRollAway(), this.getFanFactorRollHome());
    }

    @Override
    public ReportFanFactorRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fFanFactorRollHome = IJsonOption.FAN_FACTOR_ROLL_HOME.getFrom(jsonObject);
        this.fFanFactorRollAway = IJsonOption.FAN_FACTOR_ROLL_AWAY.getFrom(jsonObject);
        return this;
    }
}

