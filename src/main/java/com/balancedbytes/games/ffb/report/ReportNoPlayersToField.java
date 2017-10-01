/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportNoPlayersToField
implements IReport {
    private String fTeamId;

    public ReportNoPlayersToField() {
    }

    public ReportNoPlayersToField(String pTeamId) {
        this.fTeamId = pTeamId;
    }

    @Override
    public ReportId getId() {
        return ReportId.NO_PLAYERS_TO_FIELD;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    @Override
    public IReport transform() {
        return new ReportNoPlayersToField(this.getTeamId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        return jsonObject;
    }

    @Override
    public ReportNoPlayersToField initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        return this;
    }
}

