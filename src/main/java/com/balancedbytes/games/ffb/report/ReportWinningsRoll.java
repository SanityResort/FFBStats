package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportWinningsRoll implements IReport {
    private int fWinningsRollHome;
    private int fWinningsRollAway;

    public ReportWinningsRoll() {
    }

    public ReportWinningsRoll(int pRollHome, int pRollAway) {
        this.fWinningsRollHome = pRollHome;
        this.fWinningsRollAway = pRollAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.WINNINGS_ROLL;
    }

    public int getWinningsRollHome() {
        return this.fWinningsRollHome;
    }

    public int getWinningsRollAway() {
        return this.fWinningsRollAway;
    }

    @Override
    public IReport transform() {
        return new ReportWinningsRoll(this.getWinningsRollAway(), this.getWinningsRollHome());
    }

    @Override
    public ReportWinningsRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fWinningsRollHome = IJsonOption.WINNINGS_ROLL_HOME.getFrom(jsonObject);
        this.fWinningsRollAway = IJsonOption.WINNINGS_ROLL_AWAY.getFrom(jsonObject);
        return this;
    }
}

