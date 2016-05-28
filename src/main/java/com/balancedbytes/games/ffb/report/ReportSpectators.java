/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportSpectators
implements IReport {
    private int[] fSpectatorRollHome;
    private int fSpectatorsHome;
    private int fFameHome;
    private int[] fSpectatorRollAway;
    private int fSpectatorsAway;
    private int fFameAway;

    public ReportSpectators() {
    }

    public ReportSpectators(int[] pRollHome, int pSupportersHome, int pFameHome, int[] pRollAway, int pSupportersAway, int pFameAway) {
        this.fSpectatorRollHome = pRollHome;
        this.fSpectatorsHome = pSupportersHome;
        this.fFameHome = pFameHome;
        this.fSpectatorRollAway = pRollAway;
        this.fSpectatorsAway = pSupportersAway;
        this.fFameAway = pFameAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.SPECTATORS;
    }

    public int[] getSpectatorRollHome() {
        return this.fSpectatorRollHome;
    }

    public int getSpectatorsHome() {
        return this.fSpectatorsHome;
    }

    public int getFameHome() {
        return this.fFameHome;
    }

    public int[] getSpectatorRollAway() {
        return this.fSpectatorRollAway;
    }

    public int getSpectatorsAway() {
        return this.fSpectatorsAway;
    }

    public int getFameAway() {
        return this.fFameAway;
    }

    @Override
    public IReport transform() {
        return new ReportSpectators(this.getSpectatorRollAway(), this.getSpectatorsAway(), this.getFameAway(), this.getSpectatorRollHome(), this.getSpectatorsHome(), this.getFameHome());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.SPECTATOR_ROLL_HOME.addTo(jsonObject, this.fSpectatorRollHome);
        IJsonOption.SPECTATORS_HOME.addTo(jsonObject, this.fSpectatorsHome);
        IJsonOption.FAME_HOME.addTo(jsonObject, this.fFameHome);
        IJsonOption.SPECTATOR_ROLL_AWAY.addTo(jsonObject, this.fSpectatorRollAway);
        IJsonOption.SPECTATORS_AWAY.addTo(jsonObject, this.fSpectatorsAway);
        IJsonOption.FAME_AWAY.addTo(jsonObject, this.fFameAway);
        return jsonObject;
    }

    @Override
    public ReportSpectators initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSpectatorRollHome = IJsonOption.SPECTATOR_ROLL_HOME.getFrom(jsonObject);
        this.fSpectatorsHome = IJsonOption.SPECTATORS_HOME.getFrom(jsonObject);
        this.fFameHome = IJsonOption.FAME_HOME.getFrom(jsonObject);
        this.fSpectatorRollAway = IJsonOption.SPECTATOR_ROLL_AWAY.getFrom(jsonObject);
        this.fSpectatorsAway = IJsonOption.SPECTATORS_AWAY.getFrom(jsonObject);
        this.fFameAway = IJsonOption.FAME_AWAY.getFrom(jsonObject);
        return this;
    }
}

