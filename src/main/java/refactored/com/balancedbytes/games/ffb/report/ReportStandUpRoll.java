package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportStandUpRoll implements IReport {
    private String fPlayerId;
    private int fRoll;

    ReportStandUpRoll() {
    }

    private ReportStandUpRoll(String pPlayerId, int pRoll) {
        this.fPlayerId = pPlayerId;
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.STAND_UP_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        return new ReportStandUpRoll(this.getPlayerId(), this.getRoll());
    }

    @Override
    public ReportStandUpRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

