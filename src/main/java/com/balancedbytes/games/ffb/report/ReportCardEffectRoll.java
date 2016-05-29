/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportCardEffectRoll
implements IReport {
    private int fRoll;

    public ReportCardEffectRoll() {
    }

    public ReportCardEffectRoll( int pRoll) {
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.CARD_EFFECT_ROLL;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        ReportCardEffectRoll transformedReport = new ReportCardEffectRoll(this.getRoll());
        return transformedReport;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        return jsonObject;
    }

    @Override
    public ReportCardEffectRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

