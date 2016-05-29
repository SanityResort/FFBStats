/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPassBlock
implements IReport {
    private String fTeamId;
    private boolean fPassBlockAvailable;

    public ReportPassBlock() {
    }

    public ReportPassBlock(String pTeamId, boolean pPassBlockAvailable) {
        this.fTeamId = pTeamId;
        this.fPassBlockAvailable = pPassBlockAvailable;
    }

    @Override
    public ReportId getId() {
        return ReportId.PASS_BLOCK;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public boolean isPassBlockAvailable() {
        return this.fPassBlockAvailable;
    }

    @Override
    public IReport transform() {
        return new ReportPassBlock(this.getTeamId(), this.isPassBlockAvailable());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.PASS_BLOCK_AVAILABLE.addTo(jsonObject, this.fPassBlockAvailable);
        return jsonObject;
    }

    @Override
    public ReportPassBlock initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fPassBlockAvailable = IJsonOption.PASS_BLOCK_AVAILABLE.getFrom(jsonObject);
        return this;
    }
}

