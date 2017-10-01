/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportBlockRoll
implements IReport {
    private int[] fBlockRoll;
    private String fChoosingTeamId;

    public ReportBlockRoll() {
    }

    public ReportBlockRoll(String pChoosingTeamId, int[] pBlockRoll) {
        this.fChoosingTeamId = pChoosingTeamId;
        this.fBlockRoll = pBlockRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.BLOCK_ROLL;
    }

    public String getChoosingTeamId() {
        return this.fChoosingTeamId;
    }

    public int[] getBlockRoll() {
        return this.fBlockRoll;
    }

    @Override
    public IReport transform() {
        return new ReportBlockRoll(this.getChoosingTeamId(), this.getBlockRoll());
    }

    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.CHOOSING_TEAM_ID.addTo(jsonObject, this.fChoosingTeamId);
        IJsonOption.BLOCK_ROLL.addTo(jsonObject, this.fBlockRoll);
        return jsonObject;
    }

    @Override
    public ReportBlockRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fChoosingTeamId = IJsonOption.CHOOSING_TEAM_ID.getFrom(jsonObject);
        this.fBlockRoll = IJsonOption.BLOCK_ROLL.getFrom(jsonObject);
        return this;
    }
}

