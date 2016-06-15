package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportSpectators implements IReport {
    private int[] fSpectatorRollHome;
    private int[] fSpectatorRollAway;
    private int fFameHome;
    private int fFameAway;

    ReportSpectators() {
    }

    private ReportSpectators(int[] pRollHome, int[] pRollAway, int pFameHome, int pFameAway) {
        this.fSpectatorRollHome = pRollHome;
        this.fSpectatorRollAway = pRollAway;
        this.fFameHome = pFameHome;
        this.fFameAway = pFameAway;
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

    public int getFameHome() {
        return this.fFameHome;
    }

    public int getFameAway() {
        return this.fFameAway;
    }

    @Override
    public IReport transform() {
        return new ReportSpectators(this.getSpectatorRollAway(), this.getSpectatorRollHome(), this.getFameHome(), this.getFameAway());
    }

    @Override
    public ReportSpectators initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSpectatorRollHome = IJsonOption.SPECTATOR_ROLL_HOME.getFrom(jsonObject);
        this.fSpectatorRollAway = IJsonOption.SPECTATOR_ROLL_AWAY.getFrom(jsonObject);
        this.fFameHome = IJsonOption.FAME_HOME.getFrom(jsonObject);
        this.fFameAway = IJsonOption.FAME_AWAY.getFrom(jsonObject);
        return this;
    }
}

