package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.ReRollSource;
import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportReRoll implements IReport {

    private String fPlayerId;
    private ReRollSource fReRollSource;

    public ReportReRoll() {
    }

    public ReportReRoll(String pPlayerId, ReRollSource pReRollSource) {
        this.fPlayerId = pPlayerId;
        this.fReRollSource = pReRollSource;
    }

    @Override
    public ReportId getId() {
        return ReportId.RE_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public ReRollSource getReRollSource() {
        return this.fReRollSource;
    }

    @Override
    public IReport transform() {
        return new ReportReRoll(this.getPlayerId(), this.getReRollSource());
    }

    @Override
    public ReportReRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fReRollSource = (ReRollSource)IJsonOption.RE_ROLL_SOURCE.getFrom(jsonObject);
        return this;
    }
}

