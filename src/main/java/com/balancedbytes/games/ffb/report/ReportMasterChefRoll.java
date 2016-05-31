package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportMasterChefRoll implements IReport {
    private String fTeamId;
    private int[] fMasterChefRoll;

    ReportMasterChefRoll() {
    }

    private ReportMasterChefRoll(String pTeamId, int[] pRoll) {
        this.fTeamId = pTeamId;
        this.fMasterChefRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.MASTER_CHEF_ROLL;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int[] getMasterChefRoll() {
        return this.fMasterChefRoll;
    }

    @Override
    public IReport transform() {
        return new ReportMasterChefRoll(this.getTeamId(), this.getMasterChefRoll());
    }

    @Override
    public ReportMasterChefRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fMasterChefRoll = IJsonOption.MASTER_CHEF_ROLL.getFrom(jsonObject);
        return this;
    }
}

