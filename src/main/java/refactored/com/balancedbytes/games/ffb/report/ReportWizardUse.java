package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportWizardUse implements IReport {
    private String fTeamId;

    public ReportWizardUse() {
    }

    public ReportWizardUse(String pTeamId) {
        this.fTeamId = pTeamId;
    }

    @Override
    public ReportId getId() {
        return ReportId.WIZARD_USE;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    @Override
    public IReport transform() {
        return new ReportWizardUse(this.getTeamId());
    }

    @Override
    public ReportWizardUse initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        return this;
    }
}

