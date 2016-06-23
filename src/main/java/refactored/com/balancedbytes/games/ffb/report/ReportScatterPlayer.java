package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportScatterPlayer implements IReport {

    @Override
    public ReportId getId() {
        return ReportId.SCATTER_PLAYER;
    }

    @Override
    public IReport transform() {
        return new ReportScatterPlayer();
    }

    @Override
    public ReportScatterPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        return this;
    }
}

