/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportStartHalf
implements IReport {
    private int fHalf;

    public ReportStartHalf() {
    }

    public ReportStartHalf(int pHalf) {
        this.fHalf = pHalf;
    }

    @Override
    public ReportId getId() {
        return ReportId.START_HALF;
    }

    public int getHalf() {
        return this.fHalf;
    }

    @Override
    public IReport transform() {
        return new ReportStartHalf(this.getHalf());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.HALF.addTo(jsonObject, this.fHalf);
        return jsonObject;
    }

    @Override
    public ReportStartHalf initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fHalf = IJsonOption.HALF.getFrom(jsonObject);
        return this;
    }
}

