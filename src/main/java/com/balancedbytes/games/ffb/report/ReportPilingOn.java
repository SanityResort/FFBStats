/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPilingOn
implements IReport {
    private String fPlayerId;
    private boolean fUsed;
    private boolean fReRollInjury;

    public ReportPilingOn() {
    }

    public ReportPilingOn(String pPlayerId, boolean pUsed, boolean pReRollInjury) {
        this.fPlayerId = pPlayerId;
        this.fUsed = pUsed;
        this.fReRollInjury = pReRollInjury;
    }

    @Override
    public ReportId getId() {
        return ReportId.PILING_ON;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isUsed() {
        return this.fUsed;
    }

    public boolean isReRollInjury() {
        return this.fReRollInjury;
    }

    @Override
    public IReport transform() {
        return new ReportPilingOn(this.getPlayerId(), this.isUsed(), this.isReRollInjury());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.USED.addTo(jsonObject, this.fUsed);
        IJsonOption.RE_ROLL_INJURY.addTo(jsonObject, this.fReRollInjury);
        return jsonObject;
    }

    @Override
    public ReportPilingOn initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fUsed = IJsonOption.USED.getFrom(jsonObject);
        this.fReRollInjury = IJsonOption.RE_ROLL_INJURY.getFrom(jsonObject);
        return this;
    }
}

