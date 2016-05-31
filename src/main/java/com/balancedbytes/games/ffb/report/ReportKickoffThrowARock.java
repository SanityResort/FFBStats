package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportKickoffThrowARock implements IReport {
    private int fRollHome;
    private int fRollAway;

    ReportKickoffThrowARock() {
    }

    private ReportKickoffThrowARock(int pRollHome, int pRollAway) {
        this();
        this.fRollHome = pRollHome;
        this.fRollAway = pRollAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_THROW_A_ROCK;
    }

    public int getRollHome() {
        return this.fRollHome;
    }

    public int getRollAway() {
        return this.fRollAway;
    }

    @Override
    public IReport transform() {
        return new ReportKickoffThrowARock(this.getRollAway(), this.getRollHome());
    }

    @Override
    public ReportKickoffThrowARock initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRollHome = IJsonOption.ROLL_HOME.getFrom(jsonObject);
        this.fRollAway = IJsonOption.ROLL_AWAY.getFrom(jsonObject);
        return this;
    }
}

