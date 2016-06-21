package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportPilingOn implements IReport {
    private String fPlayerId;
    private boolean fReRollInjury;
    private boolean fUsed;

    ReportPilingOn() {
    }

    private ReportPilingOn(String pPlayerId, boolean pUsed, boolean pReRollInjury) {
        this.fPlayerId = pPlayerId;
        this.fUsed = pUsed;
        this.fReRollInjury = pReRollInjury;
    }

    @Override
    public ReportId getId() {
        return ReportId.PILING_ON;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isUsed() {
        return this.fUsed;
    }

    public boolean isReRollInjury() {
        return this.fReRollInjury;
    }

    @Override
    public IReport transform() {
        return new ReportPilingOn(this.getPlayerId(), this.isUsed(), this.isReRollInjury());
    }

    @Override
    public ReportPilingOn initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fUsed = IJsonOption.USED.getFrom(jsonObject);
        this.fReRollInjury = IJsonOption.RE_ROLL_INJURY.getFrom(jsonObject);
        return this;
    }
}

