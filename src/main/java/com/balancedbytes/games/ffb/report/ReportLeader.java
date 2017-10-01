/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.LeaderState;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportLeader
implements IReport {
    private String fTeamId;
    private LeaderState fLeaderState;

    public ReportLeader() {
    }

    public ReportLeader(String pTeamId, LeaderState pLeaderState) {
        this.fTeamId = pTeamId;
        this.fLeaderState = pLeaderState;
    }

    @Override
    public ReportId getId() {
        return ReportId.LEADER;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public LeaderState getLeaderState() {
        return this.fLeaderState;
    }

    @Override
    public IReport transform() {
        return new ReportLeader(this.getTeamId(), this.getLeaderState());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.LEADER_STATE.addTo(jsonObject, this.fLeaderState);
        return jsonObject;
    }

    @Override
    public ReportLeader initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fLeaderState = (LeaderState)IJsonOption.LEADER_STATE.getFrom(jsonObject);
        return this;
    }
}

