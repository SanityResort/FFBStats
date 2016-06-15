package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportBribesRoll implements IReport {
    private String fPlayerId;
    private int fRoll;
    private boolean fSuccessful;

    ReportBribesRoll() {
    }

    private ReportBribesRoll(String pPlayerId, int pRoll, boolean pSuccessful) {
        this.fPlayerId = pPlayerId;
        this.fRoll = pRoll;
        this.fSuccessful = pSuccessful;
    }

    @Override
    public ReportId getId() {
        return ReportId.BRIBES_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public int getRoll() {
        return this.fRoll;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    @Override
    public IReport transform() {
        return new ReportBribesRoll(this.getPlayerId(), this.getRoll(), this.isSuccessful());
    }

    @Override
    public ReportBribesRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        return this;
    }
}

