/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportRaiseDead
implements IReport {
    private String fPlayerId;
    private boolean fNurglesRot;

    public ReportRaiseDead() {
    }

    public ReportRaiseDead(String pPlayerId, boolean pNurglesRot) {
        this.fPlayerId = pPlayerId;
        this.fNurglesRot = pNurglesRot;
    }

    @Override
    public ReportId getId() {
        return ReportId.RAISE_DEAD;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isNurglesRot() {
        return this.fNurglesRot;
    }

    @Override
    public IReport transform() {
        return new ReportRaiseDead(this.getPlayerId(), this.isNurglesRot());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.NURGLES_ROT.addTo(jsonObject, this.fNurglesRot);
        return jsonObject;
    }

    @Override
    public ReportRaiseDead initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fNurglesRot = IJsonOption.NURGLES_ROT.getFrom(jsonObject);
        return this;
    }
}
