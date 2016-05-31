package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

class ReportFactory {
    IReport forJsonValue(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        ReportId reportId = (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject);
        return (IReport)reportId.createReport().initFrom(jsonObject);
    }
}

