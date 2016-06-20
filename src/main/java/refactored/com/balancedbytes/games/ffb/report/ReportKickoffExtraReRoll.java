/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.report;


import refactored.com.balancedbytes.games.ffb.KickoffResult;
import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportKickoffExtraReRoll implements IReport {
    private KickoffResult fKickoffResult;
    private int fRollHome;
    private int fRollAway;

    public ReportKickoffExtraReRoll() {
    }

    public ReportKickoffExtraReRoll(KickoffResult pKickoffResult, int pRollHome, int pRollAway) {
        this.fKickoffResult = pKickoffResult;
        this.fRollHome = pRollHome;
        this.fRollAway = pRollAway;
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_EXTRA_REROLL;
    }

    public KickoffResult getKickoffResult() {
        return this.fKickoffResult;
    }

    public int getRollHome() {
        return this.fRollHome;
    }

    public int getRollAway() {
        return this.fRollAway;
    }

    @Override
    public IReport transform() {
        return new ReportKickoffExtraReRoll(this.getKickoffResult(), this.getRollAway(),this.getRollHome());
    }

    @Override
    public ReportKickoffExtraReRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fKickoffResult = (KickoffResult)IJsonOption.KICKOFF_RESULT.getFrom(jsonObject);
        this.fRollHome = IJsonOption.ROLL_HOME.getFrom(jsonObject);
        this.fRollAway = IJsonOption.ROLL_AWAY.getFrom(jsonObject);
        return this;
    }
}

