/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportBlock
implements IReport {
    private String fDefenderId;

    public ReportBlock() {
    }

    public ReportBlock(String pDefenderId) {
        this.fDefenderId = pDefenderId;
    }

    @Override
    public ReportId getId() {
        return ReportId.BLOCK;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    @Override
    public IReport transform() {
        return new ReportBlock(this.getDefenderId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        return jsonObject;
    }

    @Override
    public ReportBlock initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        return this;
    }
}

