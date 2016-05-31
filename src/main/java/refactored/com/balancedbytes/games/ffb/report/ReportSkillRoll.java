package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportSkillRoll implements IReport {
    private ReportId fId;
    private String fPlayerId;
    private int fRoll;

    public ReportSkillRoll(ReportId pId) {
        this.fId = pId;
    }

    public ReportSkillRoll(ReportId pId, String pPlayerId, int pRoll) {
        this.fId = pId;
        this.fPlayerId = pPlayerId;
        this.fRoll = pRoll;
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

    @Override
    public IReport transform() {
        return new ReportSkillRoll(this.getId(), this.getPlayerId(), this.getRoll());
    }

    @Override
    public ReportSkillRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

