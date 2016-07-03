package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportBiteSpectator implements IReport {
    private String fPlayerId;

    public ReportBiteSpectator() {
    }

    public ReportBiteSpectator(String pCatcherId) {
        this.fPlayerId = pCatcherId;
    }

    @Override
    public ReportId getId() {
        return ReportId.BITE_SPECTATOR;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    @Override
    public IReport transform() {
        return new ReportBiteSpectator(this.getPlayerId());
    }

    @Override
    public ReportBiteSpectator initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        return this;
    }
}

