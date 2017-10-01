/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportMasterChefRoll
implements IReport {
    private String fTeamId;
    private int[] fMasterChefRoll;
    private int fReRollsStolen;

    public ReportMasterChefRoll() {
    }

    public ReportMasterChefRoll(String pTeamId, int[] pRoll, int pReRollsStolen) {
        this.fTeamId = pTeamId;
        this.fMasterChefRoll = pRoll;
        this.fReRollsStolen = pReRollsStolen;
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

    public int getReRollsStolen() {
        return this.fReRollsStolen;
    }

    @Override
    public IReport transform() {
        return new ReportMasterChefRoll(this.getTeamId(), this.getMasterChefRoll(), this.getReRollsStolen());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.MASTER_CHEF_ROLL.addTo(jsonObject, this.fMasterChefRoll);
        IJsonOption.RE_ROLLS_STOLEN.addTo(jsonObject, this.fReRollsStolen);
        return jsonObject;
    }

    @Override
    public ReportMasterChefRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fMasterChefRoll = IJsonOption.MASTER_CHEF_ROLL.getFrom(jsonObject);
        this.fReRollsStolen = IJsonOption.RE_ROLLS_STOLEN.getFrom(jsonObject);
        return this;
    }
}

