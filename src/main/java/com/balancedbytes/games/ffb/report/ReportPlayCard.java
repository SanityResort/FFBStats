/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPlayCard
implements IReport {
    private String fTeamId;
    private String fPlayerId;

    public ReportPlayCard() {
    }

    public ReportPlayCard(String pTeamId) {
        this.fTeamId = pTeamId;
    }

    public ReportPlayCard(String pTeamId, String pCatcherId) {
        this(pTeamId);
        this.fPlayerId = pCatcherId;
    }

    @Override
    public ReportId getId() {
        return ReportId.PLAY_CARD;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    @Override
    public IReport transform() {
        return new ReportPlayCard(this.getTeamId(), this.getPlayerId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        return jsonObject;
    }

    @Override
    public ReportPlayCard initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        return this;
    }
}

