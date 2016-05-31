package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportSpectators implements IReport {
    private int[] fSpectatorRollHome;
    private int[] fSpectatorRollAway;

    ReportSpectators() {
    }

    private ReportSpectators(int[] pRollHome, int[] pRollAway) {
        this.fSpectatorRollHome = pRollHome;
        this.fSpectatorRollAway = pRollAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.SPECTATORS;
    }

    public int[] getSpectatorRollHome() {
        return this.fSpectatorRollHome;
    }

    public int[] getSpectatorRollAway() {
        return this.fSpectatorRollAway;
    }

    @Override
    public IReport transform() {
        return new ReportSpectators(this.getSpectatorRollAway(), this.getSpectatorRollHome());
    }

    @Override
    public ReportSpectators initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSpectatorRollHome = IJsonOption.SPECTATOR_ROLL_HOME.getFrom(jsonObject);
        this.fSpectatorRollAway = IJsonOption.SPECTATOR_ROLL_AWAY.getFrom(jsonObject);
        return this;
    }
}

