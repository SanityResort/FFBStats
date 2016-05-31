package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportReRoll implements IReport {

    public ReportReRoll() {
    }

    @Override
    public ReportId getId() {
        return ReportId.RE_ROLL;
    }

    @Override
    public IReport transform() {
        return new ReportReRoll();
    }

    @Override
    public ReportReRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        return this;
    }
}

