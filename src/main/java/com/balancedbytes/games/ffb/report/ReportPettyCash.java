/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPettyCash
implements IReport {
    private String fTeamId;
    private int fGold;

    public ReportPettyCash() {
    }

    public ReportPettyCash(String pTeamId, int pGold) {
        this.fTeamId = pTeamId;
        this.fGold = pGold;
    }

    @Override
    public ReportId getId() {
        return ReportId.PETTY_CASH;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getGold() {
        return this.fGold;
    }

    @Override
    public IReport transform() {
        return new ReportPettyCash(this.getTeamId(), this.getGold());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.GOLD.addTo(jsonObject, this.fGold);
        return jsonObject;
    }

    @Override
    public ReportPettyCash initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fGold = IJsonOption.GOLD.getFrom(jsonObject);
        return this;
    }
}

