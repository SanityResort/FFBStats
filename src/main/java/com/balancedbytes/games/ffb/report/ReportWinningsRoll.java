/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportWinningsRoll
implements IReport {
    private int fWinningsRollHome;
    private int fWinningsHome;
    private int fWinningsRollAway;
    private int fWinningsAway;

    public ReportWinningsRoll() {
    }

    public ReportWinningsRoll(int pRollHome, int pWinningsHome, int pRollAway, int pWinningsAway) {
        this.fWinningsRollHome = pRollHome;
        this.fWinningsHome = pWinningsHome;
        this.fWinningsRollAway = pRollAway;
        this.fWinningsAway = pWinningsAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.WINNINGS_ROLL;
    }

    public int getWinningsRollHome() {
        return this.fWinningsRollHome;
    }

    public int getWinningsHome() {
        return this.fWinningsHome;
    }

    public int getWinningsRollAway() {
        return this.fWinningsRollAway;
    }

    public int getWinningsAway() {
        return this.fWinningsAway;
    }

    @Override
    public IReport transform() {
        return new ReportWinningsRoll(this.getWinningsRollAway(), this.getWinningsAway(), this.getWinningsRollHome(), this.getWinningsHome());
    }

    @Override
    public ReportWinningsRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fWinningsRollHome = IJsonOption.WINNINGS_ROLL_HOME.getFrom(jsonObject);
        this.fWinningsHome = IJsonOption.WINNINGS_HOME.getFrom(jsonObject);
        this.fWinningsRollAway = IJsonOption.WINNINGS_ROLL_AWAY.getFrom(jsonObject);
        this.fWinningsAway = IJsonOption.WINNINGS_AWAY.getFrom(jsonObject);
        return this;
    }
}

