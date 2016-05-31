package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportTentaclesShadowingRoll implements IReport {
    private String fDefenderId;
    private int[] fRoll;

    public ReportTentaclesShadowingRoll() {
    }

    public ReportTentaclesShadowingRoll( String pDefenderId, int[] pRoll) {
        this.fDefenderId = pDefenderId;
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.TENTACLES_SHADOWING_ROLL;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public int[] getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        return new ReportTentaclesShadowingRoll(this.getDefenderId(), this.getRoll());
    }

    @Override
    public ReportTentaclesShadowingRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.TENTACLE_ROLL.getFrom(jsonObject);
        return this;
    }
}

