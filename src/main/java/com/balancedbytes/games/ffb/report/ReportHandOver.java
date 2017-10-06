/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportHandOver
implements IReport {
    private String fCatcherId;

    public ReportHandOver() {
    }

    public ReportHandOver(String pCatcherId) {
        this.fCatcherId = pCatcherId;
    }

    @Override
    public ReportId getId() {
        return ReportId.HAND_OVER;
    }

    public String getCatcherId() {
        return this.fCatcherId;
    }

    @Override
    public IReport transform() {
        return new ReportHandOver(this.getCatcherId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.CATCHER_ID.addTo(jsonObject, this.fCatcherId);
        return jsonObject;
    }

    @Override
    public ReportHandOver initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fCatcherId = IJsonOption.CATCHER_ID.getFrom(jsonObject);
        return this;
    }
}
