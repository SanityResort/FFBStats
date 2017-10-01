/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportDoubleHiredStarPlayer
implements IReport {
    private String fStarPlayerName;

    public ReportDoubleHiredStarPlayer() {
    }

    public ReportDoubleHiredStarPlayer(String pStarPlayerName) {
        this.fStarPlayerName = pStarPlayerName;
    }

    @Override
    public ReportId getId() {
        return ReportId.DOUBLE_HIRED_STAR_PLAYER;
    }

    public String getStarPlayerName() {
        return this.fStarPlayerName;
    }

    @Override
    public IReport transform() {
        return new ReportDoubleHiredStarPlayer(this.getStarPlayerName());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.STAR_PLAYER_NAME.addTo(jsonObject, this.fStarPlayerName);
        return jsonObject;
    }

    @Override
    public ReportDoubleHiredStarPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fStarPlayerName = IJsonOption.STAR_PLAYER_NAME.getFrom(jsonObject);
        return this;
    }
}

