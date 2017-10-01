/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.KickoffResult;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportKickoffResult
implements IReport {
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
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.KICKOFF_RESULT.addTo(jsonObject, this.fKickoffResult);
        IJsonOption.KICKOFF_ROLL.addTo(jsonObject, this.fKickoffRoll);
        return jsonObject;
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

