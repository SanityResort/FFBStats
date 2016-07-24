package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportTimeoutEnforced implements IReport {

    public ReportTimeoutEnforced() {
    }

    @Override
    public ReportId getId() {
        return ReportId.TIMEOUT_ENFORCED;
    }

    @Override
    public IReport transform() {
        return new ReportTimeoutEnforced();
    }

    @Override
    public ReportTimeoutEnforced initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        return this;
    }
}

