/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportInducement
implements IReport {
    private String fTeamId;
    private InducementType fInducementType;
    private int fValue;

    public ReportInducement() {
    }

    public ReportInducement(String pTeamId, InducementType pType, int pValue) {
        this.fTeamId = pTeamId;
        this.fInducementType = pType;
        this.fValue = pValue;
    }

    @Override
    public ReportId getId() {
        return ReportId.INDUCEMENT;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public InducementType getInducementType() {
        return this.fInducementType;
    }

    public int getValue() {
        return this.fValue;
    }

    @Override
    public IReport transform() {
        return new ReportInducement(this.getTeamId(), this.getInducementType(), this.getValue());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.INDUCEMENT_TYPE.addTo(jsonObject, this.fInducementType);
        IJsonOption.VALUE.addTo(jsonObject, this.fValue);
        return jsonObject;
    }

    @Override
    public ReportInducement initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fInducementType = (InducementType)IJsonOption.INDUCEMENT_TYPE.getFrom(jsonObject);
        this.fValue = IJsonOption.VALUE.getFrom(jsonObject);
        return this;
    }
}

