/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportBiteSpectator
implements IReport {
    private String fPlayerId;

    public ReportBiteSpectator() {
    }

    public ReportBiteSpectator(String pCatcherId) {
        this.fPlayerId = pCatcherId;
    }

    @Override
    public ReportId getId() {
        return ReportId.BITE_SPECTATOR;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    @Override
    public IReport transform() {
        return new ReportBiteSpectator(this.getPlayerId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        return jsonObject;
    }

    @Override
    public ReportBiteSpectator initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        return this;
    }
}

