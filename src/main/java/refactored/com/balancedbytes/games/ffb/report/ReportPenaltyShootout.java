package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportPenaltyShootout implements IReport {
    private int fRollHome;
    private int fRollAway;

    public ReportPenaltyShootout() {
    }

    public ReportPenaltyShootout(int pRollHome, int pRollAway) {
        this.fRollHome = pRollHome;
        this.fRollAway = pRollAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.PENALTY_SHOOTOUT;
    }

    public int getRollHome() {
        return this.fRollHome;
    }

    public int getRollAway() {
        return this.fRollAway;
    }


    @Override
    public IReport transform() {
        return new ReportPenaltyShootout(this.getRollAway(), this.getRollHome());
    }

    @Override
    public ReportPenaltyShootout initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRollHome = IJsonOption.ROLL_HOME.getFrom(jsonObject);
        this.fRollAway = IJsonOption.ROLL_AWAY.getFrom(jsonObject);
        return this;
    }
}

