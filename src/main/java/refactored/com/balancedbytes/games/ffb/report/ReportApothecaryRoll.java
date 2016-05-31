package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportApothecaryRoll implements IReport {
    private String fPlayerId;
    private int[] fCasualtyRoll;

    ReportApothecaryRoll() {
    }

    private ReportApothecaryRoll(String pPlayerId, int[] pCasualtyRoll) {
        this.fPlayerId = pPlayerId;
        this.fCasualtyRoll = pCasualtyRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.APOTHECARY_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public int[] getCasualtyRoll() {
        return this.fCasualtyRoll;
    }

    @Override
    public IReport transform() {
        return new ReportApothecaryRoll(this.getPlayerId(), this.getCasualtyRoll());
    }

    @Override
    public ReportApothecaryRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fCasualtyRoll = IJsonOption.CASUALTY_ROLL.getFrom(jsonObject);
        return this;
    }
}

