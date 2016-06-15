package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportSkillRoll implements IReport {
    private ReportId fId;
    private String fPlayerId;
    private int fRoll;
    private boolean fSuccessful;
    private int fMinimumRoll;

    public ReportSkillRoll(ReportId pId) {
        this.fId = pId;
    }

    public ReportSkillRoll(ReportId pId, String pPlayerId,  boolean pSuccessful, int pMinimumRoll, int pRoll) {
        this.fId = pId;
        this.fPlayerId = pPlayerId;
        this.fRoll = pRoll;
        this.fSuccessful = pSuccessful;
        this.fMinimumRoll = pMinimumRoll;
    }

    @Override
    public ReportId getId() {
        return this.fId;
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

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    @Override
    public IReport transform() {
        return new ReportSkillRoll(this.getId(), this.getPlayerId(), this.isSuccessful(), this.getMinimumRoll(), this.getRoll());
    }

    @Override
    public ReportSkillRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

