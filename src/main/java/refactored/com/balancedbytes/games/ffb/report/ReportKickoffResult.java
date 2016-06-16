/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.report;


import refactored.com.balancedbytes.games.ffb.KickoffResult;
import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportKickoffResult implements IReport {
    private KickoffResult fKickoffResult;
    private int[] fKickoffRoll;

    public ReportKickoffResult() {
    }

    public ReportKickoffResult(KickoffResult pKickoffResult, int[] pKickoffRoll) {
        this.fKickoffResult = pKickoffResult;
        this.fKickoffRoll = pKickoffRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_RESULT;
    }

    public KickoffResult getKickoffResult() {
        return this.fKickoffResult;
    }

    public int[] getKickoffRoll() {
        return this.fKickoffRoll;
    }

    @Override
    public IReport transform() {
        return new ReportKickoffResult(this.getKickoffResult(), this.getKickoffRoll());
    }

    @Override
    public ReportKickoffResult initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fKickoffResult = (KickoffResult)IJsonOption.KICKOFF_RESULT.getFrom(jsonObject);
        this.fKickoffRoll = IJsonOption.KICKOFF_ROLL.getFrom(jsonObject);
        return this;
    }
}

