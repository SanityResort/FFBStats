/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPenaltyShootout
implements IReport {
    private int fRollHome;
    private int fReRollsLeftHome;
    private int fRollAway;
    private int fReRollsLeftAway;

    public ReportPenaltyShootout() {
    }

    public ReportPenaltyShootout(int pRollHome, int pReRollsLeftHome, int pRollAway, int pReRollsLeftAway) {
        this.fRollHome = pRollHome;
        this.fReRollsLeftHome = pReRollsLeftHome;
        this.fRollAway = pRollAway;
        this.fReRollsLeftAway = pReRollsLeftAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.PENALTY_SHOOTOUT;
    }

    public int getRollHome() {
        return this.fRollHome;
    }

    public int getReRollsLeftHome() {
        return this.fReRollsLeftHome;
    }

    public int getRollAway() {
        return this.fRollAway;
    }

    public int getReRollsLeftAway() {
        return this.fReRollsLeftAway;
    }

    @Override
    public IReport transform() {
        return new ReportPenaltyShootout(this.getRollAway(), this.getReRollsLeftAway(), this.getRollHome(), this.getReRollsLeftHome());
    }

    @Override
    public ReportPenaltyShootout initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRollHome = IJsonOption.ROLL_HOME.getFrom(jsonObject);
        this.fReRollsLeftHome = IJsonOption.RE_ROLLS_LEFT_HOME.getFrom(jsonObject);
        this.fRollAway = IJsonOption.ROLL_AWAY.getFrom(jsonObject);
        this.fReRollsLeftAway = IJsonOption.RE_ROLLS_LEFT_AWAY.getFrom(jsonObject);
        return this;
    }
}

