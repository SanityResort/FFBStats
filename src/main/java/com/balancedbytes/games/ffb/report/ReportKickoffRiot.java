/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportKickoffRiot
implements IReport {
    private int fRoll;
    private int fTurnModifier;

    public ReportKickoffRiot() {
    }

    public ReportKickoffRiot(int pRoll, int pTurnModifier) {
        this.fRoll = pRoll;
        this.fTurnModifier = pTurnModifier;
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_RIOT;
    }

    public int getRoll() {
        return this.fRoll;
    }

    public int getTurnModifier() {
        return this.fTurnModifier;
    }

    @Override
    public IReport transform() {
        return new ReportKickoffRiot(this.getRoll(), this.getTurnModifier());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        IJsonOption.TURN_MODIFIER.addTo(jsonObject, this.fTurnModifier);
        return jsonObject;
    }

    @Override
    public ReportKickoffRiot initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fTurnModifier = IJsonOption.TURN_MODIFIER.getFrom(jsonObject);
        return this;
    }
}

